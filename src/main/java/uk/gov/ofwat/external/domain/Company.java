package uk.gov.ofwat.external.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fountain_id", nullable = false)
    private Long fountainId;

    @Column(name = "name")
    private String name;

/*    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "company_admin",
        joinColumns = @JoinColumn(name="companies_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="id"))*/


    /*
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "company_user",
               joinColumns = @JoinColumn(name="companies_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="id"))
    private Set<User> users = new HashSet<>();*/

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "company", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompanyUserDetails> companyUserDetails;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "company", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompanyUserPrivilegeDetails> companyUserPrivilegeDetails;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CompanyUserDetails> getCompanyUserDetails() {
        return companyUserDetails;
    }

    public Company setCompanyUserDetails(Set<CompanyUserDetails> companyUserDetails) {
        this.companyUserDetails = companyUserDetails;
        return this;
    }

    public Set<CompanyUserPrivilegeDetails> getCompanyUserPrivilegeDetails() {
        return companyUserPrivilegeDetails;
    }

    public void setCompanyUserPrivilegeDetails(Set<CompanyUserPrivilegeDetails> companyUserPrivilegeDetails) {
        this.companyUserPrivilegeDetails = companyUserPrivilegeDetails;
    }

    /*    public Company addUser(User user, Authority authority) {
        this.users.add(user);
        user.getCompanies().add(this);
        return this;
    }

    public Company removeUser(User user) {
        this.users.remove(user);
        user.getCompanies().remove(this);
        return this;
    }*/

/*
    @JsonBackReference
    public Set<User> getUsers(){
        return this.companyUserDetails.stream().map(cud -> cud.getUser()).collect(Collectors.toSet());
    }
*/


    public Long getFountainId() {
        return fountainId;
    }

    public void setFountainId(Long fountainId) {
        this.fountainId = fountainId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        if (company.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
