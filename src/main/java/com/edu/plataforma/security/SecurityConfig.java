package com.edu.plataforma.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index", "/login", "/oauth2/**", "/login/oauth2/**", "/whoami",
                                "/h2-console/**", "/css/**", "/js/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/courses").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .authorizationEndpoint(authorization -> {
                            // Default resolver that builds the authorization request from the client
                            // registrations
                            DefaultOAuth2AuthorizationRequestResolver defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(
                                    clientRegistrationRepository,
                                    "/oauth2/authorization");

                            // Wrap resolver to add prompt=select_account so Google shows the account
                            // chooser

                            OAuth2AuthorizationRequestResolver customResolver = new OAuth2AuthorizationRequestResolver() {
                                @Override
                                public OAuth2AuthorizationRequest resolve(
                                        jakarta.servlet.http.HttpServletRequest request) {
                                    OAuth2AuthorizationRequest req = defaultResolver.resolve(request);
                                    if (req == null)
                                        return null;
                                    Map<String, Object> params = new HashMap<>(req.getAdditionalParameters());
                                    params.put("prompt", "select_account");
                                    return OAuth2AuthorizationRequest.from(req).additionalParameters(params).build();
                                }

                                @Override
                                public OAuth2AuthorizationRequest resolve(
                                        jakarta.servlet.http.HttpServletRequest request, String clientRegistrationId) {
                                    OAuth2AuthorizationRequest req = defaultResolver.resolve(request,
                                            clientRegistrationId);
                                    if (req == null)
                                        return null;
                                    Map<String, Object> params = new HashMap<>(req.getAdditionalParameters());
                                    params.put("prompt", "select_account");
                                    return OAuth2AuthorizationRequest.from(req).additionalParameters(params).build();
                                }
                            };

                            authorization.baseUri("/oauth2/authorization").authorizationRequestResolver(customResolver);
                        })
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                                .oidcUserService(customOidcUserService))
                        .defaultSuccessUrl("/home", true))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .sessionManagement(session -> session
                        .maximumSessions(1))
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}