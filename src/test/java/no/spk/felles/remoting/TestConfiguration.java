package no.spk.felles.remoting;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

import java.io.IOException;

@Configuration
public class TestConfiguration {

    public static final int SERVER_PORT = 55888;

    @Bean()
    public HttpInvokerProxyFactoryBean httpInvokerServiceExporter() {
        HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        httpInvokerProxyFactoryBean.setServiceInterface(RemoteService.class);
        httpInvokerProxyFactoryBean.setServiceUrl("http://localhost:"+ SERVER_PORT +"/test");
        return httpInvokerProxyFactoryBean;
    }

    @Profile("mockServerBean")
    @Bean
    public RemotingMockServer remotingMockServer() throws IOException {
        return new RemotingMockServerImpl(SERVER_PORT);
    }
}
