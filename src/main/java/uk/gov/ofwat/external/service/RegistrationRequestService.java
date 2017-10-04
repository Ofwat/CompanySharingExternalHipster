package uk.gov.ofwat.external.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.domain.RegistrationRequest;
import uk.gov.ofwat.external.repository.RegistrationRequestRepository;

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

    @Transactional(readOnly = true)
    public Page<RegistrationRequest> getAllRequests(Pageable pageable, Company company){
        return registrationRequestRepository.findAllByUserActivatedIsFalseAndCompanyIs(pageable, company);
    };

    @Transactional
    public void deleteRegistrationRequest(String login){
        registrationRequestRepository.findOneByLogin(login).ifPresent(registrationRequest -> {
            if(companyService.isCurrentUserAdminForCompany(registrationRequest.getCompany())) {
                registrationRequestRepository.delete(registrationRequest);
                log.debug("Deleted RegistrationRequest: {}", registrationRequest);
            }
        });
    }

    @Transactional
    public Optional<RegistrationRequest> approveRegistrationRequest(String login){
        return registrationRequestRepository.findOneByLogin(login).map(registrationRequest -> {
            if(companyService.isCurrentUserAdminForCompany(registrationRequest.getCompany())) {
                registrationRequest.setAdminApproved(true);
                registrationRequest = registrationRequestRepository.save(registrationRequest);
                mailService.sendRegistrationRequestApprovalEmail(registrationRequest);
                log.debug("Approved RegistrationRequest: {}", registrationRequest);
            }
            return registrationRequest;
        });
    }
}
