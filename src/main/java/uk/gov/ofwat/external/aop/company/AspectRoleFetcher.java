package uk.gov.ofwat.external.aop.company;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class AspectRoleFetcher {

    public String[] getRoles(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ValidateUserCompany validateUserCompany = method.getAnnotation(ValidateUserCompany.class);
        return validateUserCompany.roles();
    }
}
