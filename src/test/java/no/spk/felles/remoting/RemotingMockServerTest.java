package no.spk.felles.remoting;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class RemotingMockServerTest {

    @Test
    public void registerContextsAndPerformClientRequest() {

        List<RemoteContext> remoteContexts = asList(
                new RemoteContextImpl("/test", new TestResponse("test")),
                new RemoteContextImpl("/person", new TestResponse("person")),
                new RemoteContextImpl("/event", new TestResponse("event"))
        );

        RemotingMockServer mockServer = new RemotingMockServerImpl(remoteContexts);
        mockServer.start();

        TestResponse entity = (TestResponse) HttpInvokerProxyFactory.proxy(RemoteService.class, mockServer.getPort(), "test").getEntity();
        assertTrue(() -> entity != null);
        assertEquals(entity.getStringEntity(), "test");

        mockServer.stop();
    }
}
