package uk.gov.ofwat.external.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.security.AjaxAuthenticationFailureHandler;
import io.github.jhipster.security.AjaxAuthenticationSuccessHandler;
import io.github.jhipster.security.AjaxLogoutSuccessHandler;
import io.github.jhipster.security.Http401UnauthorizedEntryPoint;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CorsFilter;
import uk.gov.ofwat.external.aop.company.CompanySelectionAspect;
import uk.gov.ofwat.external.config.audit.CustomBasicAuthenticationEntryPoint;
import uk.gov.ofwat.external.security.AuthoritiesConstants;
import uk.gov.ofwat.external.security.CompanySharingAjaxAuthenticationFailureHandler;
import uk.gov.ofwat.external.security.CompanySharingDaoAuthenticationProvider;
import uk.gov.ofwat.external.service.UserService;

import javax.annotation.PostConstruct;

@SuppressWarnings("ALL")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration{

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private static String REALM="DCS_REST";

        @Bean
        public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
            return new CustomBasicAuthenticationEntryPoint();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        }
        @Autowired
        public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication().withUser("fountain").password("user").roles("FOUNTAIN");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .antMatcher("/data-api/**")
                .authorizeRequests()
                .anyRequest().hasRole("FOUNTAIN")
                .and()
                .httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint());
        }


    }

    @Configuration
    @Order(2)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        private final AuthenticationManagerBuilder authenticationManagerBuilder;

        private final UserDetailsService userDetailsService;

        private final JHipsterProperties jHipsterProperties;

        private final RememberMeServices rememberMeServices;

        private final CorsFilter corsFilter;

        @Autowired
        UserService userService;

        @Autowired
        CompanySelectionAspect companySelectionAspect;

        public FormLoginWebSecurityConfigurerAdapter(AuthenticationManagerBuilder authenticationManagerBuilder, UserDetailsService userDetailsService,
                                     JHipsterProperties jHipsterProperties, RememberMeServices rememberMeServices,
                                     CorsFilter corsFilter) {

            this.authenticationManagerBuilder = authenticationManagerBuilder;
            this.userDetailsService = userDetailsService;
            this.jHipsterProperties = jHipsterProperties;
            this.rememberMeServices = rememberMeServices;
            this.corsFilter = corsFilter;

        }

        @PostConstruct
        public void init() {
            try {
                CompanySharingDaoAuthenticationProvider provider = companySharingDaoAuthProvider();
                provider.setUserService(userService);
                //We are setting this in a PostConstruct to avoid a circular dependency with userService.
                companySelectionAspect.setUserService(userService);
                authenticationManagerBuilder
                    .authenticationProvider(provider);

            } catch (Exception e) {
                throw new BeanInitializationException("Security configuration failed", e);
            }
        }

        @Bean
        public AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
            return new AjaxAuthenticationSuccessHandler();
        }

        @Bean
        public CompanySharingAjaxAuthenticationFailureHandler companySharingAjaxAuthenticationFailureHandler() {
            return new CompanySharingAjaxAuthenticationFailureHandler();
        }

        @Bean
        public AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
            return new AjaxAuthenticationFailureHandler();
        }

        @Bean
        public AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler() {
            return new AjaxLogoutSuccessHandler();
        }

        @Bean
        public Http401UnauthorizedEntryPoint http401UnauthorizedEntryPoint() {
            return new Http401UnauthorizedEntryPoint();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public CompanySharingDaoAuthenticationProvider companySharingDaoAuthProvider() throws Exception {
            CompanySharingDaoAuthenticationProvider provider = new CompanySharingDaoAuthenticationProvider();
            provider.setPasswordEncoder(passwordEncoder());
            provider.setUserDetailsService(userDetailsService);
            return provider;
        }

/*    @Bean
    public RefreshingUserDetailsSecurityContextRepository refreshingUserDetailsSecurityContextRepository(HttpSessionSecurityContextRepository httpSessionSecurityContextRepository) {
        return new RefreshingUserDetailsSecurityContextRepository(httpSessionSecurityContextRepository, this.userDetailsService);
    }*/

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/app/**/*.{js,html}")
                .antMatchers("/i18n/**")
                .antMatchers("/content/**")
                .antMatchers("/swagger-ui/index.html")
                .antMatchers("/test/**")
                .antMatchers("/content/js/download.js")
                .antMatchers("/h2-console/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(http401UnauthorizedEntryPoint())
                .and()
                .rememberMe()
                .rememberMeServices(rememberMeServices)
                .rememberMeParameter("remember-me")
                .key(jHipsterProperties.getSecurity().getRememberMe().getKey())
                .and()
                .formLogin()
                .loginProcessingUrl("/api/authentication")
                .successHandler(ajaxAuthenticationSuccessHandler())
                .failureHandler(companySharingAjaxAuthenticationFailureHandler())
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(ajaxLogoutSuccessHandler())
                .permitAll()
                .and()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .authorizeRequests()
                .antMatchers("/excel/download").permitAll()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/activate").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/account/").permitAll()
                .antMatchers("/api/account/reset_password/init").permitAll()
                .antMatchers("/api/account/reset_password/finish").permitAll()
                .antMatchers("/api/account/resend_otp").permitAll()
                .antMatchers("/api/account/verify_otp").permitAll()
                .antMatchers("/api/account/request_account").permitAll()
                .antMatchers("/api/account/request_details").permitAll()
                .antMatchers("/api/account/request_details_resend").hasAnyAuthority(AuthoritiesConstants.OFWAT_ADMIN)
                .antMatchers("/api/invite").hasAuthority(AuthoritiesConstants.OFWAT_ADMIN)
                .antMatchers("/api/resend_invite").hasAuthority(AuthoritiesConstants.OFWAT_ADMIN)
                .antMatchers("/api/users/pending_accounts/**").hasAnyAuthority(AuthoritiesConstants.OFWAT_ADMIN, AuthoritiesConstants.COMPANY_ADMIN)
                .antMatchers("/api/users/companies/**").hasAuthority(AuthoritiesConstants.OFWAT_ADMIN)
                .antMatchers("/api/users-only/**").hasAnyAuthority(AuthoritiesConstants.OFWAT_ADMIN,AuthoritiesConstants.OFWAT_USER)
                .antMatchers("/api/data-inputs/**").hasAnyAuthority(AuthoritiesConstants.OFWAT_ADMIN,AuthoritiesConstants.OFWAT_USER)
                .antMatchers("/api/data-collections/**").hasAnyAuthority(AuthoritiesConstants.OFWAT_ADMIN,AuthoritiesConstants.OFWAT_USER)
                .antMatchers("/api/data-bundles/**").hasAnyAuthority(AuthoritiesConstants.OFWAT_ADMIN,AuthoritiesConstants.OFWAT_USER)
                .antMatchers("/api/company-data-collections/**").hasAnyAuthority(AuthoritiesConstants.OFWAT_ADMIN, AuthoritiesConstants.COMPANY_ADMIN,AuthoritiesConstants.COMPANY_USER,AuthoritiesConstants.OFWAT_USER)
                .antMatchers("/api/company-data-bundles/**").hasAnyAuthority(AuthoritiesConstants.OFWAT_ADMIN, AuthoritiesConstants.COMPANY_ADMIN, AuthoritiesConstants.COMPANY_USER,AuthoritiesConstants.OFWAT_USER)
                .antMatchers("/api/profile-info").permitAll()
                .antMatchers(HttpMethod.GET, "/api/companies").permitAll()
                .antMatchers("/api/data-download").hasAnyAuthority(AuthoritiesConstants.COMPANY_ADMIN, AuthoritiesConstants.COMPANY_USER)
                .antMatchers("/api/data-download-file").permitAll()
                .antMatchers("/management/health").permitAll()
                .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.OFWAT_ADMIN)
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/swagger-resources/confresend_otpiguration/ui").permitAll()
                .antMatchers("/content/js/download.js").permitAll()
                .antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.OFWAT_ADMIN)
                .antMatchers("/datasubmission").hasAnyAuthority(AuthoritiesConstants.OFWAT_ADMIN,AuthoritiesConstants.OFWAT_USER)
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        }

        @Bean
        public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
            return new SecurityEvaluationContextExtension();
        }
    }
}
