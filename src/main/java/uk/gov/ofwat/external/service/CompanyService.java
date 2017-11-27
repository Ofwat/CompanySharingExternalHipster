package uk.gov.ofwat.external.service;

import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.repository.CompanyRepository;
import uk.gov.ofwat.external.repository.UserRepository;
import uk.gov.ofwat.external.service.dto.CompanyDTO;
import uk.gov.ofwat.external.service.mapper.CompanyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * Service Implementation for managing Company.
 */
@Service
@Transactional
public class CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyRepository companyRepository;

    private final UserRepository userRepository;
    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save
     * @return the persisted entity
     */
    public CompanyDTO save(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        Company company = companyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    /**
     *  Get all the companies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CompanyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Companies");
        return companyRepository.findAll(pageable)
            .map(companyMapper::toDto);
    }

    /**
     *  Get one company by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CompanyDTO findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        Company company = companyRepository.findOneWithEagerRelationships(id);
        return companyMapper.toDto(company);
    }

    /**
     *  Delete the  company by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.delete(id);
    }

    @Transactional
    public void addUserToCompany(Long companyId, User user){
        Company company = companyRepository.findOne(companyId);
        try{
            company.addUser(user);
            companyRepository.save(company);
            user.getCompanies().add(company);
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
    public boolean isCurrentUserAdminForCompany(Company company){
        // TODO implement this pending Company/User/Role refactoring
        return true;
    }

    /**
     * Check if the current user has the ROLE_COMPANY_USER role for a particular company.
     * @param company
     * @return
     */
    public boolean isCurrentUserMemberOfCompany(Company company){
        // TODO implement this pending Company/User/Role refactoring
        return true;
    }

    /**
     * Get a list of companies that the current user has the role ROLE_COMPANY_ADMIN for.
     * @param company
     * @return
     */
    public Optional<List<Company>> getListOfCompaniesCurrentUserIsAdminFor(Company company){
        // TODO implement this pending Company/User/Role refactoring
        return Optional.empty();
    }

    /**
     * Get a list of companies that the current user has the role ROLE_COMPANY_USER for.
     * @param login
     * @return
     */
    public Optional<Set<Company>> getListOfCompaniesCurrentUserIsMemberFor(String login){
        Optional<User> user = userRepository.findOneByLogin(login);
        return user.map(u -> Optional.of(u.getCompanies()).orElse(Collections.emptySet()));
    }

    public Boolean removeUserFromCompany(Long companyId, String login){
        Optional<User> user = userRepository.findOneByLogin(login);
        return user.map((u) -> {
            Company c = companyRepository.getOne(companyId);
            c.removeUser(u);
            u = userRepository.save(u);
            companyRepository.save(c);
            return true;
        }).orElse(false);
    }

}
