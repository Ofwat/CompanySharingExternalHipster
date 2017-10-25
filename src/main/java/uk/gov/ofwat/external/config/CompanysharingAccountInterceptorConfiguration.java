package uk.gov.ofwat.external.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import uk.gov.ofwat.external.security.interceptor.AccountInterceptor;

@Configuration
public class CompanysharingAccountInterceptorConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    AccountInterceptor accountInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accountInterceptor);
    }

}
