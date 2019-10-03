package no.spk.felles.remoting;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.util.List;
import java.util.Optional;

public class MockServerExtension implements BeforeEachCallback, AfterEachCallback, TestInstancePostProcessor {
    private RemotingMockServer mockServer;
    private List<RemoteContext> remoteContexts;

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Optional<Object> testInstance = extensionContext.getTestInstance();
        if(testInstance.isEmpty() || !(testInstance.get() instanceof TestdataSupport))
            throw new RuntimeException("Missing TestdataSupport test instance");
        mockServer = new RemotingMockServerImpl(((TestdataSupport) testInstance.get()).getRemoteContexts());

        mockServer.start();
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        if(mockServer != null)
            mockServer.stop();
    }


    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        System.err.println(testInstance);
    }

    public void setRemoteContexts(List<RemoteContext> remoteContexts) {

        this.remoteContexts = remoteContexts;
    }
}
