package uk.gov.ofwat.external.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import uk.gov.ofwat.external.CompanySharingExternalApp;
import uk.gov.ofwat.external.aop.company.AspectRoleFetcher;
import uk.gov.ofwat.external.aop.company.CompanyHeaderParser;
import uk.gov.ofwat.external.aop.company.CompanySelectionAspect;
import uk.gov.ofwat.external.aop.company.ValidateUserCompany;
import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.repository.CompanyRepository;
import uk.gov.ofwat.external.security.AuthoritiesConstants;
import uk.gov.ofwat.external.service.CompanyService;
import uk.gov.ofwat.external.service.UserService;
import uk.gov.ofwat.external.web.rest.UserResource;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
@Transactional
public class CompanySelectionAspectIntTest {

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyRepository companyRepository;

    CompanySelectionAspect companySelectionAspect;

    ProceedingJoinPoint joinPoint;

    AspectRoleFetcher aspectRoleFetcher;

    CompanyHeaderParser companyHeaderParser;

    private final Logger log = LoggerFactory.getLogger(CompanySelectionAspectIntTest.class);

    @Before
    public void setup() {
        Company company1 = new Company();
        company1.setName("Company1");
        company1.setDeleted(false);
        company1 = companyService.save(company1);

        Company company2 = new Company();
        company2.setName("Company2");
        company2.setDeleted(false);
        company2 = companyService.save(company2);

        Company company3 = new Company();
        company3.setName("Company3");
        company3.setDeleted(false);
        company3 = companyService.save(company3);

        User admin = createTestUser("testAdmin");
        User user = createTestUser("testUser");
        companyService.addUserToCompany(company1.getId(), admin, AuthoritiesConstants.OFWAT_ADMIN);
        companyService.addUserToCompany(company2.getId(), admin, AuthoritiesConstants.OFWAT_ADMIN);
        companyService.addUserToCompany(company1.getId(), user, AuthoritiesConstants.OFWAT_USER);
        companyService.addUserToCompany(company3.getId(), user, AuthoritiesConstants.OFWAT_USER);

        //Set up mocks for the annotation.
        joinPoint = mock(ProceedingJoinPoint.class);
        aspectRoleFetcher = mock(AspectRoleFetcher.class);
        companyHeaderParser = mock(CompanyHeaderParser.class);
        ValidateUserCompany validateUserCompany = mock(ValidateUserCompany.class);
        String[] authorities = new String[]{AuthoritiesConstants.OFWAT_ADMIN};
        when(aspectRoleFetcher.getRoles(joinPoint)).thenReturn(authorities);
        RequestAttributes requestAttributes = mock(RequestAttributes.class);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        when(companyHeaderParser.getCompanyId(requestAttributes)).thenReturn(Optional.of(new Long(company1.getId())));

        companySelectionAspect = new CompanySelectionAspect(companyService, companyRepository, aspectRoleFetcher, companyHeaderParser);
        companySelectionAspect.setUserService(userService);
    }

    private User createTestUser(String login){
        return userService.createUser(login, "password", "John", "Doe", login + "@localhost", "http://placehold.it/50x50", "en-US", "077777077852");
    }

    @Test
    @WithMockUser(username="testadmin", roles={"ADMIN"})
    public void shouldAllowAdmin(){
        try {
            Object response = companySelectionAspect.validateUserCompany(joinPoint);
            verify(joinPoint, times(1)).proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            fail(throwable.getMessage());
        }
    }

    @Test
    @WithMockUser(username="testuser", roles={"USER"})
    public void shouldAllowUser(){
        try {
            String[] authorities = new String[]{AuthoritiesConstants.OFWAT_USER};
            when(aspectRoleFetcher.getRoles(joinPoint)).thenReturn(authorities);
            Object response = companySelectionAspect.validateUserCompany(joinPoint);
            verify(joinPoint, times(1)).proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            fail(throwable.getMessage());
        }
    }

    @Test
    @WithMockUser(username="testadmin", roles={"USER", "ADMIN"})
    public void shouldNotAllowWrongCompany(){
        RequestAttributes requestAttributes = mock(RequestAttributes.class);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        when(companyHeaderParser.getCompanyId(requestAttributes)).thenReturn(Optional.of(new Long(3)));
        try {
            Object response = companySelectionAspect.validateUserCompany(joinPoint);
            assertThat(response).isOfAnyClassIn(ResponseEntity.class);
            verify(joinPoint, never()).proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            fail(throwable.getMessage());
        }
    }

    @Test
    @WithMockUser(username="testadmin", roles={"USER"})
    public void shouldNotAllowWrongRole(){
        RequestAttributes requestAttributes = mock(RequestAttributes.class);
        RequestContextHolder.setRequestAttributes(requestAttributes);
        when(companyHeaderParser.getCompanyId(requestAttributes)).thenReturn(Optional.of(new Long(1)));
        try {
            Object response = companySelectionAspect.validateUserCompany(joinPoint);
            assertThat(response).isOfAnyClassIn(ResponseEntity.class);
            verify(joinPoint, never()).proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            fail(throwable.getMessage());
        }
    }
}
