package no.spk.felles.remoting;

import static java.util.Arrays.asList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Profile("mockServerBean")
@Configuration
public class TestSpringConfiguration {

    @Bean
    public HttpInvokerProxyFactoryBean httpInvokerServiceExporter(@Autowired(required = true) RemotingMockServer remotingMockServer) {
        HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        httpInvokerProxyFactoryBean.setServiceInterface(RemoteService.class);
        httpInvokerProxyFactoryBean.setServiceUrl("http://localhost:" + remotingMockServer.getPort() + "/test");
        return httpInvokerProxyFactoryBean;
    }

    @Bean
    public RemotingMockServer remotingMockServer() {
        List<RemoteContext> remoteContexts = asList(
                new RemoteContextImpl("/test", new TestResponse("test")),
                new RemoteContextImpl("/person", new TestResponse("person")),
                new RemoteContextImpl("/event", new TestResponse("event"))
        );
        return new RemotingMockServerImpl(remoteContexts);
    }
}