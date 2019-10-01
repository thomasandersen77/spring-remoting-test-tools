package no.spk.felles.remoting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Configuration
public class TestConfiguration {

    public static Integer SERVER_PORT = null;

    @Bean()
    public HttpInvokerProxyFactoryBean httpInvokerServiceExporter() {
        HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        httpInvokerProxyFactoryBean.setServiceInterface(RemoteService.class);
        httpInvokerProxyFactoryBean.setServiceUrl("http://localhost:" + SERVER_PORT + "/test");
        return httpInvokerProxyFactoryBean;
    }

}
