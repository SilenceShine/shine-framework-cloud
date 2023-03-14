package io.github.SilenceShine.shine.cloud.httpinterface.webclient.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.reactor.timelimiter.TimeLimiterOperator;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

/**
 * @author SilenceShine
 * @since 1.0
 */
public class Resilience4jWebClientBuilderBeanPostProcessor implements BeanPostProcessor {

    private final ApplicationContext context;
    private final TimeLimiterRegistry timeLimiterRegistry;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public Resilience4jWebClientBuilderBeanPostProcessor(ApplicationContext context,
                                                         TimeLimiterRegistry timeLimiterRegistry,
                                                         CircuitBreakerRegistry circuitBreakerRegistry) {
        this.context = context;
        this.timeLimiterRegistry = timeLimiterRegistry;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof WebClient.Builder builder) {
            if (null == context.findAnnotationOnBean(beanName, Resilience4j.class)) {
                return bean;
            }
            builder.filter((request, next) -> {
                URI originalUrl = request.url();
                String serviceId = originalUrl.getHost();
                TimeLimiter timeLimiter = timeLimiterRegistry.getConfiguration(serviceId)
                        .map(config -> timeLimiterRegistry.timeLimiter(serviceId, config))
                        .orElseGet(() -> timeLimiterRegistry.timeLimiter(serviceId));
                CircuitBreaker circuitBreaker = circuitBreakerRegistry.getConfiguration(serviceId)
                        .map(config -> circuitBreakerRegistry.circuitBreaker(serviceId, config))
                        .orElseGet(() -> circuitBreakerRegistry.circuitBreaker(serviceId));
                return next.exchange(request)
                        .transformDeferred(TimeLimiterOperator.of(timeLimiter))
                        .transformDeferred(CircuitBreakerOperator.of(circuitBreaker));
            });
        }
        return bean;
    }

}
