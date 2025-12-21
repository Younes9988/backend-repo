package com.example.msutilisateur.config;

import com.example.msutilisateur.security.GatewayAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // 🔓 allow internal registration
                        .requestMatchers("/api/utilisateurs/lecteur").permitAll()

                        // 🔒 everything else stays protected
                        .requestMatchers("/api/utilisateurs/**").hasRole("BIBLIOTHECAIRE")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        new GatewayAuthFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}