package io.github.SilenceShine.shine.cloud.httpinterface;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author SilenceShine
 * @since 1.0
 */
public abstract class AbstractHttpExchangeFactoryBean implements FactoryBean<Object>, InitializingBean, ApplicationContextAware {

    protected Class<?> type;

    protected ApplicationContext applicationContext;

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    @Override
    public Class<?> getObjectType() {
        return this.type;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }

}
