package no.spk.felles.remoting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestSpringConfiguration.class)
@ActiveProfiles("mockServerBean")
public class RemotingMockServerBeanTest {

    @Autowired
    private RemoteService remoteService;

    @Test
    public void registerContextsAndPerformClientRequest() {

        TestResponse entity = (TestResponse) remoteService.getEntity();
        assertTrue(() -> entity != null);
        assertEquals(entity.getStringEntity(), "test");

    }
}
