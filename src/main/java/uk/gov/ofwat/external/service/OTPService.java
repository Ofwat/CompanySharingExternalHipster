package uk.gov.ofwat.external.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.repository.NotifyMessageTemplateRepository;
import uk.gov.ofwat.external.repository.UserRepository;

import java.util.HashMap;
import java.util.Random;

@Service
@Transactional
public class OTPService {

    private final Logger log = LoggerFactory.getLogger(OTPService.class);

    private final NotifyMessageTemplateRepository notifyMessageTemplateRepository;

    private final UserRepository userRepository;

    private final NotifyService notifyService;

    public OTPService(NotifyMessageTemplateRepository notifyMessageTemplateRepository, UserRepository userRepository, NotifyService notifyService){
        this.notifyMessageTemplateRepository = notifyMessageTemplateRepository;
        this.userRepository = userRepository;
        this.notifyService = notifyService;
    }

    /**
     * Reset an individuals OTP count to 0.
     * @param user
     * @return
     */
    public User resetOtpCount(User user){
        user.setOtpSentCount(0);
        return user;
    }

    @Transactional
    public void generateOtpCode(User user){

        // TODO Generate an OTP code and store it on the User object.
        // Do we need to perform a rate limiting check here?
        // Update the timestamp for the last sent OTP code and increment the counter.
        // Does this code really need to be an OTP code or can it just be a Random set of numbers?
        // We'll use an OTP code for the time being but that may not be the best idea.

        // TODO refactor to pick up the default for a constant name of OTP_CODE (for example!)
        notifyMessageTemplateRepository.findOneByName("OTP Code").map(smsTemplate -> {
            user.setEnabled(false);
            Random rnd = new Random();
            int n = 100000 + rnd.nextInt(900000);
            user.setOtpCode(Integer.valueOf(n).toString());
            HashMap<String, String> properties = new HashMap<String, String>();
            userRepository.save(user);
            properties.put("code", user.getOtpCode());
            notifyService.sendMessage(user, smsTemplate, properties);
            return smsTemplate;
        });
    }

    /**
     * Verify the passed user and code - return a boolean indicating success or failure.
     * Trying not ot leak any other data out of here that might indicate the existence/state of the user.
     * @param user
     * @param code
     * @return
     */
    public Boolean verifyOtpCode(User user, String code){
        Boolean verified = false;
        try{
            String storedCode = user.getOtpCode();
            if(StringUtils.equalsIgnoreCase(storedCode, code)){
                verified = true;
                //Update the user - set enabled to true and reset the sent OTP count.
                user.setEnabled(true);
                user.setOtpSentCount(0);
                userRepository.save(user);
            };
        }catch(Exception e){
            log.error("Exception verifying code for user {}", user.getLogin());
        }
        finally{
            return verified;
        }
    }
}
