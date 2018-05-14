package uk.gov.ofwat.external.service;

import org.hibernate.cfg.NotYetImplementedException;
import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.repository.*;
import uk.gov.ofwat.external.config.Constants;
import uk.gov.ofwat.external.security.AuthoritiesConstants;
import uk.gov.ofwat.external.security.SecurityUtils;
import uk.gov.ofwat.external.service.util.RandomUtil;
import uk.gov.ofwat.external.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.repository.AuthorityRepository;
import uk.gov.ofwat.external.repository.PersistentTokenRepository;
import uk.gov.ofwat.external.repository.UserRepository;
import uk.gov.ofwat.external.web.rest.vm.ManagedUserVM;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final OTPService otpService;

    private final PersistentTokenRepository persistentTokenRepository;

    private final AuthorityRepository authorityRepository;

    private final PrivilegeRepository privilegeRepository;

    private final CompanyRepository companyRepository;

    private final RegistrationRequestRepository registrationRequestRepository;

    private final MailService mailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, OTPService otpService, PersistentTokenRepository persistentTokenRepository,
                       AuthorityRepository authorityRepository, CompanyRepository companyRepository, RegistrationRequestRepository registrationRequestRepository, MailService mailService,PrivilegeRepository privilegeRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
        this.persistentTokenRepository = persistentTokenRepository;
        this.authorityRepository = authorityRepository;
        this.companyRepository = companyRepository;
        this.registrationRequestRepository = registrationRequestRepository;
        this.mailService = mailService;
        this.privilegeRepository = privilegeRepository;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                log.debug("Activated user: {}", user);

                registrationRequestRepository.findOneByLogin(user.getLogin()).map(
                    registrationRequest -> {
                        registrationRequest.setUserActivated(true);
                        registrationRequestRepository.save(registrationRequest);
                        user.setRegistrationRequest(registrationRequest);
                        return userRepository.save(user);
                    }
                );

                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
       log.debug("Reset user password for reset key {}", key);

       return userRepository.findOneByResetKey(key)
           .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
           .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setPasswordLastChangeDate(Instant.now());
                user.setResetKey(null);
                user.setResetDate(null);
                return user;
           });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmail(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(Instant.now());
                return user;
            });
    }

    public User createUser(String login, String password, String firstName, String lastName, String email,
                           String imageUrl, String langKey, String mobileTelephoneNumber, RegistrationRequest registrationRequest){

        User user = createUser(login, password, firstName, lastName, email, imageUrl, langKey, mobileTelephoneNumber);
        registrationRequest.setUserActivated(true);
        registrationRequestRepository.save(registrationRequest);
        user.setActivated(true);
        user = userRepository.save(user);
        return user;

    }

    public User createUser(String login, String password, String firstName, String lastName, String email,
        String imageUrl, String langKey, String mobileTelephoneNumber) {

        User newUser = new User();
        Authority authority = authorityRepository.findOne(AuthoritiesConstants.OFWAT_USER);
        Set<Authority> authorities =     new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setPasswordLastChangeDate(Instant.now());
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setImageUrl(imageUrl);
        newUser.setLangKey(langKey);
        newUser.setMobileTelephoneNumber(mobileTelephoneNumber);
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        newUser.setOtpSentCount(0);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);

        return newUser;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setImageUrl(userDTO.getImageUrl());
        user.setMobileTelephoneNumber(userDTO.getMobileTelephoneNumber());
        if (userDTO.getLangKey() == null) {
            user.setLangKey("en"); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }

        //Set<Authority> authorities = new HashSet<>();
        //log.debug( userDTO.getAuthorities().toString() );
        //authorities.add(authorityRepository.findOne("1"));
        //user.setAuthorities(authorities);

        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = new HashSet<>();
            userDTO.getAuthorities().forEach(
                authority -> authorities.add(authorityRepository.findOne(authority))
            );
            user.setAuthorities(authorities);
        }

