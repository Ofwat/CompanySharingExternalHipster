package uk.gov.ofwat.external.service.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import uk.gov.ofwat.external.config.Constants;
import uk.gov.ofwat.external.domain.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    private Long id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 5)
    private String langKey;

    @Size(min = 4, max = 40)
    private String mobileTelephoneNumber;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<String> authorities;

    private Set<String> privileges;

    private Boolean enabled = false;

    private Instant passwordLastChangeDate;

    private Set<Company> companies;

    private Set<CompanyUserDetails> companyUserDetails;

    private Set<CompanyUserPrivilegeDetails> companyUserPrivilegeDetails;


    public UserDTO() {
        // Empty constructor needed for Jackson.
    }


    public UserDTO(User user) {
        this(user.getId(), user.getLogin(), user.getFirstName(), user.getLastName(),
            user.getEmail(), user.getActivated(), user.getImageUrl(), user.getLangKey(),
            user.getCreatedBy(), user.getCreatedDate(), user.getLastModifiedBy(), user.getLastModifiedDate(),
            user.getAuthorities().stream().map(Authority::getName)
                .collect(Collectors.toSet()), user.getMobileTelephoneNumber(), user.getEnabled(), user.getPasswordLastChangeDate(),
            user.getPrivileges().stream().map(Privilege::getName).collect(Collectors.toSet()),user.getCompanies(),
            user.getCompanyUserDetails(),user.getCompanyUserPrivilegeDetails());
    }

    public UserDTO(Long id, String login, String firstName, String lastName,
        String email, boolean activated, String imageUrl, String langKey,
        String createdBy, Instant createdDate, String lastModifiedBy, Instant lastModifiedDate,
        Set<String> authorities, String mobileTelephoneNumber, boolean enabled, Instant passwordLastChangeDate,Set<String> privileges,
                   Set<Company> companies, Set<CompanyUserDetails> companyUserDetails, Set<CompanyUserPrivilegeDetails> companyUserPrivilegeDetails) {

        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activated = activated;
        this.imageUrl = imageUrl;
        this.langKey = langKey;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.authorities = authorities;
        this.mobileTelephoneNumber = mobileTelephoneNumber;
        this.enabled = enabled;
        this.passwordLastChangeDate = passwordLastChangeDate;
        this.privileges = privileges;
        this.companies=companies;
        this.companyUserDetails = companyUserDetails;
        this.companyUserPrivilegeDetails = companyUserPrivilegeDetails;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public String getMobileTelephoneNumber() {
        return mobileTelephoneNumber;
    }

    public void setMobileTelephoneNumber(String mobileTelephoneNumber) {
        this.mobileTelephoneNumber = mobileTelephoneNumber;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Instant getPasswordLastChangeDate() {
        return passwordLastChangeDate;
    }

    public void setPasswordLastChangeDate(Instant passwordLastChangeDate) {
        this.passwordLastChangeDate = passwordLastChangeDate;
    }

    public Set<CompanyUserDetails> getCompanyUserDetails() {
        return companyUserDetails;
    }

    public void setCompanyUserDetails(Set<CompanyUserDetails> companyUserDetails) {
        this.companyUserDetails = companyUserDetails;
    }

    public Set<CompanyUserPrivilegeDetails> getCompanyUserPrivilegeDetails() {
        return companyUserPrivilegeDetails;
    }

    public void setCompanyUserPrivilegeDetails(Set<CompanyUserPrivilegeDetails> companyUserPrivilegeDetails) {
        this.companyUserPrivilegeDetails = companyUserPrivilegeDetails;
    }

    public Set<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<String> privileges) {
        this.privileges = privileges;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", createdBy=" + createdBy +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", authorities=" + authorities +
            "}";
    }
}
