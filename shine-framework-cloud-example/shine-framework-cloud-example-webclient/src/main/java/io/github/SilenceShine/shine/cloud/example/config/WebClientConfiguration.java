package io.github.SilenceShine.shine.cloud.example.config;

import io.github.SilenceShine.shine.cloud.httpinterface.webclient.resilience4j.Resilience4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

/**
 * @author SilenceShine
 * @since 1.0
 */
@Configuration
public class WebClientConfiguration {

    @Bean
    @LoadBalanced
    @Resilience4j
    @ConditionalOnMissingBean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @HttpExchange("http://basic-id")
    public interface TestClient {

        @GetExchange("/uid/single")
        Mono<ResponseEntity<Object>> single();

    }

}
