package uk.gov.ofwat.external.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import uk.gov.ofwat.external.config.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Table(name = "registration_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RegistrationRequest extends AbstractAuditingEntity{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false, name = "login")
    private String login;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "email", length = 100, unique = true, nullable = false)
    private String email;

    @Size(max = 40)
    @Column(name = "mobile_telephone_number", length = 40)
    private String mobileTelephoneNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name="company_id", nullable = false)
    private Company company;

    @Column(name = "admin_approved", nullable = false)
    private Boolean adminApproved;

    @Column(name = "user_activated", nullable = false)
    private Boolean userActivated;

    @JsonIgnore
    @Column(name = "key_created", nullable = false)
    private Instant keyCreated;

    @OneToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Size(max = 20)
    @Column(name = "registration_key", length = 20)
    @JsonIgnore
    private String registrationKey;

    public Boolean getUserActivated() {
        return userActivated;
    }

    public void setUserActivated(Boolean userActivated) {
        this.userActivated = userActivated;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getMobileTelephoneNumber() {
        return mobileTelephoneNumber;
    }

    public void setMobileTelephoneNumber(String mobileTelephoneNumber) {
        this.mobileTelephoneNumber = mobileTelephoneNumber;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Boolean getAdminApproved() {
        return adminApproved;
    }

    public void setAdminApproved(Boolean adminApproved) {
        this.adminApproved = adminApproved;
    }

    public Instant getKeyCreated() {
        return keyCreated;
    }

    public void setKeyCreated(Instant keyCreated) {
        this.keyCreated = keyCreated;
    }

    public String getRegistrationKey() {
        return registrationKey;
    }

    public void setRegistrationKey(String registrationKey) {
        this.registrationKey = registrationKey;
    }
}
