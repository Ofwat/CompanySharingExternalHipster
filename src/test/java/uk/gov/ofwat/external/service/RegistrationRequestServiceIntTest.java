package uk.gov.ofwat.external.service;

import cucumber.api.java.cs.A;
import io.github.jhipster.config.JHipsterProperties;
import org.hibernate.cfg.NotYetImplementedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.spring4.SpringTemplateEngine;
import uk.gov.ofwat.external.CompanySharingExternalApp;
import uk.gov.ofwat.external.config.Constants;
import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.domain.RegistrationRequest;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.domain.message.NotifyMessageTemplate;
import uk.gov.ofwat.external.repository.NotifyMessageTemplateRepository;
import uk.gov.ofwat.external.repository.RegistrationRequestRepository;
import uk.gov.ofwat.external.repository.UserRepository;
import uk.gov.ofwat.external.security.AuthoritiesConstants;
import uk.gov.ofwat.external.service.mapper.CompanyMapper;
import uk.gov.service.notify.NotificationClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
@Transactional
public class RegistrationRequestServiceIntTest {

    private RegistrationRequestService registrationRequestService;

    @Autowired
    RegistrationRequestRepository registrationRequestRepository;

    @Autowired
    NotifyMessageTemplateRepository notifyMessageTemplateRepository;

    @Autowired
    CompanyService companyService;

    @Autowired
    private JHipsterProperties jHipsterProperties;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    CompanyMapper companyMapper;

    @Spy
    NotifyService notifyService = new NotifyService(notificationClient, userRepository);

