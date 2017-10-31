package uk.gov.ofwat.external.security.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
/**
 * Interceptor to check if the accounts 'enabled' status has changed.
 * Not sure if this is the correct way to manage this but we need to force 'immediate' suspension of an account or to bounce to OTP enter
 * screen when required!
 */
public class AccountInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {

        //See if the user is logged in - if they are check their account status.
        // Spring sec will handle non activated accounts.
        SecurityContextImpl securityContext = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if(securityContext != null) {
            Authentication account = (Authentication) securityContext.getAuthentication();
            if (account != null) {
                UserDetails userDetails = (UserDetails) account.getPrincipal();
                Optional<User> user = userRepository.findOneByLogin(userDetails.getUsername());
            }
        }

        System.out.println("In preHandle we are Intercepting the Request");
        System.out.println("____________________________________________");
        String requestURI = request.getRequestURI();
        //response.sendRedirect(request.getContextPath() + "/app/main-age");
        System.out.println("____________________________________________");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object object, ModelAndView model)
        throws Exception {
        System.out.println("_________________________________________");
        System.out.println("In postHandle request processing "
            + "completed by @RestController");
        System.out.println("_________________________________________");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object object, Exception arg3)
        throws Exception {
        System.out.println("________________________________________");
        System.out.println("In afterCompletion Request Completed");
        System.out.println("________________________________________");
    }
}
