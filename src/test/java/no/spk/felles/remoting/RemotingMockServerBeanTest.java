package no.spk.felles.remoting;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RemotingMockServerBeanTest.TestSpringConfiguration.class)
@ActiveProfiles("mockServerBean")
public class RemotingMockServerBeanTest {

    @Autowired
    private RemoteService remoteServiceTest;
    @Autowired
    private RemoteService remoteServicePerson;
    @Autowired
    private RemoteService remoteServiceEvent;

    @Test
    public void registerContextAndPerformClientRequest_test() {
        TestResponse entity = (TestResponse) remoteServiceTest.getEntity();
        assertTrue(() -> entity != null);
        assertEquals(entity.getStringEntity(), "test");
    }

    @Test
    public void registerContextAndPerformClientRequest_person() {
        TestResponse entity = (TestResponse) remoteServicePerson.getEntity();
        assertTrue(() -> entity != null);
        assertEquals(entity.getStringEntity(), "person");
    }

    @Test
    public void registerContextAndPerformClientRequest_event() {
        TestResponse entity = (TestResponse) remoteServiceEvent.getEntity();
        assertTrue(() -> entity != null);
        assertEquals(entity.getStringEntity(), "event");
    }

    @Profile("mockServerBean")
    @Configuration
    public static class TestSpringConfiguration {

        @Bean(name = "remoteServiceTest")
        public HttpInvokerProxyFactoryBean httpInvokerServiceExporter1(@Autowired RemotingMockServer remotingMockServer) {
            HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
            httpInvokerProxyFactoryBean.setServiceInterface(RemoteService.class);
            httpInvokerProxyFactoryBean.setServiceUrl("http://localhost:" + PortUtil.getPort() + "/test");
            return httpInvokerProxyFactoryBean;
        }

        @Bean(name = "remoteServiceEvent")
        public HttpInvokerProxyFactoryBean httpInvokerServiceExporter2(@Autowired(required = true) RemotingMockServer remotingMockServer) {
            HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
            httpInvokerProxyFactoryBean.setServiceInterface(RemoteService.class);
            httpInvokerProxyFactoryBean.setServiceUrl("http://localhost:" + PortUtil.getPort() + "/event");
            return httpInvokerProxyFactoryBean;
        }

        @Bean(name = "remoteServicePerson")
        public HttpInvokerProxyFactoryBean httpInvokerServiceExporter3(@Autowired(required = true) RemotingMockServer remotingMockServer) {
            HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
            httpInvokerProxyFactoryBean.setServiceInterface(RemoteService.class);
            httpInvokerProxyFactoryBean.setServiceUrl("http://localhost:" + PortUtil.getPort() + "/person");
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
}
