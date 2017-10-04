package uk.gov.ofwat.external.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uk.gov.ofwat.external.domain.SmsTemplate;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.repository.UserRepository;
import uk.gov.service.notify.*;

import java.time.Instant;
import java.util.HashMap;

@Service
public class NotifyService {

    private final Logger log = LoggerFactory.getLogger(NotifyService.class);

    private final NotificationClient notificationClient;

    private final UserRepository userRepository;

    private final Integer SMS_COUNT = 10;

    public NotifyService(NotificationClient notificationClient, UserRepository userRepository){
        this.notificationClient = notificationClient;
        this.userRepository = userRepository;
    }

    //@Async

    /**
     * Send the user an SMS message based on a template stored at https://www.notifications.service.gov.uk/
     * @param user
     * @param smsTemplate
     * @param personalisation
     */
    public void sendMessage(User user, SmsTemplate smsTemplate, HashMap<String, String> personalisation){
        personalisation.put("reference_number", "1");
        String templateId = smsTemplate.getTemplateId();
        // TODO We need to return an error here if we can't send the SMS due to an invalid tel number etc..
        if(canSendOtp(user)) {
            try {
                userRepository.save(updateOtpCount(user));
                SendSmsResponse response = this.notificationClient.sendSms(templateId, user.getMobileTelephoneNumber(), personalisation, "yourReferenceString");
                log.info("Sent SMS message with reference: '{}'", response.getReference());
            } catch (NotificationClientException e) {
                if (log.isDebugEnabled()) {
                    log.warn("SMS Message could not be sent to user '{}'", null, e);
                } else {
                    log.warn("SMS Message could not be sent to user '{}': {}", null, e.getMessage());
                }
            }
        }else{
            log.warn("SMS Message could not be sent to user '{}': {} as max number of SMS in time period exceeded.", user);
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
