package org.lab.security.config;

import org.lab.infrastructure.filter.JwtAuthFilter;
import org.lab.security.exception.handling.DefaultAccessDeniedHandler;
import org.lab.security.service.CustomLogoutHandler;
import org.lab.security.service.ProjectAuthorizationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfiguration {

    private final DefaultAccessDeniedHandler accessDeniedHandler;
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CustomLogoutHandler logoutHandler;
    private final ProjectAuthorizationManager projectAuthorizationManager;

    private static final String[] WHITE_LIST_URL_PATTERNS = {
            "/auth/**",
            "/"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(customizer -> customizer
                        .requestMatchers(WHITE_LIST_URL_PATTERNS).permitAll()
                        .requestMatchers("/projects/**")
                            .access(projectAuthorizationManager)
                        .anyRequest().authenticated())
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(customizer -> customizer
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(accessDeniedHandler))
                .authenticationProvider(authenticationProvider)
                .logout(customizer -> customizer
                        .logoutUrl("/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)).permitAll())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