    @Mock
    MailService mailService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        registrationRequestService = new RegistrationRequestService(registrationRequestRepository, companyService, mailService, companyMapper);
        doNothing().when(notifyService).sendMessage(any(User.class), any(NotifyMessageTemplate.class), any(HashMap.class));
        doNothing().when(notifyService).sendMessage(any(NotifyMessageTemplate.class), any(HashMap.class));
    }

    private Company createCompany(String name){
        Company company = new Company();
        company.setName(name);
        company.setDeleted(false);
        return companyService.save(company);
    }

    @Test
    public void testCreateRegistrationRequest(){
        Optional<RegistrationRequest> registrationRequest = registrationRequestRepository.findOneByLogin("test@test.com");
        assertThat(registrationRequest.isPresent()).isFalse();
        Company company = createCompany("Company 1");
        RegistrationRequest newRequest = registrationRequestService.createRegistrationRequest("test@test.com", "test", "smith", "test@test.com", "07000100100" , company.getId());
        assertThat(registrationRequestRepository.findOneByLogin("test@test.com").isPresent()).isTrue();
        registrationRequestRepository.delete(newRequest.getId());
        companyService.delete(company.getId());
    }

    @Test
    public void testDeleteRegistrationRequest(){
        Company company = createCompany("Company 1");
        User admin = userRepository.findOneByLogin("admin").get();
        companyService.addUserToCompany(company.getId(), admin, AuthoritiesConstants.ADMIN);
        RegistrationRequest newRequest = registrationRequestService.createRegistrationRequest("test@test.com", "test", "smith", "test@test.com", "07000100100" , company.getId());
        assertThat(registrationRequestRepository.findOneByLogin("test@test.com").isPresent()).isTrue();
        registrationRequestService.deleteRegistrationRequest(newRequest.getLogin(), admin);
        assertThat(registrationRequestRepository.findOneByLogin("test@test.com").isPresent()).isFalse();

        //Clean up
        companyService.delete(company.getId());
        userService.deleteUser(admin.getLogin());

    }

    @Test
    public void testGetAllRequestsByCompany(){
        Company company1 = createCompany("Company 1");
        ArrayList<RegistrationRequest> registrationRequests = new ArrayList<RegistrationRequest>();
        registrationRequests.add(registrationRequestService.createRegistrationRequest("test1@test.com", "test", "smith", "test1@test.com", "07000100100" , company1.getId()));
        registrationRequests.add(registrationRequestService.createRegistrationRequest("test2@test.com", "test", "smith", "test2@test.com", "07000100100" , company1.getId()));
        registrationRequests.add(registrationRequestService.createRegistrationRequest("test3@test.com", "test", "smith", "test3@test.com", "07000100100" , company1.getId()));
        registrationRequests.add(registrationRequestService.createRegistrationRequest("test4@test.com", "test", "smith", "test4@test.com", "07000100100" , company1.getId()));

        final PageRequest pageable = new PageRequest(0, (int) registrationRequestRepository.count());
        Page<RegistrationRequest> page = registrationRequestService.getAllRequests(pageable, company1);
        assertThat(page.getContent().size() == 4);

        Company company2 = createCompany("Company 2");
        page = registrationRequestService.getAllRequests(pageable, company2);
        assertThat(page.getContent().isEmpty());
    }

    public void testOnlyAdminForCompanyCanApproveRequest(){
        User admin = userRepository.findOneByLogin("admin").get();
        User nonAdmin = userService.createUser("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "http://placehold.it/50x50", "en-US", "077777077852");
        Company company1 = createCompany("Company 1");
        companyService.addUserToCompany(company1.getId(), admin, AuthoritiesConstants.ADMIN);
        companyService.addUserToCompany(company1.getId(), nonAdmin, AuthoritiesConstants.USER);

        RegistrationRequest registrationRequest = registrationRequestService.createRegistrationRequest("test1@test.com", "test", "smith", "test1@test.com", "07000100100" , company1.getId());
        Optional<RegistrationRequest> rr = registrationRequestService.approveRegistrationRequest(registrationRequest.getLogin(), admin);
        assertThat(rr.get().getAdminApproved() == true);

        registrationRequestService.deleteRegistrationRequest(registrationRequest.getLogin(), admin);
        registrationRequest = registrationRequestService.createRegistrationRequest("test1@test.com", "test", "smith", "test1@test.com", "07000100100" , company1.getId());
        rr = registrationRequestService.approveRegistrationRequest(registrationRequest.getLogin(), nonAdmin);
        assertThat(rr.get().getAdminApproved() == false);

    }

    public void testOnlyAdminForCompanyCanDeleteRequest(){
        Company company = createCompany("Company 1");
        User admin = userRepository.findOneByLogin("admin").get();
        User user = userRepository.findOneByLogin("user").get();
        companyService.addUserToCompany(company.getId(), admin, AuthoritiesConstants.ADMIN);
        companyService.addUserToCompany(company.getId(), user, AuthoritiesConstants.USER);
        RegistrationRequest newRequest = registrationRequestService.createRegistrationRequest("test@test.com", "test", "smith", "test@test.com", "07000100100" , company.getId());
        assertThat(registrationRequestRepository.findOneByLogin("test@test.com").isPresent()).isTrue();

        registrationRequestService.deleteRegistrationRequest(newRequest.getLogin(), user);
        assertThat(registrationRequestRepository.findOneByLogin("test@test.com").isPresent()).isTrue();

        registrationRequestService.deleteRegistrationRequest(newRequest.getLogin(), admin);
        assertThat(registrationRequestRepository.findOneByLogin("test@test.com").isPresent()).isFalse();

    }

    /*
    //TODO Implement this.
    public void testRegistrationRequestLinkExpiresAfter24Hours(){

    }*/

    @Test
    public void testWeCanResendRegistrationRequest(){
        Company company = createCompany("Company 1");
        User admin = userRepository.findOneByLogin("admin").get();
        companyService.addUserToCompany(company.getId(), admin, AuthoritiesConstants.ADMIN);
        RegistrationRequest newRequest = registrationRequestService.createRegistrationRequest("test@test.com", "test", "smith", "test@test.com", "07000100100" , company.getId());
        assertThat(registrationRequestRepository.findOneByLogin("test@test.com").isPresent()).isTrue();

        //Approve the request
        registrationRequestService.approveRegistrationRequest("test@test.com", admin);
        //Test that it is approved
        assertThat(registrationRequestRepository.findOneByLogin("test@test.com").get().getAdminApproved()).isTrue();
        assertThat(registrationRequestRepository.findOneByLogin("test@test.com").get().getRegistrationKey()).isNotEmpty();

        //Test that the mail has been sent
        verify(mailService, times(1)).sendRegistrationRequestApprovalEmail(newRequest);

    }


}
