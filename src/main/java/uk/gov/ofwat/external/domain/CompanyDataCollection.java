package uk.gov.ofwat.external.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CompanyDataCollection.
 */
@Entity
@Table(name = "company_data_collection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyDataCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private CompanyStatus status;

    @ManyToOne(optional = false)
    @NotNull
    private Company company;

    @ManyToOne(optional = false)
    @NotNull
    private DataCollection dataCollection;

    @ManyToOne
    private User companyOwner;

    @ManyToOne
    private User companyReviewer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyStatus getStatus() {
        return status;
    }

    public CompanyDataCollection status(CompanyStatus companyStatus) {
        this.status = companyStatus;
        return this;
    }

    public void setStatus(CompanyStatus companyStatus) {
        this.status = companyStatus;
    }

    public Company getCompany() {
        return company;
    }

    public CompanyDataCollection company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public DataCollection getDataCollection() {
        return dataCollection;
    }

    public CompanyDataCollection dataCollection(DataCollection dataCollection) {
        this.dataCollection = dataCollection;
        return this;
    }

    public void setDataCollection(DataCollection dataCollection) {
        this.dataCollection = dataCollection;
    }

    public User getCompanyOwner() {
        return companyOwner;
    }

    public CompanyDataCollection companyOwner(User user) {
        this.companyOwner = user;
        return this;
    }

    public void setCompanyOwner(User user) {
        this.companyOwner = user;
    }

    public User getCompanyReviewer() {
        return companyReviewer;
    }

    public CompanyDataCollection companyReviewer(User user) {
        this.companyReviewer = user;
        return this;
    }

    public void setCompanyReviewer(User user) {
        this.companyReviewer = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyDataCollection companyDataCollection = (CompanyDataCollection) o;
        if (companyDataCollection.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyDataCollection.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyDataCollection{" +
            "id=" + getId() +
            "}";
    }
}
