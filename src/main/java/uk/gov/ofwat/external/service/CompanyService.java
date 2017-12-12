package uk.gov.ofwat.external.service;

import groovy.transform.TailRecursive;
import org.springframework.boot.actuate.autoconfigure.ShellProperties;
import uk.gov.ofwat.external.domain.Authority;
import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.domain.CompanyUserDetails;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.repository.AuthorityRepository;
import uk.gov.ofwat.external.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.repository.CompanyUserDetailsRepository;
import uk.gov.ofwat.external.repository.UserRepository;
import uk.gov.ofwat.external.security.AuthoritiesConstants;
import uk.gov.ofwat.external.service.Exception.UnableToRemoveUserException;

import java.lang.annotation.IncompleteAnnotationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Company.
 */
@Service
@Transactional
public class CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyRepository companyRepository;

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final CompanyUserDetailsRepository companyUserDetailsRepository;

    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository, AuthorityRepository authorityRepository, CompanyUserDetailsRepository companyUserDetailsRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.companyUserDetailsRepository = companyUserDetailsRepository;
    }

    /**
     * Save a company.
     *
     * @param company the entity to save
     * @return the persisted entity
     */
    public Company save(Company company) {
        log.debug("Request to save Company : {}", company);
        return companyRepository.save(company);
    }

    /**
     *  Get all the companies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Company> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        return companyRepository.findAll(pageable);
    }

    /**
     *  Get one company by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Company findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        return companyRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Delete the  company by id.
     *
     *  @param id the id of the entity
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        Company company = companyRepository.findOne(id);
        List<CompanyUserDetails> companyUserDetailsList = companyUserDetailsRepository.findAllByCompany(company);
        deleteAllCompanyUserDetailsForCompany(companyUserDetailsList, company);
        companyRepository.delete(id);
    }

    private void deleteAllCompanyUserDetailsForCompany(List<CompanyUserDetails> companyUserDetailsList, Company company){
        companyUserDetailsList.forEach(companyUserDetails -> {
            removeCompanyDetailsFromCompany(companyUserDetails, company);
            if (companyUserDetailsRepository.exists(companyUserDetails.getId()))
                companyUserDetailsRepository.delete(companyUserDetails.getId());
        });
    }

    @Transactional
    public void addUserToCompany(Long companyId, User user, String role){
        Company company = companyRepository.findOne(companyId);
        CompanyUserDetails companyUserDetails = createCompanyUserDetailsForCompany(company, user, role);
        addCompanyDetailsToUserAndCompany(companyUserDetails, user, company);
        log.debug("Added user '{}' to company '{}'", company, user);
    }

    private CompanyUserDetails createCompanyUserDetailsForCompany(Company company, User user, String role){
        CompanyUserDetails companyUserDetails = new CompanyUserDetails();
        companyUserDetails.setCompany(company);
        companyUserDetails.setUser(user);
        companyUserDetails.setAuthority(authorityRepository.findOne(role));
        return companyUserDetailsRepository.save(companyUserDetails);
    }

    private void addCompanyDetailsToUserAndCompany(CompanyUserDetails companyUserDetails, User user, Company company){
        addCompanyDetailsToUser(companyUserDetails, user);
        addCompanyDetailsToCompany(companyUserDetails, company);
    }

    private void addCompanyDetailsToUser(CompanyUserDetails companyUserDetails, User user){
        user.getCompanyUserDetails().add(companyUserDetails);
        userRepository.save(user);
    }

    private void addCompanyDetailsToCompany(CompanyUserDetails companyUserDetails, Company company){
        company.getCompanyUserDetails().add(companyUserDetails);
        companyRepository.save(company);
    }

    /**
     * Check if the current user has the ROLE_COMPANY_ADMIN role for a particular company.
     * @param company
     * @return
     */

    public boolean isUserAdminForCompany(Long companyId,  String login){
        Company company = companyRepository.getOne(companyId);
        return isUserAdminForCompany(company, login);
    }

    public boolean isUserAdminForCompany(Company company,  String login){
        Optional<User> user = userRepository.findOneByLogin(login);
        if(user.isPresent()) {
            return user.get().getCompanyUserDetails().stream()
                .anyMatch(companyUserDetails -> {
                 return checkForAdminRoles(company, companyUserDetails);
                });
        }
        return false;
    }

    private Boolean checkForAdminRoles(Company company, CompanyUserDetails companyUserDetails){
        if(company.equals(companyUserDetails.getCompany()))
            return checkForAdminRoles(companyUserDetails);
        return false;
    }

    @Transactional
    public Boolean doesUserHaveRoleForCompany(String login, Long companyId, String role){
        Company company = companyRepository.getOne(companyId);
        return doesUserHaveRoleForCompany(login, company, role);
    }

    public Boolean doesUserHaveRoleForCompany(String login, Company company, String role){
        Optional<User> user = userRepository.findOneByLogin(login);
        if(user.isPresent()){
            List<CompanyUserDetails> companyUserDetailsList = companyUserDetailsRepository.findAllByCompanyAndUser(company, user.get());
            return companyUserDetailsList.stream()
                .anyMatch(companyUserDetails -> {
                    return checkForRole(role, companyUserDetails);
                });
        }
        return false;
    }

    private boolean checkForRole(String role, CompanyUserDetails companyUserDetails){
        if(companyUserDetails.getAuthority().getName().equals(role))
            return true;
        return false;
    }

    private Boolean checkForAdminRoles(CompanyUserDetails companyUserDetails){
        String authority = companyUserDetails.getAuthority().getName();
        return authority.equals(AuthoritiesConstants.ADMIN) || authority.equals(AuthoritiesConstants.COMPANY_ADMIN);
    }

    /**
     * Check if the current user has the ROLE_COMPANY_USER role for a particular company.
     * @param company
     * @return
     */
    public boolean isUserMemberOfCompany(Company company, User user){
        return user.getCompanyUserDetails().stream().anyMatch(c -> {return c.getCompany().equals(company);});
    }

    /**
     * Get a list of companies that the current user has the role ROLE_COMPANY_ADMIN for.
     * @param user
     * @return
     */
    public Optional<List<Company>> getListOfCompaniesUserIsAdminFor(User user){
        return companyRepository.findAllWhereUserIsAdmin(user.getId());
    }

    public Optional<List<Company>> getListOfCompaniesUserIsMemberFor(String login){
        User user = userRepository.findOneByLogin(login).get();
        return getListOfCompaniesUserIsMemberFor(user);
    }

    /**
     * Get a list of companies that the current user has the role ROLE_COMPANY_USER for.
     * @param user
     * @return
     */
    public Optional<List<Company>> getListOfCompaniesUserIsMemberFor(User user){
        return Optional.of(user.getCompanyUserDetails().stream()
            .map(CompanyUserDetails::getCompany)
            .collect(Collectors.toList()));
    }

    public void removeUserFromCompany(Long companyId, String login) throws UnableToRemoveUserException {
        Optional<User> user = userRepository.findOneByLogin(login);
        Company company = companyRepository.getOne(companyId);
        if(user.isPresent())
            deleteAllCompanyUserDetailsForUserAtCompany(user.get(), company);
    }

    private void deleteAllCompanyUserDetailsForUserAtCompany(User user, Company company){
        List<CompanyUserDetails> companyUserDetailsList = companyUserDetailsRepository.findAllByCompanyAndUser(company, user);
        companyUserDetailsList.forEach(companyUserDetails -> {
            deleteAllCompanyUserDetailsForCompany(companyUserDetailsList, company);
            deleteAllCompanyUserDetailsForUser(companyUserDetailsList, user);
        });
    }

    private void deleteAllCompanyUserDetailsForUser(List<CompanyUserDetails> companyUserDetailsList, User user){
        companyUserDetailsList.forEach(companyUserDetails -> {
            removeCompanyDetailsFromUser(companyUserDetails, user);
            if (companyUserDetailsRepository.exists(companyUserDetails.getId()))
                companyUserDetailsRepository.delete(companyUserDetails.getId());
        });
    }

    private void removeCompanyDetailsFromUser(CompanyUserDetails companyUserDetails, User user){
        user.getCompanyUserDetails().remove(companyUserDetails);
        userRepository.save(user);
    }

    private void removeCompanyDetailsFromCompany(CompanyUserDetails companyUserDetails, Company company){
        company.getCompanyUserDetails().remove(companyUserDetails);
        companyRepository.save(company);
    }


}
