package no.spk.felles.remoting;

import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

public class HttpInvokerProxyFactory {
    @SuppressWarnings("unchecked")
    public static <T> T proxy(Class<T> tClass, String path) {
        HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        httpInvokerProxyFactoryBean.setServiceInterface(tClass);
        httpInvokerProxyFactoryBean.setServiceUrl("http://localhost:" + System.getProperty("mock.port") + "/" + path);
        httpInvokerProxyFactoryBean.afterPropertiesSet();
        return (T) httpInvokerProxyFactoryBean.getObject();
    }
}
