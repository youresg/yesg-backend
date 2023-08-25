package youresg.yesg.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import youresg.yesg.config.auth.CustomOAuth2UserService;
import youresg.yesg.config.auth.OAuth2SuccessHandler;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf ->
                        csrf.disable()  // JWT -> STATELESS 하므로 CSRF 옵션 해제
                )
                .headers((headerConfig) ->
                        headerConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()
                        )
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth2LoginConfigurer ->
                        oauth2LoginConfigurer
                                .userInfoEndpoint(userInfoEndpointConfigurer ->
                                        userInfoEndpointConfigurer
                                                .userService(oAuth2UserService)

                                )
                                .successHandler(successHandler)
                                .userInfoEndpoint(Customizer.withDefaults())
                )
                .logout((logoutConfig) ->
                        logoutConfig.logoutSuccessUrl("/")
                );
        return httpSecurity.build();
    }
}