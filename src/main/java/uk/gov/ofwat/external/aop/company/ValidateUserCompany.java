package uk.gov.ofwat.external.aop.company;

import uk.gov.ofwat.external.security.AuthoritiesConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateUserCompany {
    public String[] roles();
}
