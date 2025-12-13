package com.example.reactivegateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class MyGlobalLogFilter implements GlobalFilter, Ordered {
    /// Spring Cloud Gateway utilise WebFlux (réactif), donc tout est non bloquant.
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /// Traitement avant l’envoi de la requête
        ///ServerWebExchange exchange : Représente la requête et la réponse HTTP.
        /// C’est un contexte complet qui contient tout ce qui concerne la requête entrante et la réponse sortante.
        String url = exchange.getRequest().getURI().toString();
        System.out.println("MyGlobalLogFilter : Requête interceptée ! URL : {}"+ url);
        return chain.filter(exchange);
        /// Traitement après la réponse
        /// En retournant chain.filter(exchange), le filtre indique qu'il a terminé son travail (dans ce cas, loguer l'URL de la requête)
        ///et qu'il passe la requête au prochain filtre de la chaîne
    }
    @Override
    public int getOrder() {
        return -1; /// Ordre de priorité des filtres, ici c'est un filtre très tôt dans la chaîne
    }
}
