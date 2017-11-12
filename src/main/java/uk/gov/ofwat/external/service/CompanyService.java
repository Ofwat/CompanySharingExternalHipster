package uk.gov.ofwat.external.service;

import groovy.transform.TailRecursive;
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
        Company c = companyRepository.findOne(id);
        List<CompanyUserDetails> companyUserDetailsList = companyUserDetailsRepository.findAllByCompany(c);
        companyUserDetailsList.forEach(companyUserDetails -> {
            c.getCompanyUserDetails().remove(companyUserDetails);
            companyUserDetailsRepository.delete(companyUserDetails.getId());
        });
        companyRepository.delete(id);
    }

    @Transactional
    public void addUserToCompany(Long companyId, User user, String role){
        Company company = companyRepository.findOne(companyId);
        try{
            //company.addUser(user);
            CompanyUserDetails companyUserDetails = new CompanyUserDetails();
            companyUserDetails.setCompany(company);
            companyUserDetails.setUser(user);
            companyUserDetails.setAuthority(authorityRepository.findOne(role));
            companyUserDetailsRepository.save(companyUserDetails);
            user.getCompanyUserDetails().add(companyUserDetails);
            company.getCompanyUserDetails().add(companyUserDetails);
            companyRepository.save(company);
            userRepository.save(user);
            log.debug("Added user '{}' to company '{}'", company, user);
        }catch(Exception e){
            log.error("Unable to add user '{}' to company '{}': {}", user, company, e);
        }
    }

    /**
     * Check if the current user has the ROLE_COMPANY_ADMIN role for a particular company.
     * @param company
     * @return
     */
    public boolean isUserAdminForCompany(Company company,  User user){
        // TODO implement this pending Company/User/Role refactoring
        return user.getAuthorities().stream().anyMatch(authority -> {return authority.getName().equals(AuthoritiesConstants.ADMIN) || authority.getName().equals(AuthoritiesConstants.COMPANY_ADMIN);});
    }

    /**
     * Check if the current user has the ROLE_COMPANY_USER role for a particular company.
     * @param company
     * @return
     */
    public boolean isUserMemberOfCompany(Company company, User user){
        // TODO implement this pending Company/User/Role refactoring
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

    public Boolean removeUserFromCompany(Long companyId, String login){
        Optional<User> user = userRepository.findOneByLogin(login);
        return user.map((u) -> {
            Company c = companyRepository.getOne(companyId);
            List<CompanyUserDetails> companyUserDetailsList = companyUserDetailsRepository.findAllByCompanyAndUser(c, u);
            companyUserDetailsList.forEach(companyUserDetails -> {
                companyUserDetailsRepository.delete(companyUserDetails.getId());
                c.getCompanyUserDetails().remove(companyUserDetails);
                u.getCompanyUserDetails().remove(companyUserDetails);
            });
            userRepository.save(u);
            companyRepository.save(c);
            return true;
        }).orElse(false);
    }

}
