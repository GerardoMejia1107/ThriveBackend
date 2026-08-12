package com.gerardo.thrive.auth.config;

import com.gerardo.thrive.auth.filters.JwtAuthenticationFilter;
import com.gerardo.thrive.auth.security.CustomDeniedHandler;
import com.gerardo.thrive.auth.security.CustomPasswordEncoder;
import com.gerardo.thrive.auth.security.JsonAuthenticationEntryPoint;
import com.gerardo.thrive.auth.security.SecurityEndpoints;
import com.gerardo.thrive.auth.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JsonAuthenticationEntryPoint jsonAuthenticationEntryPoint;
    private final CustomDeniedHandler customDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers(SecurityEndpoints.PUBLIC)
                        .permitAll()
                        .requestMatchers(SecurityEndpoints.PRIVATE)
                        .hasRole("ADMIN")
                        .requestMatchers(SecurityEndpoints.AUTHENTICATED)
                        .authenticated())
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.accessDeniedHandler(customDeniedHandler);
                    exceptionHandling.authenticationEntryPoint(jsonAuthenticationEntryPoint);
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(customPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public CustomPasswordEncoder customPasswordEncoder() {
        return new CustomPasswordEncoder();
    }

}
