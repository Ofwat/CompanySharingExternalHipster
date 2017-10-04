package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.security.access.annotation.Secured;
import uk.gov.ofwat.external.domain.PersistentToken;
import uk.gov.ofwat.external.domain.RegistrationRequest;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.repository.PersistentTokenRepository;
import uk.gov.ofwat.external.repository.UserRepository;
import uk.gov.ofwat.external.security.AuthoritiesConstants;
import uk.gov.ofwat.external.security.SecurityUtils;
import uk.gov.ofwat.external.service.*;
import uk.gov.ofwat.external.service.dto.UserDTO;
import uk.gov.ofwat.external.web.rest.vm.KeyAndPasswordVM;
import uk.gov.ofwat.external.web.rest.vm.ManagedUserVM;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;

    private final NotifyService notifyService;

    private final OTPService otpService;

    private final CompanyService companyService;

    private final PersistentTokenRepository persistentTokenRepository;

    private final CaptchaService captchaService;

    private static final String CHECK_ERROR_MESSAGE = "Incorrect password";

    private static final String CAPTCHA_ERROR_MESSAGE = "captcha failed";

    private static final String REGISTRATION_KEY_ERROR_MESSAGE = "invalid key";

    public AccountResource(UserRepository userRepository, UserService userService,
            MailService mailService, PersistentTokenRepository persistentTokenRepository, CompanyService companyService, NotifyService notifyService, OTPService otpService, CaptchaService captchaService) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
        this.persistentTokenRepository = persistentTokenRepository;
        this.companyService = companyService;
        this.notifyService = notifyService;
        this.otpService = otpService;
        this.captchaService = captchaService;
    }

    /**
     * POST  /register : register the user.
     *
     * @param managedUserVM the managed user View Model
     * @return the ResponseEntity with status 201 (Created) if the user is registered or 400 (Bad Request) if the login or email is already in use
     */
    @PostMapping(path = "/register",
        produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Timed
    public ResponseEntity registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {

        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);

        if (!checkPasswordLength(managedUserVM.getPassword())) {
            return new ResponseEntity<>(CHECK_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if (!captchaService.verifyCaptcha(managedUserVM.getCaptcha())) {
            return new ResponseEntity<>(CAPTCHA_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        // Verify the key is valid.
        Optional<RegistrationRequest> registrationRequest = userService.validateRegistrationKey(managedUserVM.getRegistrationKey());
        if(!registrationRequest.isPresent()){
            return new ResponseEntity<>(REGISTRATION_KEY_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        return userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase())
            .map(user -> new ResponseEntity<>("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
            .orElseGet(() -> userRepository.findOneByEmail(managedUserVM.getEmail())
                .map(user -> new ResponseEntity<>("email address already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {
                    User user = userService
                        .createUser(managedUserVM.getLogin(), managedUserVM.getPassword(),
                            managedUserVM.getFirstName(), managedUserVM.getLastName(),
                            managedUserVM.getEmail().toLowerCase(), managedUserVM.getImageUrl(),
                            managedUserVM.getLangKey(), managedUserVM.getMobileTelephoneNumber());
                    companyService.addUserToCompany(managedUserVM.getCompanyId(), user);
                    mailService.sendActivationEmail(user);
                    otpService.generateOtpCode(user);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                })
        );
    }

    /**
     * POST  /invite : invite the user.
     *
     * @param managedUserVM the managed user View Model
     * @return the ResponseEntity with status 201 (Created) if the user is invited or 400 (Bad Request) if the login or email is already in use.
     */
    @PostMapping(path = "/invite",
        produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity inviteUser(@Valid @RequestBody ManagedUserVM managedUserVM) {

        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);

        if (!checkPasswordLength(managedUserVM.getPassword())) {
            return new ResponseEntity<>(CHECK_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if (!captchaService.verifyCaptcha(managedUserVM.getCaptcha())) {
            return new ResponseEntity<>(CAPTCHA_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        // Verify the key is valid.
        Optional<RegistrationRequest> registrationRequest = userService.validateRegistrationKey(managedUserVM.getRegistrationKey());
        if(!registrationRequest.isPresent()){
            return new ResponseEntity<>(REGISTRATION_KEY_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        return userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase())
            .map(user -> new ResponseEntity<>("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
            .orElseGet(() -> userRepository.findOneByEmail(managedUserVM.getEmail())
                .map(user -> new ResponseEntity<>("email address already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {
                    User user = userService
                        .createUser(managedUserVM.getLogin(), managedUserVM.getPassword(),
                            managedUserVM.getFirstName(), managedUserVM.getLastName(),
                            managedUserVM.getEmail().toLowerCase(), managedUserVM.getImageUrl(),
                            managedUserVM.getLangKey(), managedUserVM.getMobileTelephoneNumber());
                    companyService.addUserToCompany(managedUserVM.getCompanyId(), user);
                    mailService.sendActivationEmail(user);
                    otpService.generateOtpCode(user);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                })
            );
    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @return the ResponseEntity with status 200 (OK) and the activated user in body, or status 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/activate")
    @Timed
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        return userService.activateRegistration(key)
            .map(user -> new ResponseEntity<String>(HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the ResponseEntity with status 200 (OK) and the current user in body, or status 500 (Internal Server Error) if the user couldn't be returned
     */
    @GetMapping("/account")
    @Timed
    public ResponseEntity<UserDTO> getAccount() {
        return Optional.ofNullable(userService.getUserWithAuthorities())
            .map(user -> new ResponseEntity<>(new UserDTO(user), HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /account : update the current user information.
     *
     * @param userDTO the current user information
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) or 500 (Internal Server Error) if the user couldn't be updated
     */
    @PostMapping("/account")
    @Timed
    public ResponseEntity saveAccount(@Valid @RequestBody UserDTO userDTO) {
        final String userLogin = SecurityUtils.getCurrentUserLogin();
        Optional<User> existingUser = userRepository.findOneByEmail(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "emailexists", "Email already in use")).body(null);
        }
        return userRepository
            .findOneByLogin(userLogin)
            .map(u -> {
                userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                    userDTO.getLangKey(), userDTO.getImageUrl(), userDTO.getMobileTelephoneNumber());
                return new ResponseEntity(HttpStatus.OK);
            })
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /account/change_password : changes the current user's password
     *
     * @param password the new password
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) if the new password is not strong enough
     */
    @PostMapping(path = "/account/change_password",
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity changePassword(@RequestBody String password) {
        if (!checkPasswordLength(password)) {
            return new ResponseEntity<>(CHECK_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET  /account/sessions : get the current open sessions.
     *
     * @return the ResponseEntity with status 200 (OK) and the current open sessions in body,
     *  or status 500 (Internal Server Error) if the current open sessions couldn't be retrieved
     */
    @GetMapping("/account/sessions")
    @Timed
    public ResponseEntity<List<PersistentToken>> getCurrentSessions() {
        return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin())
            .map(user -> new ResponseEntity<>(
                persistentTokenRepository.findByUser(user),
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * DELETE  /account/sessions?series={series} : invalidate an existing session.
     *
     * - You can only delete your own sessions, not any other user's session
     * - If you delete one of your existing sessions, and that you are currently logged in on that session, you will
     *   still be able to use that session, until you quit your browser: it does not work in real time (there is
     *   no API for that), it only removes the "remember me" cookie
     * - This is also true if you invalidate your current session: you will still be able to use it until you close
     *   your browser or that the session times out. But automatic login (the "remember me" cookie) will not work
     *   anymore.
     *   There is an API to invalidate the current session, but there is no API to check which session uses which
     *   cookie.
     *
     * @param series the series of an existing session
     * @throws UnsupportedEncodingException if the series couldnt be URL decoded
     */
    @DeleteMapping("/account/sessions/{series}")
    @Timed
    public void invalidateSession(@PathVariable String series) throws UnsupportedEncodingException {
        String decodedSeries = URLDecoder.decode(series, "UTF-8");
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(u ->
            persistentTokenRepository.findByUser(u).stream()
                .filter(persistentToken -> StringUtils.equals(persistentToken.getSeries(), decodedSeries))
                .findAny().ifPresent(t -> persistentTokenRepository.delete(decodedSeries)));
    }

    /**
     * POST   /account/reset_password/init : Send an email to reset the password of the user
     *
     * @param mail the mail of the user
     * @return the ResponseEntity with status 200 (OK) if the email was sent, or status 400 (Bad Request) if the email address is not registered
     */
    @PostMapping(path = "/account/reset_password/init",
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity requestPasswordReset(@RequestBody String mail) {
        return userService.requestPasswordReset(mail)
            .map(user -> {
                mailService.sendPasswordResetMail(user);
                return new ResponseEntity<>("email was sent", HttpStatus.OK);
            // }).orElse(new ResponseEntity<>("email address not registered", HttpStatus.BAD_REQUEST));
            // We are always going to send a success so that people can't spam the service to find out email addresses!
            }).orElse(new ResponseEntity<>("email was sent", HttpStatus.OK));
    }

    /**
     * POST   /account/reset_password/finish : Finish to reset the password of the user
     *
     * @param keyAndPassword the generated key and the new password
     * @return the ResponseEntity with status 200 (OK) if the password has been reset,
     * or status 400 (Bad Request) or 500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping(path = "/account/reset_password/finish",
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<String> finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            return new ResponseEntity<>(CHECK_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        return userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey())
              .map(user -> new ResponseEntity<String>(HttpStatus.OK))
              .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }

    @Timed
    @PostMapping(path = "/account/resend_otp")
    public ResponseEntity resendOTP(@RequestBody String mail){
        // If not see if there is an account based on passed parameters and send OTP to that.
        // All rate limited!
        Optional<User> user = userRepository.findOneByEmail(mail);
        log.debug("Requesting new OTP for user {}", user.get().getLogin());
        otpService.generateOtpCode(user.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/account/verify_otp")
    public ResponseEntity verifyOTP(@RequestBody HashMap<String, String> otpData){
        // If not see if there is an account based on passed parameters and send OTP to that.
        // All rate limited!
        Boolean verifySuccess = false;
        ResponseEntity response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
            Optional<User> user = userRepository.findOneByEmail(otpData.get("mail"));
            log.debug("Verifying new OTP for user {}", user.get().getLogin());
            verifySuccess = otpService.verifyOtpCode(user.get(), otpData.get("code"));
        }catch(Exception e){
            log.error("Error when verifying user with mail {}", otpData.get("mail"));
            log.error(e.getMessage());
        }
        finally{
            if(verifySuccess){
                log.info("Successfully validated OTP for user {}", otpData.get("mail"));
                log.info("Updating the registration request for this user (if they have one!)");
                response = new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return response;
    }

    @PostMapping(path = "/account/request_account")
    @Timed
    public ResponseEntity requestAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {

        HttpHeaders textPlainHeaders = new HttpHeaders();

        if (!captchaService.verifyCaptcha(managedUserVM.getCaptcha())) {
            return new ResponseEntity<>(CAPTCHA_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        /**
         * Check for existing users and Registration Requests.
         */
        return userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase())
            //TODO check for existing registration requests!
            .map(user -> new ResponseEntity<>("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
            .orElseGet(() -> userRepository.findOneByEmail(managedUserVM.getEmail())
                .map(user -> new ResponseEntity<>("email address already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {
                    RegistrationRequest rr = userService.createRegistrationRequest(managedUserVM.getLogin(), managedUserVM.getFirstName(), managedUserVM.getLastName(), managedUserVM.getEmail(),
                        managedUserVM.getMobileTelephoneNumber(), managedUserVM.getCompanyId());

                    mailService.sendRegistrationRequestUserEmail(rr);
                    //TODO We need to send the admin email with the correct company.
                    User user = userRepository.findOneByLogin("admin").get();
                    mailService.sendRegistrationRequestAdminEmail(rr, user);

                    return new ResponseEntity<>(HttpStatus.CREATED);
                }));
    }

    @PostMapping(path = "/account/request_details")
    public ResponseEntity getRegistrationRequestDetails(@RequestBody String key){
        HttpHeaders textPlainHeaders = new HttpHeaders();
        return userService.validateRegistrationKey(key)
            .map(registrationRequest -> new ResponseEntity<RegistrationRequest>(registrationRequest, textPlainHeaders, HttpStatus.OK))
            .orElseGet(() -> {
                return new ResponseEntity("Not found or expired", textPlainHeaders, HttpStatus.BAD_REQUEST);
            });
    }

}
