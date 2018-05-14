package uk.gov.ofwat.external.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import uk.gov.ofwat.external.config.Constants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.time.Instant;
import java.util.stream.Collectors;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash",length = 60)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    @Column(length = 100, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @NotNull
    @Column(nullable = false)
    private boolean enabled = false;

    @Size(min = 2, max = 5)
    @Column(name = "lang_key", length = 5)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 40)
    @Column(name = "mobile_telephone_number", length = 40, nullable = false)
    private String mobileTelephoneNumber;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    @Column(name = "require_otp_validation", nullable = false)
    @JsonIgnore
    private Boolean requireOtpValidation = true;

    @Size(max = 20)
    @Column(name = "otp_code", length = 20)
    @JsonIgnore
    private String otpCode;

    @Column(name = "otp_sent_date")
    @JsonIgnore
    private Instant otpSentDate = null;

    @Column(name = "otp_sent_count", nullable = false)
    @JsonIgnore
    private Integer otpSentCount = 0;

    @Column(name = "password_last_change_date")
    private Instant passwordLastChangeDate = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();


    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_privilege",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "privilege_name", referencedColumnName = "name")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Privilege> privileges = new HashSet<>();

/*    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "company_user",
        joinColumns = @JoinColumn(name="users_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="companies_id", referencedColumnName="id"))*/
/*
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private Set<Company> companies = new HashSet<>();
*/

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PersistentToken> persistentTokens = new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
        fetch = FetchType.LAZY, optional = true)
    private RegistrationRequest registrationRequest;


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user", fetch = FetchType.EAGER)
    @OrderColumn(name="order_Index")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompanyUserDetails> companyUserDetails = new HashSet<>();


    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user", fetch = FetchType.EAGER)
    @OrderColumn(name="order_Index")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompanyUserPrivilegeDetails> companyUserPrivilegeDetails = new HashSet<>();

    @JsonBackReference
    public Set<Company> getCompanies() {
        return this.companyUserDetails.stream().map(cud -> cud.getCompany()).collect(Collectors.toSet());
    }

    public Set<CompanyUserDetails> getCompanyUserDetails() {
        return companyUserDetails;
    }

    public User setCompanyUserDetails(Set<CompanyUserDetails> companyUserDetails) {
        this.companyUserDetails = companyUserDetails;
        return this;
    }

    public RegistrationRequest getRegistrationRequest() {
        return registrationRequest;
    }

    public void setRegistrationRequest(RegistrationRequest registrationRequest) {
        this.registrationRequest = registrationRequest;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public Boolean getRequireOtpValidation() {
        return requireOtpValidation;
    }

    public void setRequireOtpValidation(Boolean requireOtpValidation) {
        this.requireOtpValidation = requireOtpValidation;
    }

    public Instant getPasswordLastChangeDate() {
        return passwordLastChangeDate;
    }

    public void setPasswordLastChangeDate(Instant passwordLastChangeDate) {
        this.passwordLastChangeDate = passwordLastChangeDate;
    }

    public Instant getOtpSentDate() {
        return otpSentDate;
    }

    public void setOtpSentDate(Instant otpSentDate) {
        this.otpSentDate = otpSentDate;
    }

    public Integer getOtpSentCount() {
        return otpSentCount;
    }

    public void setOtpSentCount(Integer otpSentCount) {
        this.otpSentCount = otpSentCount;
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

    //Lowercase the login before saving it in database
    public void setLogin(String login) {
        this.login = login.toLowerCase(Locale.ENGLISH);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
       return resetDate;
    }

    public void setResetDate(Instant resetDate) {
       this.resetDate = resetDate;
    }
    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getMobileTelephoneNumber() {
        return mobileTelephoneNumber;
    }

    public void setMobileTelephoneNumber(String mobileTelephoneNumber) {
        this.mobileTelephoneNumber = mobileTelephoneNumber;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Set<PersistentToken> getPersistentTokens() {
        return persistentTokens;
    }

    public void setPersistentTokens(Set<PersistentToken> persistentTokens) {
        this.persistentTokens = persistentTokens;
    }

    public Set<CompanyUserPrivilegeDetails> getCompanyUserPrivilegeDetails() {
        return companyUserPrivilegeDetails;
    }

    public void setCompanyUserPrivilegeDetails(Set<CompanyUserPrivilegeDetails> companyUserPrivilegeDetails) {
        this.companyUserPrivilegeDetails = companyUserPrivilegeDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            ", mobileTelephoneNumber='" + mobileTelephoneNumber + '\'' +
            "}";
    }
}
