package uk.gov.ofwat.external.service;

import io.github.jhipster.config.JHipsterProperties;
import org.hibernate.cfg.NotYetImplementedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.ofwat.external.CompanySharingExternalApp;
import uk.gov.ofwat.external.config.ApplicationProperties;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.domain.message.NotifyMessageTemplate;
import uk.gov.ofwat.external.repository.NotifyMessageTemplateRepository;
import uk.gov.ofwat.external.repository.UserRepository;
import uk.gov.service.notify.NotificationClient;
import uk.gov.service.notify.NotificationClientException;
import uk.gov.service.notify.SendEmailResponse;
import uk.gov.service.notify.SendSmsResponse;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class NotifyServiceIntTest {

    @Autowired
    private UserRepository userRepository;

    @Captor
    ArgumentCaptor<String> templateIdCaptor;

    @Captor
    ArgumentCaptor<String> emailAddressCaptor;

    @Captor
    ArgumentCaptor<String> telephoneNumberCaptor;

    @Captor
    ArgumentCaptor<HashMap<String, String>> personalisationCaptor;

    @Captor
    ArgumentCaptor<String> referenceCaptor;

    @Autowired
    private ApplicationProperties applicationProperties;

    private NotificationClient notificationClient;

    @Autowired
    NotifyMessageTemplateRepository notifyMessageTemplateRepository;

    private NotifyService notifyService;

    @Mock
    private SendSmsResponse sendSmsResponse;

    @Mock
    private SendEmailResponse sendEmailResponse;

    @Before
    public void setup() throws NotificationClientException {
        notificationClient = Mockito.spy(new NotificationClient(applicationProperties.getNotify().getApiKey()));
        MockitoAnnotations.initMocks(this);
        //String templateId, String phoneNumber, Map<String, String> personalisation, String reference
        //notificationClient = new NotificationClient(applicationProperties.getNotify().getApiKey());
        doReturn(sendSmsResponse).when(notificationClient).sendSms(any(String.class), any(String.class), any(Map.class), any(String.class));
        doReturn(sendEmailResponse).when(notificationClient).sendEmail(any(String.class), any(String.class), any(Map.class), any(String.class));
/*        when(notificationClient.sendSms(any(String.class), any(String.class), any(Map.class), any(String.class))).thenReturn(null);
        when(notificationClient.sendEmail(any(String.class), any(String.class), any(Map.class), any(String.class))).thenReturn(null);*/
        notifyService = new NotifyService(notificationClient, userRepository);
    }
    /*
    id;description;name;template_id;type
1;Test email template;test email;478d07b5-4d5c-44bc-8287-5803e23973c8;EMAIL
2;OTP code message;OTP Code;85969360-29f8-4d3b-a30f-7de2ebd88979;SMS
3;Password reset email;Password reset user;4cbb33f2-aba7-4a6a-af68-06f06d7f7888;EMAIL
4;Account activation mail sent to the user when approved by admin;Account activation user;8fc57a73-10c3-475f-89f3-75873cca1a3c;EMAIL
5;Account request email sent to admin;Account request admin;47348f24-3915-4a96-84c1-b1d7d1ffeb72;EMAIL
6;Account request email sent to user;Account request user;caca3ac2-60c9-4945-8018-476978e5714a;EMAIL
7;Welcome email sent on user signup;Welcome user;26bf6490-93a7-4b99-9fd0-69d1ae468fd4;EMAIL
    */

    @Test
    public void sendSmsForUser(){
        User user = userRepository.findOneByLogin("user").get();
        NotifyMessageTemplate notifyMessageTemplate = notifyMessageTemplateRepository.findOneByName("OTP Code").get();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(NotifyService.MOBILE_TELEPHONE_NUMBER, "07967077853");
        params.put(NotifyService.REFERENCE_NUMBER, "Ref1");
        notifyService.sendMessage(user, notifyMessageTemplate, params);
        try {
            Mockito.verify(notificationClient).sendSms(templateIdCaptor.capture(), telephoneNumberCaptor.capture(), personalisationCaptor.capture(), referenceCaptor.capture());
            assertThat(templateIdCaptor.getValue()).isEqualToIgnoringCase(notifyMessageTemplate.getTemplateId());
            assertThat(telephoneNumberCaptor.getValue()).isEqualToIgnoringCase(params.get(NotifyService.MOBILE_TELEPHONE_NUMBER));
            assertThat(referenceCaptor.getValue()).isEqualToIgnoringCase(params.get(NotifyService.REFERENCE_NUMBER));
        } catch (NotificationClientException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void shouldSendEmailForUser(){
        User user = userRepository.findOneByLogin("user").get();
        NotifyMessageTemplate notifyMessageTemplate = notifyMessageTemplateRepository.findOneByName("test email").get();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(NotifyService.REFERENCE_NUMBER, "Ref1");
        params.put(NotifyService.EMAIL, user.getEmail());
        notifyService.sendMessage(user, notifyMessageTemplate, params);
        try {
            Mockito.verify(notificationClient).sendEmail(templateIdCaptor.capture(), emailAddressCaptor.capture(), personalisationCaptor.capture(), referenceCaptor.capture());
            assertThat(emailAddressCaptor.getValue()).isEqualToIgnoringCase(user.getEmail());
            assertThat(templateIdCaptor.getValue()).isEqualToIgnoringCase(notifyMessageTemplate.getTemplateId());
            assertThat(referenceCaptor.getValue()).isEqualToIgnoringCase("Ref1");
        } catch (NotificationClientException e) {
            Assert.fail(e.getMessage());
        }
    }

    public void sendSmsWithoutUser(){
        NotifyMessageTemplate notifyMessageTemplate = notifyMessageTemplateRepository.findOneByName("OTP Code").get();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(NotifyService.MOBILE_TELEPHONE_NUMBER, "07967077853");
        params.put(NotifyService.REFERENCE_NUMBER, "Ref1");
        notifyService.sendMessage(notifyMessageTemplate, params);
        try {
            Mockito.verify(notificationClient).sendSms(templateIdCaptor.capture(), telephoneNumberCaptor.capture(), personalisationCaptor.capture(), referenceCaptor.capture());
            assertThat(templateIdCaptor.getValue()).isEqualToIgnoringCase(notifyMessageTemplate.getTemplateId());
            assertThat(telephoneNumberCaptor.getValue()).isEqualToIgnoringCase(params.get(NotifyService.MOBILE_TELEPHONE_NUMBER));
            assertThat(referenceCaptor.getValue()).isEqualToIgnoringCase(params.get(NotifyService.REFERENCE_NUMBER));
        } catch (NotificationClientException e) {
            Assert.fail(e.getMessage());
        }
    }

    public void shouldSendEmailWithoutUser(){
        NotifyMessageTemplate notifyMessageTemplate = notifyMessageTemplateRepository.findOneByName("test email").get();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(NotifyService.REFERENCE_NUMBER, "Ref1");
        params.put(NotifyService.EMAIL, "test@test.com");
        notifyService.sendMessage(notifyMessageTemplate, params);
        try {
            Mockito.verify(notificationClient).sendEmail(templateIdCaptor.capture(), emailAddressCaptor.capture(), personalisationCaptor.capture(), referenceCaptor.capture());
            assertThat(emailAddressCaptor.getValue()).isEqualToIgnoringCase("test@test.com");
            assertThat(templateIdCaptor.getValue()).isEqualToIgnoringCase(notifyMessageTemplate.getTemplateId());
            assertThat(referenceCaptor.getValue()).isEqualToIgnoringCase("Ref1");
        } catch (NotificationClientException e) {
            Assert.fail(e.getMessage());
        }
    }

    public void shouldSendOtpCode(){
        User user = userRepository.findOneByLogin("user").get();
        NotifyMessageTemplate notifyMessageTemplate = notifyMessageTemplateRepository.findOneByName("OTP Code").get();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(NotifyService.MOBILE_TELEPHONE_NUMBER, "07967077853");
        params.put(NotifyService.REFERENCE_NUMBER, "Ref1");
        notifyService.sendMessage(user, notifyMessageTemplate, params);
        try {
            Mockito.verify(notificationClient).sendSms(templateIdCaptor.capture(), telephoneNumberCaptor.capture(), personalisationCaptor.capture(), referenceCaptor.capture());
            assertThat(templateIdCaptor.getValue()).isEqualToIgnoringCase(notifyMessageTemplate.getTemplateId());
            assertThat(telephoneNumberCaptor.getValue()).isEqualToIgnoringCase(params.get(NotifyService.MOBILE_TELEPHONE_NUMBER));
            assertThat(referenceCaptor.getValue()).isEqualToIgnoringCase(params.get(NotifyService.REFERENCE_NUMBER));
        } catch (NotificationClientException e) {
            Assert.fail(e.getMessage());
        }
    }

    public void shouldSendPasswordResetEmail(){
        User user = userRepository.findOneByLogin("user").get();
        NotifyMessageTemplate notifyMessageTemplate = notifyMessageTemplateRepository.findOneByName("Password reset user").get();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(NotifyService.REFERENCE_NUMBER, "Ref1");
        params.put(NotifyService.EMAIL, user.getEmail());
        notifyService.sendMessage(user, notifyMessageTemplate, params);
        try {
            Mockito.verify(notificationClient).sendEmail(templateIdCaptor.capture(), emailAddressCaptor.capture(), personalisationCaptor.capture(), referenceCaptor.capture());
            assertThat(emailAddressCaptor.getValue()).isEqualToIgnoringCase(user.getEmail());
            assertThat(templateIdCaptor.getValue()).isEqualToIgnoringCase(notifyMessageTemplate.getTemplateId());
            assertThat(referenceCaptor.getValue()).isEqualToIgnoringCase("Ref1");
        } catch (NotificationClientException e) {
            Assert.fail(e.getMessage());
        }
    }

    public void shouldSendActivationEmail(){
        User user = userRepository.findOneByLogin("user").get();
        NotifyMessageTemplate notifyMessageTemplate = notifyMessageTemplateRepository.findOneByName("Account activation user").get();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(NotifyService.REFERENCE_NUMBER, "Ref1");
        params.put(NotifyService.EMAIL, user.getEmail());
        notifyService.sendMessage(user, notifyMessageTemplate, params);
        try {
            Mockito.verify(notificationClient).sendEmail(templateIdCaptor.capture(), emailAddressCaptor.capture(), personalisationCaptor.capture(), referenceCaptor.capture());
            assertThat(emailAddressCaptor.getValue()).isEqualToIgnoringCase(user.getEmail());
            assertThat(templateIdCaptor.getValue()).isEqualToIgnoringCase(notifyMessageTemplate.getTemplateId());
            assertThat(referenceCaptor.getValue()).isEqualToIgnoringCase("Ref1");
        } catch (NotificationClientException e) {
            Assert.fail(e.getMessage());
        }
    }

    public void shouldSendRequestEmailUser(){
        User user = userRepository.findOneByLogin("user").get();
        NotifyMessageTemplate notifyMessageTemplate = notifyMessageTemplateRepository.findOneByName("Account request admin").get();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(NotifyService.REFERENCE_NUMBER, "Ref1");
        params.put(NotifyService.EMAIL, user.getEmail());
        notifyService.sendMessage(user, notifyMessageTemplate, params);
        try {
            Mockito.verify(notificationClient).sendEmail(templateIdCaptor.capture(), emailAddressCaptor.capture(), personalisationCaptor.capture(), referenceCaptor.capture());
            assertThat(emailAddressCaptor.getValue()).isEqualToIgnoringCase(user.getEmail());
            assertThat(templateIdCaptor.getValue()).isEqualToIgnoringCase(notifyMessageTemplate.getTemplateId());
            assertThat(referenceCaptor.getValue()).isEqualToIgnoringCase("Ref1");
        } catch (NotificationClientException e) {
            Assert.fail(e.getMessage());
        }
    }

    public void shouldSendRequestEmailAdmin(){
        User user = userRepository.findOneByLogin("user").get();
        NotifyMessageTemplate notifyMessageTemplate = notifyMessageTemplateRepository.findOneByName("Account request user").get();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(NotifyService.REFERENCE_NUMBER, "Ref1");
        params.put(NotifyService.EMAIL, user.getEmail());
        notifyService.sendMessage(user, notifyMessageTemplate, params);
        try {
            Mockito.verify(notificationClient).sendEmail(templateIdCaptor.capture(), emailAddressCaptor.capture(), personalisationCaptor.capture(), referenceCaptor.capture());
            assertThat(emailAddressCaptor.getValue()).isEqualToIgnoringCase(user.getEmail());
            assertThat(templateIdCaptor.getValue()).isEqualToIgnoringCase(notifyMessageTemplate.getTemplateId());
            assertThat(referenceCaptor.getValue()).isEqualToIgnoringCase("Ref1");
        } catch (NotificationClientException e) {
            Assert.fail(e.getMessage());
        }
    }

    public void shouldSendWelconeEmail(){
        User user = userRepository.findOneByLogin("user").get();
        NotifyMessageTemplate notifyMessageTemplate = notifyMessageTemplateRepository.findOneByName("Welcome user").get();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(NotifyService.REFERENCE_NUMBER, "Ref1");
        params.put(NotifyService.EMAIL, user.getEmail());
        notifyService.sendMessage(user, notifyMessageTemplate, params);
        try {
            Mockito.verify(notificationClient).sendEmail(templateIdCaptor.capture(), emailAddressCaptor.capture(), personalisationCaptor.capture(), referenceCaptor.capture());
            assertThat(emailAddressCaptor.getValue()).isEqualToIgnoringCase(user.getEmail());
            assertThat(templateIdCaptor.getValue()).isEqualToIgnoringCase(notifyMessageTemplate.getTemplateId());
            assertThat(referenceCaptor.getValue()).isEqualToIgnoringCase("Ref1");
        } catch (NotificationClientException e) {
            Assert.fail(e.getMessage());
        }
    }

}
