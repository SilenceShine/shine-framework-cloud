package io.github.SilenceShine.shine.cloud.httpinterface.webclient.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerBeanPostProcessorAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author SilenceShine
 * @since 1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(WebClient.class)
@AutoConfigureBefore(LoadBalancerBeanPostProcessorAutoConfiguration.class)
public class Resilience4jWebClientConfiguration {

    @Bean
    public Resilience4jWebClientBuilderBeanPostProcessor resilience4jWebClientBuilderBeanPostProcessor(
        ApplicationContext context,
        TimeLimiterRegistry timeLimiterRegistry,
        CircuitBreakerRegistry circuitBreakerRegistry) {
        return new Resilience4jWebClientBuilderBeanPostProcessor(context, timeLimiterRegistry, circuitBreakerRegistry);
    }

    @Bean
    public TimeLimiterRegistry timeLimiterRegistry() {
        return TimeLimiterRegistry.ofDefaults();
    }

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        return CircuitBreakerRegistry.ofDefaults();
    }

}
