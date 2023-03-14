package io.github.SilenceShine.shine.cloud.httpinterface.webclient;

import io.github.SilenceShine.shine.cloud.httpinterface.AbstractHttpExchangeFactoryBean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * @author SilenceShine
 * @since 1.0
 */
public class WebClientHttpExchangeFactoryBean extends AbstractHttpExchangeFactoryBean {

    @Override
    public Object getObject() throws Exception {
        WebClient.Builder builder = applicationContext.getBean(WebClient.Builder.class);
        return HttpServiceProxyFactory.builder(WebClientAdapter.forClient(builder.build()))
            .build()
            .createClient(type);
    }

}
