package uk.gov.ofwat.external.aop.company;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class CompanyHeaderParser {

    public Optional<Long> getCompanyId(RequestAttributes requestAttributes){
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
        Long companyId = Long.parseLong(request.getHeader("selectedCompanyId"));
        return Optional.of(companyId);
    }

}
