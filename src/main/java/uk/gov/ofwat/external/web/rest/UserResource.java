package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uk.gov.ofwat.external.aop.company.ValidateUserCompany;
import uk.gov.ofwat.external.config.Constants;
import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.domain.RegistrationRequest;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.repository.UserRepository;
import uk.gov.ofwat.external.security.AuthoritiesConstants;
import uk.gov.ofwat.external.service.CompanyService;
import uk.gov.ofwat.external.service.Exception.UnableToRemoveUserException;
import uk.gov.ofwat.external.service.MailService;
import uk.gov.ofwat.external.service.RegistrationRequestService;
import uk.gov.ofwat.external.service.UserService;
import uk.gov.ofwat.external.service.dto.UserDTO;
import uk.gov.ofwat.external.service.mapper.CompanyMapper;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;
import uk.gov.ofwat.external.web.rest.util.PaginationUtil;
import uk.gov.ofwat.external.web.rest.vm.ManagedUserVM;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "userManagement";

    private final UserRepository userRepository;

    private final MailService mailService;

    private final UserService userService;

    private final RegistrationRequestService registrationRequestService;

    private final CompanyService companyService;

    private final CompanyMapper companyMapper;

     public UserResource(UserRepository userRepository, MailService mailService,
                         UserService userService, RegistrationRequestService registrationRequestService,
                         CompanyService companyService, CompanyMapper companyMapper) {

         this.userRepository = userRepository;
         this.mailService = mailService;
         this.userService = userService;
         this.registrationRequestService = registrationRequestService;
         this.companyService = companyService;
         this.companyMapper = companyMapper;
     }

    @Timed
    @PutMapping("/users/companies")
    public ResponseEntity<String> addCompanyUser(@RequestBody Long companyId, @RequestBody String login){
        log.debug("Addding user {} to company with id {}", login, companyId);
        return userRepository.findOneByLogin(login).map(user -> {
            companyService.addUserToCompany(companyId, user, AuthoritiesConstants.OFWAT_USER);
            return new ResponseEntity<String>(HttpStatus.CREATED);
        }).orElse(new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param managedUserVM the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.OFWAT_ADMIN)
    public ResponseEntity createUser(@Valid @RequestBody ManagedUserVM managedUserVM) throws URISyntaxException {
        log.debug("REST request to save User : {}", managedUserVM);

        if (managedUserVM.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user cannot already have an ID"))
                .body(null);
        // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use"))
                .body(null);
        } else if (userRepository.findOneByEmail(managedUserVM.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use"))
                .body(null);
        } else {
            User newUser = userService.createUser(managedUserVM);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert( "A user is created with identifier " + newUser.getLogin(), newUser.getLogin()))
                .body(newUser);
        }
    }

    /**
     * PUT  /users : Updates an existing User.
     *
     * @param managedUserVM the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user,
     * or with status 400 (Bad Request) if the login or email is already in use,
     * or with status 500 (Internal Server Error) if the user couldn't be updated
     */
    @PutMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.OFWAT_ADMIN)
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody ManagedUserVM managedUserVM) {
        log.debug("REST request to update User : {}", managedUserVM);
        Optional<User> existingUser = userRepository.findOneByEmail(managedUserVM.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserVM.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use")).body(null);
        }
        existingUser = userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserVM.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use")).body(null);
        }
        Optional<UserDTO> updatedUser = userService.updateUser(managedUserVM);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("A user is updated with identifier " + managedUserVM.getLogin(), managedUserVM.getLogin()));
    }

    /**
     * GET  /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/users")
    @Timed
    @ValidateUserCompany(roles = {AuthoritiesConstants.COMPANY_USER, AuthoritiesConstants.COMPANY_ADMIN})
    public ResponseEntity<String> getAllUsers(@ApiParam Pageable pageable,@RequestParam Long companyId) throws JsonProcessingException {
        final Page<UserDTO> page;
        if (companyId==0){
            //Get all users
            page = userService.getAllManagedUsers(pageable);
        }else {
            //Get Company Users
            page = userService.getAllManagedUsersByCompany(pageable,companyId);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        ObjectMapper obm = new ObjectMapper();
        //Due to object mapping of entities need to get Companies explicitly from companyUserDetails because
        //Json conversion fails because of cyclic dependencies being present.
        page.getContent().stream().forEach(x-> {
            Set<Company> companiesList = new HashSet<>(new ArrayList<>());
            x.getCompanyUserDetails().stream().forEach(y -> {
                if (companyId==0) {
                    companiesList.add(y.getCompany());
                }else{
                    if (y.getCompany().getId().equals(companyId))
                        companiesList.add(y.getCompany());
                }
            });
            x.setCompanies(companiesList);
        });
        String content = obm.writeValueAsString(page.getContent());
        return new ResponseEntity<>(content, headers, HttpStatus.OK);

    }

    /**
     * @return a string list of the all of the roles
     */
    @GetMapping("/users/authorities")
    @Timed
    @Secured(AuthoritiesConstants.OFWAT_ADMIN)
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    /**
     * GET  /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        ResponseEntity<UserDTO> responseEntity = ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDTO::new));
        return responseEntity;
    }

    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @Secured(AuthoritiesConstants.OFWAT_ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "A user is deleted with identifier " + login, login)).build();
    }

    @Timed
    @PostMapping("/users/otp")
    public ResponseEntity<Void> sendOtpCode(@PathVariable String login){
        // TODO How do we secure this?
        log.info("REST request to resend OTP code for login: {}", login);

        return ResponseEntity.ok().headers(HeaderUtil.createAlert("", "")).build();
    }

    /**
     * GET  /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    @GetMapping("/users/pending_accounts")
    @Timed
    @Secured({AuthoritiesConstants.OFWAT_ADMIN, AuthoritiesConstants.COMPANY_ADMIN})
    public ResponseEntity<List<RegistrationRequest>> getAllRegistrationRequests(@ApiParam Pageable pageable, @RequestParam Long companyId) {
        Company company = companyMapper.toEntity(companyService.findOne(companyId));
        User currentUser = userService.getUserWithAuthorities();
        if(company != null) {
            Boolean isValidAdmin = companyService.isUserAdminForCompany(company, currentUser.getLogin());
            if(isValidAdmin) {
                final Page<RegistrationRequest> page = registrationRequestService.getAllRequests(pageable, company);
                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users/pending_accounts");
                return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * DELETE /users/pending_accounts/:login : delete the "login" RegistrationRequest.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/pending_accounts/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @Secured(AuthoritiesConstants.OFWAT_ADMIN)
    public ResponseEntity<Void> deleteRegistrationRequest(@PathVariable String login,@AuthenticationPrincipal User activeUser) {
        log.debug("REST request to delete RegistrationRequest: {}", login);
        activeUser = userService.getUserWithAuthorities();
        registrationRequestService.deleteRegistrationRequest(login, activeUser);
        //return ResponseEntity.ok().headers(HeaderUtil.createAlert( "A RegistrationRequest is deleted with identifier " + login, login)).build();
        return  ResponseEntity.ok().build();
    }

    /**
     * PUT - Send the pending user an email with the link to complete registration.
     * i.e approve the request.
     * @param login the login for the request.
     * @return
     */
    @PostMapping("/users/pending_accounts")
    @Timed
    @Secured(AuthoritiesConstants.OFWAT_ADMIN)
    public ResponseEntity<String> approveRegistrationRequest(@RequestBody String login,@AuthenticationPrincipal User activeUser) {
        log.debug("REST request to approve RegistrationRequest: {}", login);
        activeUser = userService.getUserWithAuthorities();
        return registrationRequestService.approveRegistrationRequest(login, activeUser).map(registrationRequest -> new ResponseEntity<String>(HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Timed
    @PostMapping("/users/companies")
    @Secured({AuthoritiesConstants.OFWAT_ADMIN, AuthoritiesConstants.COMPANY_ADMIN, AuthoritiesConstants.COMPANY_USER})
    public List<Company> getCompaniesForUser(@RequestBody String login){
        log.info("REST request to get companies for login: {}", login);
        return companyService.getListOfCompaniesUserIsMemberFor(login).get();
    }

    @Timed
    @DeleteMapping("/users/companies/{companyId}/{login}")
    public ResponseEntity<String> removeCompanyUser(@PathVariable Long companyId, @PathVariable String login){
        try {
            companyService.removeUserFromCompany(companyId, login);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(UnableToRemoveUserException exception){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
