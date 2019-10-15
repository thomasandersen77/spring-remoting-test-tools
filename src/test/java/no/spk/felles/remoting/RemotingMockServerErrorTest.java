package no.spk.felles.remoting;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.remoting.RemoteAccessException;

@ExtendWith(MockServerExtension.class)
public class RemotingMockServerErrorTest implements TestdataSupport {

    @Test
    public void registerContextsAndPerformClientRequest() {
        assertThrows(RemoteAccessException.class, () -> HttpInvokerProxyFactory.proxy(RemoteService.class, PortUtil.getPort(), "test").getEntity());
    }

    @Override
    public List<RemoteContext> getRemoteContexts() {
        return asList(
                new RemoteContextImpl("/test", new IllegalArgumentException("IllegalArgumentException"))
        );
    }
}
