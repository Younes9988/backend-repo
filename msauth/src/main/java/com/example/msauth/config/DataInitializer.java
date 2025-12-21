package com.example.msauth.config;


import com.example.msauth.domain.AuthUser;
import com.example.msauth.domain.Role;
import com.example.msauth.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initUsers(AuthUserRepository repository) {
        return args -> {

            if (!repository.existsByEmail("admin@library.com")) {
                AuthUser admin = AuthUser.builder()
                        .email("admin@library.com")
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .enabled(true)
                        .build();

                repository.save(admin);
            }
        };
    }
}