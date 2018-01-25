package uk.gov.ofwat.external.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.ofwat.external.CompanySharingExternalApp;
import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.domain.message.NotifyMessageTemplate;
import uk.gov.ofwat.external.repository.NotifyMessageTemplateRepository;
import uk.gov.ofwat.external.repository.UserRepository;
import uk.gov.service.notify.NotificationClient;

import javax.transaction.Transactional;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
@Transactional
public class OTPServiceIntTest {

    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    NotifyMessageTemplateRepository notifyMessageTemplateRepository;

    @Autowired
    UserService userService;

    @Spy
    NotifyService notifyService = new NotifyService(notificationClient, userRepository);

    OTPService otpService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        doNothing().when(notifyService).sendMessage(any(User.class), any(NotifyMessageTemplate.class), any(HashMap.class));
        doNothing().when(notifyService).sendMessage(any(NotifyMessageTemplate.class), any(HashMap.class));
        otpService = new OTPService(notifyMessageTemplateRepository, userRepository, notifyService);
    }


    @Test
    public void shouldGenerateAnOtpCode(){
        User user = userService.createUser("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "http://placehold.it/50x50", "en-US", "077777077852");
        otpService.generateOtpCode(user);
        NotifyMessageTemplate notifyMessageTemplate = notifyMessageTemplateRepository.findOneByName("OTP Code").get();
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("code", user.getOtpCode());
        assertThat(user.getEnabled()).isEqualTo(false);
        assertThat(user.getOtpCode()).isNotEmpty();
        //Test OTP Code for validity.
        verify(notifyService, times(1)).sendMessage(user, notifyMessageTemplate, properties);
    }

    public void shouldValidateAnOtpCode(){
        User user = userService.createUser("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "http://placehold.it/50x50", "en-US", "077777077852");
        otpService.generateOtpCode(user);
        NotifyMessageTemplate notifyMessageTemplate = notifyMessageTemplateRepository.findOneByName("OTP Code").get();
        assertThat(user.getOtpCode()).isNotEmpty();

        Boolean valid = otpService.verifyOtpCode(user, user.getOtpCode());
        assertThat(valid).isTrue();
        valid = otpService.verifyOtpCode(user, "INVALID_CODE");
        assertThat(valid).isFalse();
    }

    public void shouldResetUsersOtpCount(){
        User user = userService.createUser("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "http://placehold.it/50x50", "en-US", "077777077852");
        user.setOtpSentCount(100);
        userRepository.save(user);
        otpService.resetOtpCount(user);
        user = userRepository.findOneByLogin("johndoe").get();
        assertThat(user.getOtpSentCount()).isZero();
    }


}
