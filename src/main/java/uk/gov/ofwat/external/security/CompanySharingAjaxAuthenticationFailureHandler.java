package uk.gov.ofwat.external.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CompanySharingAjaxAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        Class exceptionCauseClass = exception.getCause().getClass();
        if(exceptionCauseClass.equals(UserNotActivatedException.class)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Check Activation.");
        } else if(exceptionCauseClass.equals(UserNotEnabledException.class)){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Check Enabled.");
        } else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed.");
        }
    }
}
