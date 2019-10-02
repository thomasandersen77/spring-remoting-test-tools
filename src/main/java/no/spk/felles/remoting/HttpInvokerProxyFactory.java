package no.spk.felles.remoting;

import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

public class HttpInvokerProxyFactory {
    public static <T> T proxy(Class<T> tClass, int port, String path) {
        HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        httpInvokerProxyFactoryBean.setServiceInterface(tClass);
        httpInvokerProxyFactoryBean.setServiceUrl("http://localhost:" + port + "/" + path);
        httpInvokerProxyFactoryBean.afterPropertiesSet();
        return (T) httpInvokerProxyFactoryBean.getObject();
    }
}
