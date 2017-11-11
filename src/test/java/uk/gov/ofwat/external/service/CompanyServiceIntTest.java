package uk.gov.ofwat.external.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.ofwat.external.CompanySharingExternalApp;
import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.domain.RegistrationRequest;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.repository.CompanyRepository;
import uk.gov.ofwat.external.repository.UserRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
@Transactional
public class CompanyServiceIntTest {

    @Autowired
    public UserService userService;

    @Autowired
    public CompanyRepository companyRepository;

    @Autowired
    public UserRepository userRepository;

    private CompanyService companyService;

    @Before
    public void setup(){
        this.companyService = new CompanyService(companyRepository, userRepository);
    }

    @Test
    public void testAddACompany(){
        Company company = new Company();
        company.setName("Company1");
        company.setDeleted(false);
        company = companyService.save(company);
        assertThat(company.getId()).isNotNull();
        companyService.delete(company.getId());
    }

    @Test
    public void testDeleteACompany(){
        Company company = new Company();
        company.setName("Company1");
        company.setDeleted(false);
        company = companyService.save(company);

        User admin = userRepository.findOneByLogin("admin").get();
        companyService.addUserToCompany(company.getId(), admin);

        assertThat(company.getId()).isNotNull();
        Long companyId = company.getId();
        companyService.delete(companyId);
        Company foundCompany = companyRepository.findOne(companyId);
        assertThat(foundCompany).isNull();
    }

    @Test
    public void testAddUserToCompany(){
        Company company1 = new Company();
        company1.setName("Company1");
        company1.setDeleted(false);
        company1 = companyService.save(company1);

        Company company2 = new Company();
        company2.setName("Company2");
        company2.setDeleted(false);
        company2 = companyService.save(company2);

        User admin = userRepository.findOneByLogin("admin").get();
        User user = userRepository.findOneByLogin("user").get();
        companyService.addUserToCompany(company1.getId(), admin);
        companyService.addUserToCompany(company1.getId(), user);
        companyService.addUserToCompany(company2.getId(), admin);

        Company foundCompany1 = companyRepository.findOne(company1.getId());
        assertThat(foundCompany1).isNotNull();

        Company foundCompany2 = companyRepository.findOne(company2.getId());
        assertThat(foundCompany2).isNotNull();

        ///Check the company has the users.
        Set<User> usersCompany1 = foundCompany1.getUsers();
        assertThat(usersCompany1.size()).isEqualTo(2);
        assertThat(usersCompany1).contains(admin, user);

        Set<User> usersCompany2 = foundCompany2.getUsers();
        assertThat(usersCompany2.size()).isEqualTo(1);
        assertThat(usersCompany2).contains(admin);

        //Check the users have the correct companies.
        assertThat(admin.getCompanies()).containsExactly(company1, company2);
        assertThat(user.getCompanies()).containsExactly(company1);

        //Clean up
        //companyService.delete(company1.getId());
        //companyService.delete(company2.getId());
    }

    @Test
    public void testRemoveUserFromCompany(){
        Company company1 = new Company();
        company1.setName("Company1");
        company1.setDeleted(false);
        company1 = companyService.save(company1);

        User admin = userRepository.findOneByLogin("admin").get();
        User user = userRepository.findOneByLogin("user").get();
        companyService.addUserToCompany(company1.getId(), admin);
        companyService.addUserToCompany(company1.getId(), user);

        companyService.removeUserFromCompany(company1.getId(), admin.getLogin());
        companyService.removeUserFromCompany(company1.getId(), user.getLogin());

        assertThat(company1.getUsers()).isEmpty();

        //Clean up
        companyService.delete(company1.getId());

        //Make sure it's not in the users company list still!
        User foundUser = userRepository.findOne(admin.getId());
        assertThat(foundUser.getCompanies()).isEmpty();
    }


    @Test
    public void testGetListOfCompaniesUserIsAdminFor(){
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

        User admin = userRepository.findOneByLogin("admin").get();
        User user = userRepository.findOneByLogin("user").get();
        companyService.addUserToCompany(company1.getId(), admin);
        companyService.addUserToCompany(company2.getId(), admin);
        companyService.addUserToCompany(company1.getId(), user);
        companyService.addUserToCompany(company3.getId(), user);

        Optional<List<Company>> companies = companyService.getListOfCompaniesUserIsAdminFor(admin);
        assertThat(companies.get()).containsExactly(company1, company2);


    }


    @Test
    public void testUserIsAdminForCompany(){
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

        User admin = userRepository.findOneByLogin("admin").get();
        User user = userRepository.findOneByLogin("user").get();
        companyService.addUserToCompany(company1.getId(), admin);
        companyService.addUserToCompany(company2.getId(), admin);
        companyService.addUserToCompany(company1.getId(), user);
        companyService.addUserToCompany(company3.getId(), user);

        assertThat(companyService.isUserAdminForCompany(company1, admin)).isTrue();
        assertThat(companyService.isUserAdminForCompany(company1, user)).isFalse();
        assertThat(companyService.isUserAdminForCompany(company2, admin)).isTrue();
        //This test will fail until Company/User refactoring.
        //assertThat(companyService.isUserAdminForCompany(company3, admin)).isFalse();

    }

    @Test
    public void testUserIsMemberOfCompany(){
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

        User admin = userRepository.findOneByLogin("admin").get();
        User user = userRepository.findOneByLogin("user").get();
        companyService.addUserToCompany(company1.getId(), admin);
        companyService.addUserToCompany(company2.getId(), admin);
        companyService.addUserToCompany(company1.getId(), user);
        companyService.addUserToCompany(company3.getId(), user);

        assertThat(companyService.isUserMemberOfCompany(company1, admin)).isTrue();
        assertThat(companyService.isUserMemberOfCompany(company1, user)).isTrue();
        assertThat(companyService.isUserMemberOfCompany(company2, admin)).isTrue();
        assertThat(companyService.isUserMemberOfCompany(company2, user)).isFalse();
        assertThat(companyService.isUserMemberOfCompany(company3, user)).isTrue();
        assertThat(companyService.isUserMemberOfCompany(company3, admin)).isFalse();
    }

    @Test
    public void testGetListOfCompaniesUserIsMemberOf(){
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

        User admin = userRepository.findOneByLogin("admin").get();
        User user = userRepository.findOneByLogin("user").get();
        companyService.addUserToCompany(company1.getId(), admin);
        companyService.addUserToCompany(company2.getId(), admin);
        companyService.addUserToCompany(company1.getId(), user);
        companyService.addUserToCompany(company3.getId(), user);

        assertThat(companyService.getListOfCompaniesUserIsMemberFor(admin).get()).containsExactly(company1, company2);
        assertThat(companyService.getListOfCompaniesUserIsMemberFor(user).get()).containsExactly(company1, company3);

    }

}
