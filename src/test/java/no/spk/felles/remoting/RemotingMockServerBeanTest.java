package no.spk.felles.remoting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("mockServerBean")
public class RemotingMockServerBeanTest {

    @Autowired
    private RemotingMockServer remotingMockServer;
    @Autowired
    private RemoteService remoteService;

    @Test
    public void registerContextsAndPerformClientRequest() {
        List<RemoteContext> remoteContexts = asList(
                new RemoteContextImpl("/test", new TestResponse("test")),
                new RemoteContextImpl("/person", new TestResponse("person")),
                new RemoteContextImpl("/event", new TestResponse("event"))
        );

        TestResponse entity = (TestResponse) remoteService.getEntity();
        assertTrue(() -> entity != null);
        assertEquals(entity.getStringEntity(), "test");

        remotingMockServer.stop();
    }
}
