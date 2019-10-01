package no.spk.felles.remoting;

import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

public class RemoteServiceFactory {
    public static <T> T proxy(Class<T> tClass, int port) {
        HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        httpInvokerProxyFactoryBean.setServiceInterface(tClass);
        httpInvokerProxyFactoryBean.setServiceUrl("http://localhost:" + port + "/test");
        httpInvokerProxyFactoryBean.afterPropertiesSet();
        return (T) httpInvokerProxyFactoryBean.getObject();
    }
}
