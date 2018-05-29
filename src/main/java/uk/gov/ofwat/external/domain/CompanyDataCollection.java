package uk.gov.ofwat.external.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;

/**
 * A CompanyDataCollection.
 */
@Entity
@Table(name = "company_data_collection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyDataCollection extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnore
    private CompanyStatus status;

    @ManyToOne(optional = false)
    @NotNull
    private Company company;

    @ManyToOne(optional = false,fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    @JoinColumn(name="DATA_COLLECTION_ID", nullable=false)
    @NotNull
    @JsonIgnore
    private DataCollection dataCollection;

    @OneToMany(mappedBy="companyDataCollection",fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    @OrderColumn(name="order_Index")
    @JsonIgnore
    private CompanyDataBundle[] companyDataBundles;

    @NotNull
    @Column(name = "company_data_collection_order_Index", nullable = false)
    private Long companyDataCollectionOrderIndex;

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

    public String getName() {
        return name;
    }

    public CompanyDataCollection name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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

    public CompanyDataBundle[] getCompanyDataBundles() {
        return companyDataBundles;
    }

    public void setCompanyDataBundles(CompanyDataBundle[] companyDataBundles) {
        this.companyDataBundles = companyDataBundles;
    }

    public Long getCompanyDataCollectionOrderIndex() {
        return companyDataCollectionOrderIndex;
    }

    public void setCompanyDataCollectionOrderIndex(Long companyDataCollectionOrderIndex) {
        this.companyDataCollectionOrderIndex = companyDataCollectionOrderIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyDataCollection)) return false;

        CompanyDataCollection that = (CompanyDataCollection) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        if (dataCollection != null ? !dataCollection.equals(that.dataCollection) : that.dataCollection != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(companyDataBundles, that.companyDataBundles)) return false;
        if (companyDataCollectionOrderIndex != null ? !companyDataCollectionOrderIndex.equals(that.companyDataCollectionOrderIndex) : that.companyDataCollectionOrderIndex != null)
            return false;
        if (companyOwner != null ? !companyOwner.equals(that.companyOwner) : that.companyOwner != null) return false;
        return companyReviewer != null ? companyReviewer.equals(that.companyReviewer) : that.companyReviewer == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (dataCollection != null ? dataCollection.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(companyDataBundles);
        result = 31 * result + (companyDataCollectionOrderIndex != null ? companyDataCollectionOrderIndex.hashCode() : 0);
        result = 31 * result + (companyOwner != null ? companyOwner.hashCode() : 0);
        result = 31 * result + (companyReviewer != null ? companyReviewer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CompanyDataCollection{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", status=" + status +
            ", company=" + company +
            ", dataCollection=" + dataCollection +
            ", companyDataBundles=" + Arrays.toString(companyDataBundles) +
            ", companyDataCollectionOrderIndex=" + companyDataCollectionOrderIndex +
            ", companyOwner=" + companyOwner +
            ", companyReviewer=" + companyReviewer +
            '}';
    }
}