/*        if (userDTO.getPrivileges() != null) {
            Set<Privilege> privileges = new HashSet<>();
            userDTO.getPrivileges().forEach(
                authority -> privileges.add(privilegeRepository.findOne(authority))
            );
            user.setPrivileges(privileges);
        }*/

        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setPasswordLastChangeDate(Instant.now());
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user
     * @param lastName last name of user
     * @param email email id of user
     * @param langKey language key
     * @param imageUrl image URL of user
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl, String mobileTelephoneNumber) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setLangKey(langKey);
            user.setImageUrl(imageUrl);
            user.setMobileTelephoneNumber(mobileTelephoneNumber);
            log.debug("Changed Information for User: {}", user);
        });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDetails user to update
     * @return updated user
     */
    public java.util.Optional<UserDTO> updateUser(ManagedUserVM userDetails) {
        return Optional.of(userRepository
            .findOne(userDetails.getId()))
            .map(user -> {
                user.setLogin(userDetails.getLogin());
                user.setFirstName(userDetails.getFirstName());
                user.setLastName(userDetails.getLastName());
                user.setEmail(userDetails.getEmail());
                user.setImageUrl(userDetails.getImageUrl());
                user.setActivated(userDetails.isActivated());
                user.setLangKey(userDetails.getLangKey());
                user.setMobileTelephoneNumber(userDetails.getMobileTelephoneNumber());
                user.setEnabled(userDetails.getEnabled());

                if(userDetails.getPassword()!=null) {
                    String encryptedPassword = passwordEncoder.encode(userDetails.getPassword());
                    user.setPassword(encryptedPassword);
                    user.setPasswordLastChangeDate(Instant.now());
                }

                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDetails.getAuthorities().stream()
                    .map(authorityRepository::findOne)
                    .forEach(managedAuthorities::add);


/*                Set<Privilege> managedPrivileges = user.getPrivileges();
                managedPrivileges.clear();
                userDetails.getPrivileges().stream()
                    .map(privilegeRepository::findOne)
                    .forEach(managedPrivileges::add);*/
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public void changePassword(String password) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            String encryptedPassword = passwordEncoder.encode(password);
            user.setPassword(encryptedPassword);
            log.debug("Changed password for User: {}", user);
        });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        return userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).orElse(null);
    }

    /**
     * Persistent Token are used for providing automatic authentication, they should be automatically deleted after
     * 30 days.
     * <p>
     * This is scheduled to get fired everyday, at midnight.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void removeOldPersistentTokens() {
        LocalDate now = LocalDate.now();
        persistentTokenRepository.findByTokenDateBefore(now.minusMonths(1)).forEach(token -> {
            log.debug("Deleting token {}", token.getSeries());
            User user = token.getUser();
            user.getPersistentTokens().remove(token);
            persistentTokenRepository.delete(token);
        });
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getLogin());
            userRepository.delete(user);
        }
    }

    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    /**
     * Reset all the OTP counts for users to 0 to allow them to re-send SMS messages.
     * This is to prevent spamming of SMS - see OTPService for rate checks - I.E no more than X messages in a Y time period.
     */
    @Scheduled(initialDelay=60000, fixedRate=300000)
    public void resetOtpCounts(){
        log.debug("Starting reset OTP Counts for all users job.");
        List<User> users = userRepository.findAll();
        for (User user : users) {
            log.debug("Resetting OTP count for user {}", user.getLogin());
            user = otpService.resetOtpCount(user);
            userRepository.save(user);
        }
    }


    /**
     * Check for a registration key and if a vlaid key is found return the pre-stored details.
     * @param key
     * @return
     */
    public Optional<RegistrationRequest> validateRegistrationKey(String key){
        log.debug("Validating a registration key");
        return registrationRequestRepository.findOneByRegistrationKey(key)
            .filter(registrationRequest -> registrationRequest.getKeyCreated().isAfter(Instant.now().minusSeconds(86400)))
            .map(registrationRequest -> {
                log.debug("Found valid key for {}", registrationRequest);
                return registrationRequest;
            });
    }

    public Optional<RegistrationRequest> getRegistrationRequest(String login){
        log.debug("Getting a registrationRequest details for user {}", login);
        return registrationRequestRepository.findOneByLogin(login);
    }

    public Optional<RegistrationRequest> resendRegistrationRequest(String login){
        log.info("Resending registration request for login: {}", login);
        return registrationRequestRepository.findOneByLogin(login).map( registrationRequest -> {
            registrationRequest.setRegistrationKey(RandomUtil.generateActivationKey());
            registrationRequest.setKeyCreated(Instant.now());
            registrationRequest = registrationRequestRepository.save(registrationRequest);
            mailService.sendRegistrationRequestApprovalEmail(registrationRequest);
            return registrationRequest;
        });
    }

    public Boolean isUserAdministrator(String login){
        return userRepository.findOneByLogin(login).get().getAuthorities().stream().anyMatch(authority -> {
            return authority.getName().equals(AuthoritiesConstants.OFWAT_ADMIN);
        });
    }

    public Boolean isUserOfwatAdministrator(String login){
        return userRepository.findOneByLogin(login).get().getAuthorities().stream().anyMatch(authority -> {
            return authority.getName().equals(AuthoritiesConstants.OFWAT_ADMIN);
        });
    }

}
