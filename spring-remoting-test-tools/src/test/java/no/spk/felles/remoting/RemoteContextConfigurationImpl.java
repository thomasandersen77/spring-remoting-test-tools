package no.spk.felles.remoting;

import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;

@Service
public class RemoteContextConfigurationImpl implements RemoteContextConfiguration {
    @Override
    public List<RemoteContext> getConfiguration() {
        return asList(
                new RemoteContextImpl("/test", new TestResponse("test"))
        );
    }
}
