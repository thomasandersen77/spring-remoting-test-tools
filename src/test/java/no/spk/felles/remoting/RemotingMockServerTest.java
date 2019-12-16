package no.spk.felles.remoting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockServerExtension.class)
public class RemotingMockServerTest implements TestdataSupport {

    @Test
    public void registerContextsAndPerformClientRequest() {

        TestResponse entity = (TestResponse) HttpInvokerProxyFactory.proxy(RemoteClient.class, "test").getEntity();
        assertTrue(() -> entity != null);
        assertEquals(entity.getStringEntity(), "test");
    }

    @Override
    public List<RemoteContext> getRemoteContexts() {
        return asList(
                new RemoteContextImpl("/test", new TestResponse("test")),
                new RemoteContextImpl("/person", new TestResponse("person")),
                new RemoteContextImpl("/event", new TestResponse("event"))
        );
    }
}
