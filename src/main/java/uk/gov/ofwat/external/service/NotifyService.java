package uk.gov.ofwat.external.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.domain.message.MessageConstants;
import uk.gov.ofwat.external.domain.message.NotifyMessageTemplate;
import uk.gov.ofwat.external.repository.UserRepository;
import uk.gov.service.notify.NotificationClient;
import uk.gov.service.notify.NotificationClientException;
import uk.gov.service.notify.SendEmailResponse;
import uk.gov.service.notify.SendSmsResponse;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotifyService {

    private final Logger log = LoggerFactory.getLogger(NotifyService.class);

    private final NotificationClient notificationClient;

    private final UserRepository userRepository;

    private final Integer SMS_COUNT = 10;

    public static final String REFERENCE_NUMBER = "reference_number";

    public static final String EMAIL = "email";

    public static final String MOBILE_TELEPHONE_NUMBER = "mobile_telephone_number";

    public NotifyService(NotificationClient notificationClient, UserRepository userRepository){
        this.notificationClient = notificationClient;
        this.userRepository = userRepository;
    }

    /**
     * Send a message through GDS Notify.
     * @param user
     * @param notifyMessageTemplate
     * @param personalisation
     */
    @Async
    public void sendMessage(User user, NotifyMessageTemplate notifyMessageTemplate, HashMap<String, String> personalisation) {
        //sendMessageToNotify(notifyMessageTemplate, personalisation);
        if(notifyMessageTemplate.getType().equals(MessageConstants.SMS)) {
            personalisation = buildPersonalisation(user, personalisation);
            if(checkPersonalisationForValidMobile(personalisation)){
                sendNotifySms(user, notifyMessageTemplate, personalisation, buildReference(personalisation));
            }else{
                log.error("SMS Message could not be sent with reference '{}'", personalisation.get(REFERENCE_NUMBER));
            }
        }else{
            if(checkPersonalisationForValidEmail(personalisation)) {
                sendNotifyEmail(notifyMessageTemplate, personalisation, buildReference(personalisation));
            }else{
                log.error("Email could not be sent with reference '{}'", personalisation.get(REFERENCE_NUMBER));
            }
        }
    }

    /**
     * Send a message through GDS Notify.
     * @param notifyMessageTemplate
     * @param personalisation
     */
    @Async
    public void sendMessage(NotifyMessageTemplate notifyMessageTemplate, HashMap<String, String> personalisation){
        //sendMessageToNotify(notifyMessageTemplate, personalisation);
        if(notifyMessageTemplate.getType().equals(MessageConstants.SMS)) {
            if(checkPersonalisationForValidMobile(personalisation)){
                sendNotifySms(notifyMessageTemplate, personalisation, buildReference(personalisation));
            }else{
                log.error("SMS Message could not be sent with reference '{}'", personalisation.get(REFERENCE_NUMBER));
            }
        }else{
            if(checkPersonalisationForValidEmail(personalisation)) {
                sendNotifyEmail(notifyMessageTemplate, personalisation, buildReference(personalisation));
            }else{
                log.error("Email could not be sent with reference '{}'", personalisation.get(REFERENCE_NUMBER));
            }
        }
    }

    /**
     * Build up the reference required for the communication.
     * @return
     */
    private String buildReference(HashMap<String, String> personalisation){
        if(personalisation.get(REFERENCE_NUMBER) == null){
        return "REFERENCE_1";
        }else{
            return personalisation.get(REFERENCE_NUMBER);
        }
    }

    private HashMap<String, String> buildPersonalisation(User user, HashMap<String, String> personalisation){
        personalisation.put(EMAIL, user.getEmail());
        personalisation.put(MOBILE_TELEPHONE_NUMBER, user.getMobileTelephoneNumber());
        return personalisation;
    }

    private Boolean checkPersonalisationForValidMobile(HashMap<String, String> personalisation){
        return personalisation.entrySet().stream().filter(e -> e.getKey().equals(MOBILE_TELEPHONE_NUMBER) && !e.getValue().isEmpty()).map(Map.Entry::getValue).findAny().isPresent();
    }

    private Boolean checkPersonalisationForValidEmail(HashMap<String, String> personalisation){
        return personalisation.entrySet().stream().filter(e -> e.getKey().equals(EMAIL) && !e.getValue().isEmpty()).map(Map.Entry::getValue).findAny().isPresent();
    }


    private void sendNotifySms(User user, NotifyMessageTemplate notifyMessageTemplate, HashMap<String, String> personalisation, String reference){
        if(canSendOtp(user)){
            sendNotifySms(notifyMessageTemplate, personalisation, reference);
            userRepository.save(updateOtpCount(user));
        } else {
            log.warn("SMS Message could not be sent to user '{}': {} as max number of SMS in time period exceeded.", user.getLogin());
        }
    }

    private void sendNotifySms(NotifyMessageTemplate notifyMessageTemplate, HashMap<String, String> personalisation, String reference){
        try {
            SendSmsResponse response = this.notificationClient.sendSms(notifyMessageTemplate.getTemplateId(), personalisation.get(MOBILE_TELEPHONE_NUMBER), personalisation, reference);
            log.info("Sent SMS message with reference: '{}'", response.getReference());
        } catch (NotificationClientException e) {
            if (log.isDebugEnabled()) {
                log.warn("SMS Message could not be sent with reference '{}'", personalisation.get(REFERENCE_NUMBER), e);
            } else {
                log.warn("SMS Message could not be sent with reference '{}': {}", personalisation.get(REFERENCE_NUMBER), e.getMessage());
            }
        }
    }

    private void sendNotifyEmail(NotifyMessageTemplate notifyMessageTemplate, HashMap<String, String> personalisation, String reference){
        try {
            SendEmailResponse response = this.notificationClient.sendEmail(notifyMessageTemplate.getTemplateId(), personalisation.get(EMAIL), personalisation, reference);
            log.info("Sent email with reference: '{}'", response.getReference());
        }catch (NotificationClientException e){
            if (log.isDebugEnabled()) {
                log.warn("Email Message could not be sent to user '{}'", null, e);
            } else {
                log.warn("Email Message could not be sent to user '{}': {}", null, e.getMessage());
            }
        }
    }

    /**
     * Check to see if the SMS count is above an arbitrary limit for the time period defined in UserService.resetOtpCounts Scheduled task.
     * @param user
     */
    private boolean canSendOtp(User user){
        if(user.getOtpSentCount() >= SMS_COUNT){
            return false;
        }else{
            return true;
        }
    }

    private User updateOtpCount(User user){
        Integer otpSentCount = user.getOtpSentCount();
        user.setOtpSentCount(++otpSentCount);
        user.setOtpSentDate(Instant.now());
        return user;
    }

}
