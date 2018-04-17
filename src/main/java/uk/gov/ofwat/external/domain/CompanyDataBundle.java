package uk.gov.ofwat.external.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CompanyDataBundle.
 */
@Entity
@Table(name = "company_data_bundle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyDataBundle extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "company_deadline")
    private LocalDate companyDeadline;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnore
    private CompanyStatus status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnore
    private Company company;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="DATA_BUNDLE_ID", nullable=false)
    @NotNull
    @JsonIgnore
    private DataBundle dataBundle;

    @ManyToOne
    @JsonIgnore
    private User companyOwner;

    @NotNull
    @Column(name = "order_Index", nullable = false)
    private Long orderIndex;

    @ManyToOne
    @JsonIgnore
    private User companyReviewer;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="COMPANY_DATA_COLLECTION_ID", nullable=false)
    @NotNull
    @JsonIgnore
    private CompanyDataCollection companyDataCollection;

    @OneToMany(mappedBy="companyDataBundle",fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    @OrderColumn(name="order_Index")
    @JsonIgnore
    private CompanyDataInput[] companyDataInputs;

    @NotNull
    @Column(name = "company_data_bundle_order_Index", nullable = false)
    private Long companyDataBundleOrderIndex;

    @OneToMany(mappedBy = "companyDataBundle",fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SubmissionSignOff> submissionSignOffs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CompanyDataBundle name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCompanyDeadline() {
        return companyDeadline;
    }

    public CompanyDataBundle companyDeadline(LocalDate companyDeadline) {
        this.companyDeadline = companyDeadline;
        return this;
    }

    public void setCompanyDeadline(LocalDate companyDeadline) {
        this.companyDeadline = companyDeadline;
    }

    public CompanyStatus getStatus() {
        return status;
    }

    public CompanyDataBundle status(CompanyStatus companyStatus) {
        this.status = companyStatus;
        return this;
    }

    public void setStatus(CompanyStatus companyStatus) {
        this.status = companyStatus;
    }

    public Company getCompany() {
        return company;
    }

    public CompanyDataBundle company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public CompanyDataCollection getCompanyDataCollection() {
        return companyDataCollection;
    }

    public CompanyDataBundle companyDataCollection(CompanyDataCollection companyDataCollection) {
        this.companyDataCollection = companyDataCollection;
        return this;
    }

    public void setCompanyDataCollection(CompanyDataCollection companyDataCollection) {
        this.companyDataCollection = companyDataCollection;
    }

    public DataBundle getDataBundle() {
        return dataBundle;
    }

    public CompanyDataBundle dataBundle(DataBundle dataBundle) {
        this.dataBundle = dataBundle;
        return this;
    }

    public void setDataBundle(DataBundle dataBundle) {
        this.dataBundle = dataBundle;
    }

    public User getCompanyOwner() {
        return companyOwner;
    }

    public CompanyDataBundle companyOwner(User user) {
        this.companyOwner = user;
        return this;
    }

    public void setCompanyOwner(User user) {
        this.companyOwner = user;
    }

    public User getCompanyReviewer() {
        return companyReviewer;
    }

    public CompanyDataBundle companyReviewer(User user) {
        this.companyReviewer = user;
        return this;
    }

    public void setCompanyReviewer(User user) {
        this.companyReviewer = user;
    }
    public Set<SubmissionSignOff> getSubmissionSignOffs() {
        return submissionSignOffs;
    }

    public CompanyDataBundle submissionSignOffs(Set<SubmissionSignOff> submissionSignOffs) {
        this.submissionSignOffs = submissionSignOffs;
        return this;
    }

    public CompanyDataBundle addSubmissionSignOff(SubmissionSignOff submissionSignOff) {
        this.submissionSignOffs.add(submissionSignOff);
        submissionSignOff.setCompanyDataBundle(this);
        return this;
    }

    public CompanyDataBundle removeSubmissionSignOff(SubmissionSignOff submissionSignOff) {
        this.submissionSignOffs.remove(submissionSignOff);
        submissionSignOff.setCompanyDataBundle(null);
        return this;
    }

    public void setSubmissionSignOffs(Set<SubmissionSignOff> submissionSignOffs) {
        this.submissionSignOffs = submissionSignOffs;
    }

    public Long getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Long orderIndex) {
        this.orderIndex = orderIndex;
    }


    public CompanyDataInput[] getCompanyDataInputs() {
        return companyDataInputs;
    }

    public void setCompanyDataInputs(CompanyDataInput[] companyDataInputs) {
        this.companyDataInputs = companyDataInputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyDataBundle)) return false;

        CompanyDataBundle that = (CompanyDataBundle) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (companyDeadline != null ? !companyDeadline.equals(that.companyDeadline) : that.companyDeadline != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        if (dataBundle != null ? !dataBundle.equals(that.dataBundle) : that.dataBundle != null) return false;
        if (companyOwner != null ? !companyOwner.equals(that.companyOwner) : that.companyOwner != null) return false;
        if (orderIndex != null ? !orderIndex.equals(that.orderIndex) : that.orderIndex != null) return false;
        if (companyReviewer != null ? !companyReviewer.equals(that.companyReviewer) : that.companyReviewer != null)
            return false;
        if (companyDataCollection != null ? !companyDataCollection.equals(that.companyDataCollection) : that.companyDataCollection != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(companyDataInputs, that.companyDataInputs)) return false;
        return submissionSignOffs != null ? submissionSignOffs.equals(that.submissionSignOffs) : that.submissionSignOffs == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (companyDeadline != null ? companyDeadline.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (dataBundle != null ? dataBundle.hashCode() : 0);
        result = 31 * result + (companyOwner != null ? companyOwner.hashCode() : 0);
        result = 31 * result + (orderIndex != null ? orderIndex.hashCode() : 0);
        result = 31 * result + (companyReviewer != null ? companyReviewer.hashCode() : 0);
        result = 31 * result + (companyDataCollection != null ? companyDataCollection.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(companyDataInputs);
        result = 31 * result + (submissionSignOffs != null ? submissionSignOffs.hashCode() : 0);
        return result;
    }

    public Long getCompanyDataBundleOrderIndex() {
        return companyDataBundleOrderIndex;
    }

    public void setCompanyDataBundleOrderIndex(Long companyDataBundleOrderIndex) {
        this.companyDataBundleOrderIndex = companyDataBundleOrderIndex;
    }

    @Override
    public String toString() {
        return "CompanyDataBundle{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", companyDeadline=" + companyDeadline +
            ", status=" + status +
            ", company=" + company +
            ", dataBundle=" + dataBundle +
            ", companyOwner=" + companyOwner +
            ", orderIndex=" + orderIndex +
            ", companyReviewer=" + companyReviewer +
            ", companyDataCollection=" + companyDataCollection +
            ", companyDataInputs=" + Arrays.toString(companyDataInputs) +
            ", companyDataBundleOrderIndex=" + companyDataBundleOrderIndex +
            ", submissionSignOffs=" + submissionSignOffs +
            '}';
    }
}
