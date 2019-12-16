package no.spk.felles.remoting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Configuration
public class RemotingMockServerBeanTest {

    @Autowired
    private RemoteClient<TestResponse> remoteClient;

    @Test
    public void registerContextAndPerformClientRequest_test() {
        TestResponse entity = remoteClient.getEntity();
        assertTrue(() -> entity != null);
        assertEquals(entity.getStringEntity(), "test");
    }

    @EnableRemoteMockServer
    @Configuration
    public static class TestSpringConfiguration {
    }
}
