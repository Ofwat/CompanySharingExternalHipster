package uk.gov.ofwat.external.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "company_user_privilege_details")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyUserPrivilegeDetails extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="user_id")
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;

    @NotNull
    @Column(name = "order_Index", nullable = false)
    private Long orderIndex;

    @ManyToOne
    private Privilege privilege;

    public Long getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Long orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public User getUser() {
        return user;
    }

    public CompanyUserPrivilegeDetails setUser(User user) {
        this.user = user;
        return this;
    }

    public Company getCompany() {
        return company;
    }

    public CompanyUserPrivilegeDetails setCompany(Company company) {
        this.company = company;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
