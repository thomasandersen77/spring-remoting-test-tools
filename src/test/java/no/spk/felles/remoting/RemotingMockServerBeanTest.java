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
}
