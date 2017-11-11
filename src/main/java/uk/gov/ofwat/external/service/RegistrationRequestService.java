package uk.gov.ofwat.external.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.domain.RegistrationRequest;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.repository.RegistrationRequestRepository;
import uk.gov.ofwat.external.service.util.RandomUtil;

import java.time.Instant;
import java.util.Optional;

@Service
public class RegistrationRequestService {

    private final RegistrationRequestRepository registrationRequestRepository;

    private CompanyService companyService;

    private MailService mailService;

    private final Logger log = LoggerFactory.getLogger(RegistrationRequestService.class);

    public RegistrationRequestService(RegistrationRequestRepository registrationRequestRepository, CompanyService companyService, MailService mailService) {
        this.registrationRequestRepository = registrationRequestRepository;
        this.companyService = companyService;
        this.mailService = mailService;
    }

    @Transactional
    public RegistrationRequest createRegistrationRequest(String login, String firstName, String lastName, String email,
                                                         String mobileTelephoneNumber, Long companyId){
        log.debug("Creating new registration request for user: {}", login);
        RegistrationRequest rr = new RegistrationRequest();
        rr.setLogin(login);
        rr.setFirstName("");
        rr.setLastName("");
        rr.setEmail(email);
        rr.setMobileTelephoneNumber(mobileTelephoneNumber);
        rr.setRegistrationKey(RandomUtil.generateActivationKey());
        rr.setAdminApproved(false);
        rr.setUserActivated(false);
        rr.setKeyCreated(Instant.now());
        Company company = companyService.findOne(companyId);
        rr.setCompany(company);
        return registrationRequestRepository.save(rr);
    }

    @Transactional(readOnly = true)
    public Page<RegistrationRequest> getAllRequests(Pageable pageable, Company company){
        return registrationRequestRepository.findAllByUserActivatedIsFalseAndCompanyIs(pageable, company);
    };

    @Transactional
    public void deleteRegistrationRequest(String login, User currentUser){
        registrationRequestRepository.findOneByLogin(login).ifPresent(registrationRequest -> {
            if(companyService.isUserAdminForCompany(registrationRequest.getCompany(), currentUser)) {
                registrationRequestRepository.delete(registrationRequest);
                log.debug("Deleted RegistrationRequest: {}", registrationRequest);
            }
        });
    }

    @Transactional
    public Optional<RegistrationRequest> approveRegistrationRequest(String login, User currentUser){
        return registrationRequestRepository.findOneByLogin(login).map(registrationRequest -> {
            if(companyService.isUserAdminForCompany(registrationRequest.getCompany(), currentUser)) {
                registrationRequest.setAdminApproved(true);
                registrationRequest = registrationRequestRepository.save(registrationRequest);
                mailService.sendRegistrationRequestApprovalEmail(registrationRequest);
                log.debug("Approved RegistrationRequest: {}", registrationRequest);
            }
            return registrationRequest;
        });
    }
}
