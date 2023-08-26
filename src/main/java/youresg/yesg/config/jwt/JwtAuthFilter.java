package youresg.yesg.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import youresg.yesg.config.auth.OAuth2UserDto;
import youresg.yesg.domain.member.Member;
import youresg.yesg.domain.member.MemberRepository;
import youresg.yesg.domain.member.Role;
import youresg.yesg.domain.member.SocialProvider;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("JwtAuthFilter doFilter");

        String token = ((HttpServletRequest) request).getHeader("Auth");

        if (token != null && jwtTokenProvider.validateToken(token)) {
            log.info("Token verified");
            String email = jwtTokenProvider.getUid(token);
            SocialProvider socialProvider = jwtTokenProvider.getSocialProvider(token);

            Member member = memberRepository.findByEmailAndSocialProvider(email, socialProvider).get();  // TODO 예외처리

            OAuth2UserDto oAuth2UserDto = OAuth2UserDto.builder()
                    .email(member.getEmail())
                    .name(member.getUsername())
                    .picture(member.getProfileImg())
                    .socialProvider(member.getSocialProvider())
                    .build();
            log.info("oAuth2UserDto = {}", oAuth2UserDto);

            Authentication auth = getAuthentication(oAuth2UserDto);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }

    public Authentication getAuthentication(OAuth2UserDto member) {
        return new UsernamePasswordAuthenticationToken(member, "",
                Arrays.asList(new SimpleGrantedAuthority(Role.MEMBER.getKey())));
    }
}