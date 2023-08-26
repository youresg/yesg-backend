package youresg.yesg.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import youresg.yesg.config.jwt.JwtToken;
import youresg.yesg.config.jwt.JwtTokenProvider;
import youresg.yesg.domain.member.Role;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        OAuth2UserDto oAuth2UserDto = OAuth2UserDto.toDto(oAuth2User);

        JwtToken jwtToken = jwtTokenProvider.generateToken(oAuth2UserDto.getEmail(), oAuth2UserDto.getSocialProvider(), Role.MEMBER);
        log.info("jwtToken = {}", jwtToken);

        writeTokenResponse(response, jwtToken);
    }

    private void writeTokenResponse(HttpServletResponse response, JwtToken jwtToken) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        response.addHeader("Auth", jwtToken.getAccessToken());
        response.addHeader("Refresh", jwtToken.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        var writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(jwtToken));
        writer.flush();
    }
}