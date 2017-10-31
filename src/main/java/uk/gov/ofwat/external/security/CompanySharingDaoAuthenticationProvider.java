package uk.gov.ofwat.external.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.security.exception.UserNotEnabledException;
import uk.gov.ofwat.external.service.UserService;
import java.util.Optional;

public class CompanySharingDaoAuthenticationProvider extends DaoAuthenticationProvider {

    private UserService userService;

    public CompanySharingDaoAuthenticationProvider(){
        super();
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void additionalAuthenticationChecks(UserDetails userDetails,
                                               UsernamePasswordAuthenticationToken authentication)
        throws AuthenticationException {

        super.additionalAuthenticationChecks(userDetails, authentication);
        String lowercaseLogin = userDetails.getUsername().toLowerCase();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(lowercaseLogin);
        if(user.isPresent()){
            User u = user.get();
            if(!u.getEnabled()){
                throw new UserNotEnabledException("User " + lowercaseLogin + " was not enabled");
            }
        }else{
            //Check if the user has validated their OTP code?!
            throw new BadCredentialsException(messages.getMessage(
                "AbstractUserDetailsAuthenticationProvider.badCredentials",
                "Bad credentials"));

        }
    }
}
