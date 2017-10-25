package uk.gov.ofwat.external.service;

import io.github.jhipster.config.JHipsterProperties;
import org.hibernate.cfg.NotYetImplementedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.ofwat.external.CompanySharingExternalApp;
import uk.gov.ofwat.external.repository.UserRepository;
import uk.gov.service.notify.NotificationClient;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class NotifyServiceIntTest {

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private UserRepository userRepository;

    @Spy
    private NotificationClient notificationClient;

    @Autowired
    private JHipsterProperties jHipsterProperties;

    @Before
    public void setup() {
        notifyService = new NotifyService(notificationClient, userRepository);
    }

    @Test
    public void testSendNotification(){
        throw new NotYetImplementedException();
    }

}
