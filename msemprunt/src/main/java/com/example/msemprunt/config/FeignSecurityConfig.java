package com.example.msemprunt.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignSecurityConfig {

    @Bean
    public RequestInterceptor internalServiceInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("X-User-Email", "service@msemprunt");
            requestTemplate.header("X-User-Role", "SERVICE");
        };
    }
}