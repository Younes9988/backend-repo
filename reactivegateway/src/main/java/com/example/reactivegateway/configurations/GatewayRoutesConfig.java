package com.example.reactivegateway.configurations;

import com.example.reactivegateway.filters.CustomGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("msauth-route", r -> r
                        .path("/AUTH/**")
                        .filters(f -> f
                                .rewritePath("/AUTH/(?<remaining>.*)", "/${remaining}")
                                .addRequestHeader("X-Request-Origin", "Gateway")
                        )
                        .uri("lb://MSAUTH")
                )
                // Route for msutilisateur
                .route("msutilisateur-route", r -> r
                        .path("/MSUTILISATEUR/**")
                        .filters(f -> f
                                .rewritePath("/MSUTILISATEUR/(?<remaining>.*)", "/${remaining}")
                                .addRequestHeader("X-Request-Origin", "Gateway")
                                .filter(new CustomGatewayFilter())
                        )
                        .uri("lb://MSUTILISATEUR")
                )

                // Route for mslivre
                .route("mslivre-route", r -> r
                        .path("/MSLIVRE/**")
                        .filters(f -> f
                                .rewritePath("/MSLIVRE/(?<remaining>.*)", "/${remaining}")
                                .addRequestHeader("X-Request-Origin", "Gateway")
                                .filter(new CustomGatewayFilter())
                        )
                        .uri("lb://MSLIVRE")
                )
                // Route for msemprunt
                .route("msemprunt-route", r -> r
                        .path("/MSEMPRUNT/**")
                        .filters(f -> f
                                .rewritePath("/MSEMPRUNT/(?<remaining>.*)", "/${remaining}")
                                .addRequestHeader("X-Request-Origin", "Gateway")
                                .filter(new CustomGatewayFilter())
                        )
                        .uri("lb://MSEMPRUNT")
                )
                .build();
    }
}
