package no.spk.felles.remoting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RemotingMockServerTest {

    @Autowired
    private RemoteService remoteService;

    @Test
    public void registerContextsAndPerformClientRequest() throws IOException {
        List<RemoteContext> remoteContexts = asList(
                new RemoteContextImpl("/test", new TestResponse("test")),
                new RemoteContextImpl("/person", new TestResponse("person")),
                new RemoteContextImpl("/event", new TestResponse("event"))
        );

        RemotingMockServer mockServer = new RemotingMockServerImpl(TestConfiguration.SERVER_PORT, remoteContexts);
        mockServer.start();

        TestResponse entity = (TestResponse) remoteService.getEntity();
        assertTrue(() -> entity != null);
        assertEquals(entity.getStringEntity(), "test");

        mockServer.stop();
    }
}
