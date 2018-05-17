package uk.gov.ofwat.external.web.rest.vm;

import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.domain.CompanyUserDetails;
import uk.gov.ofwat.external.domain.CompanyUserPrivilegeDetails;
import uk.gov.ofwat.external.service.dto.UserDTO;
import javax.validation.constraints.Size;

import java.time.Instant;
import java.util.Set;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    private long companyId;

    private String captcha;

    private String registrationKey;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public ManagedUserVM(Long id, String login, String password, String firstName, String lastName,
                         String email, boolean activated, String imageUrl, String langKey,
                         String createdBy, Instant createdDate, String lastModifiedBy, Instant lastModifiedDate,
                         Set<String> authorities, Long companyId, String mobileTelehoneNumber, boolean enabled, Instant passwordLastChangeDate, Set<String> privileges,
                         Set<Company> companies, Set<CompanyUserDetails> companyUserDetails, Set<CompanyUserPrivilegeDetails> companyUserPrivilegeDetails,Set<String> companyNames) {

        super(id, login, firstName, lastName, email, activated, imageUrl, langKey,
            createdBy, createdDate, lastModifiedBy, lastModifiedDate,  authorities, mobileTelehoneNumber, enabled, passwordLastChangeDate,privileges,companies,companyUserDetails,companyUserPrivilegeDetails,companyNames);

        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" +
            "} " + super.toString();
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getRegistrationKey() {
        return registrationKey;
    }

    public void setRegistrationKey(String registrationKey) {
        this.registrationKey = registrationKey;
    }
}
