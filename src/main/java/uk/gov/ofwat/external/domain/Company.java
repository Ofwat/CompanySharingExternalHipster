package uk.gov.ofwat.external.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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

    @Column(name = "fountain-id", nullable = false)
    private Long fountainId;

    @Column(name = "name")
    private String name;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

/*    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "company_admin",
        joinColumns = @JoinColumn(name="companies_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="id"))*/



    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "company_user",
               joinColumns = @JoinColumn(name="companies_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="id"))
    private Set<User> users = new HashSet<>();

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

    public Set<User> getUsers() {
        return users;
    }

    public Company users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Company addUser(User user) {
        this.users.add(user);
        user.getCompanies().add(this);
        return this;
    }

    public Company removeUser(User user) {
        this.users.remove(user);
        user.getCompanies().remove(this);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getFountainId() {
        return fountainId;
    }

    public void setFountainId(Long fountainId) {
        this.fountainId = fountainId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;

        Company company = (Company) o;

        if (getId() != null ? !getId().equals(company.getId()) : company.getId() != null) return false;
        if (getFountainId() != null ? !getFountainId().equals(company.getFountainId()) : company.getFountainId() != null)
            return false;
        if (getName() != null ? !getName().equals(company.getName()) : company.getName() != null) return false;
        if (getDeleted() != null ? !getDeleted().equals(company.getDeleted()) : company.getDeleted() != null)
            return false;
        return getUsers() != null ? getUsers().equals(company.getUsers()) : company.getUsers() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getFountainId() != null ? getFountainId().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDeleted() != null ? getDeleted().hashCode() : 0);
        result = 31 * result + (getUsers() != null ? getUsers().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + id +
            ", fountainId=" + fountainId +
            ", name='" + name + '\'' +
            ", deleted=" + deleted +
            ", users=" + users +
            '}';
    }
}
