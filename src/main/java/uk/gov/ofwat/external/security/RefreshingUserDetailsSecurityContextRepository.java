package uk.gov.ofwat.external.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RefreshingUserDetailsSecurityContextRepository implements SecurityContextRepository {

    private SecurityContextRepository delegate;

    private UserDetailsService userDetailsService;

    public SecurityContextRepository getDelegate() {
        return this.delegate;
    }

    public void setDelegate(SecurityContextRepository delegate) {
        this.delegate = delegate;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public RefreshingUserDetailsSecurityContextRepository(final SecurityContextRepository delegate, final UserDetailsService userDetailsService) {
        //Assert.notNull(delegate);
        //Assert.notNull(userDetailsService);
        this.delegate = delegate;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public SecurityContext loadContext(final HttpRequestResponseHolder requestResponseHolder) {
        SecurityContext securityContext = delegate.loadContext(requestResponseHolder);

        if(securityContext.getAuthentication() == null) {
            return securityContext;
        }

        Authentication principal = securityContext.getAuthentication();
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());

        //this code has to be modified when using remember me service, jaas or a custom authentication token
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword());

        securityContext.setAuthentication(token);
        saveContext(securityContext, requestResponseHolder.getRequest(), requestResponseHolder.getResponse());
        return securityContext;
    }

    @Override
    public void saveContext(final SecurityContext context, final HttpServletRequest request, final HttpServletResponse response) {
        delegate.saveContext(context, request, response);
    }

    @Override
    public boolean containsContext(final HttpServletRequest request) {
        return delegate.containsContext(request);
    }
}
