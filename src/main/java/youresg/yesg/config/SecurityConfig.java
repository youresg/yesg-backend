package youresg.yesg.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import youresg.yesg.config.auth.CustomOAuth2UserService;
import youresg.yesg.config.auth.OAuth2SuccessHandler;
import youresg.yesg.config.jwt.JwtAuthFilter;
import youresg.yesg.config.jwt.JwtTokenProvider;
import youresg.yesg.domain.member.MemberRepository;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf ->
                        csrf.disable()
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
                .addFilterBefore(new JwtAuthFilter(jwtTokenProvider, memberRepository), UsernamePasswordAuthenticationFilter.class)
                .logout((logoutConfig) ->
                        logoutConfig.logoutSuccessUrl("/")
                );
        return httpSecurity.build();
    }
}