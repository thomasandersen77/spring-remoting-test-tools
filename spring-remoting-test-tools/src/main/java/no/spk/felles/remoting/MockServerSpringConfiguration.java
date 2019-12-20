package no.spk.felles.remoting;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Configuration
@ComponentScan
public class MockServerSpringConfiguration {

    @ConditionalOnBean(value = {RemoteContextConfiguration.class})
    @Bean
    public RemotingMockServer remotingMockServer(RemoteContextConfiguration remoteContextConfiguration) {
        return new RemotingMockServerImpl(remoteContextConfiguration.getConfiguration());
    }

    @ConditionalOnBean(value = {RemoteContextConfiguration.class})
    @Bean(name = "mockServerPort")
    public String serverPort(RemotingMockServer mockServer){
        System.setProperty("mock.server.port", "" + mockServer.getPort());
        return "" + mockServer.getPort();
    }

    @Bean
    @TestClient
    public HttpInvokerProxyFactoryBean httpInvokerServiceExporter(RemotingMockServer remotingMockServer) {
        HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        httpInvokerProxyFactoryBean.setServiceInterface(RemoteClient.class);
        httpInvokerProxyFactoryBean.setServiceUrl("http://localhost:" + remotingMockServer.getPort() + "/test");
        return httpInvokerProxyFactoryBean;
    }
}
