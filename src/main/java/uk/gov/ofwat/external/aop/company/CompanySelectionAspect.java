package uk.gov.ofwat.external.aop.company;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import uk.gov.ofwat.external.repository.CompanyRepository;
import uk.gov.ofwat.external.security.SecurityUtils;
import uk.gov.ofwat.external.service.CompanyService;
import uk.gov.ofwat.external.service.UserService;

import java.util.Arrays;
import java.util.Optional;

@Aspect
@Component
public class CompanySelectionAspect {

    private final Logger log = LoggerFactory.getLogger(CompanySelectionAspect.class);

    private final CompanyService companyService;

    private UserService userService;

    private final CompanyRepository companyRepository;

    private final AspectRoleFetcher aspectRoleFetcher;

    private final CompanyHeaderParser companyHeaderParser;

    private Optional<Long> companyId;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public CompanySelectionAspect(CompanyService companyService, CompanyRepository companyRepository, AspectRoleFetcher aspectRoleFetcher, CompanyHeaderParser companyHeaderParser){
        this.companyService = companyService;
        this.companyRepository = companyRepository;
        this.aspectRoleFetcher = aspectRoleFetcher;
        this.companyHeaderParser = companyHeaderParser;
    }

    @Around("@annotation(ValidateUserCompany)")
    public Object validateUserCompany(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Boolean validUser = checkIfValidUser(proceedingJoinPoint);
        if(!validUser) {
            System.out.println("\n\n\n In the annotation - failed! - Company is " + companyId.get().toString() + "\n\n\n");
            return new ResponseEntity<>("Invalid company for user role", HttpStatus.BAD_REQUEST);
        }else {
            System.out.println("\n\n\n In the annotation - success! - Company is " + companyId.get().toString() + "\n\n\n");
            return proceedingJoinPoint.proceed();
        }
    }

    private Boolean checkIfValidUser(ProceedingJoinPoint proceedingJoinPoint){
        String currentUserLogin = SecurityUtils.getCurrentUserLogin();
        companyId = companyHeaderParser.getCompanyId(RequestContextHolder.getRequestAttributes());
        String[] roles = aspectRoleFetcher.getRoles(proceedingJoinPoint);
        Boolean validUser = checkIfUserHasValidRoleForCompany(roles, companyId.get(), currentUserLogin);
        return validUser;
    }

    private Boolean checkIfUserHasValidRoleForCompany(String[] roles, Long companyId, String currentUserLogin) {
        Boolean validUser = false;
        validUser = Arrays.stream(roles).anyMatch(role -> {
            return companyService.doesUserHaveRoleForCompany(currentUserLogin, companyId, role);
        });

        if(!validUser)
            validUser = userService.isUserAdministrator(currentUserLogin);

        if(!validUser)
            validUser = userService.isUserOfwatAdministrator(currentUserLogin);

        return validUser;
    }



}
