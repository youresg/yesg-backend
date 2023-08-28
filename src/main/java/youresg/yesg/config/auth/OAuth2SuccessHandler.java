package youresg.yesg.config.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import youresg.yesg.config.jwt.JwtToken;
import youresg.yesg.config.jwt.JwtTokenProvider;
import youresg.yesg.domain.member.Role;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        OAuth2UserDto oAuth2UserDto = OAuth2UserDto.toDto(oAuth2User);

        JwtToken jwtToken = jwtTokenProvider.generateToken(oAuth2UserDto.getEmail(), oAuth2UserDto.getSocialProvider(), Role.MEMBER);
        String redirectUrl = UriComponentsBuilder.fromUriString("/oauth2/redirect")
                .queryParam("accessToken", jwtToken.getAccessToken())
                .build().toUriString();

        response.sendRedirect(redirectUrl);
    }

}