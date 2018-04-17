package uk.gov.ofwat.external.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.ofwat.external.repository.CompanyStatusRepository;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CompanyDataInput.
 */
@Entity
@Table(name = "company_data_input")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyDataInput extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @NotNull
    private CompanyStatus status;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="COMPANY_ID", nullable=false)
    @NotNull
    private Company company;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name="COMPANY_DATA_BUNDLE_ID", nullable=false)
    @NotNull
    private CompanyDataBundle companyDataBundle;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name="DATA_INPUT_ID", nullable=false)
    @NotNull
    private DataInput dataInput;

    @ManyToOne(optional = false)
    @NotNull
    private User companyOwner;

    @ManyToOne(optional = false)
    @NotNull
    private User companyReviewer;

    @OneToMany(mappedBy = "companyDataInput",fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReviewSignOff> reviewSignOffs = new HashSet<>();

    @OneToMany(mappedBy = "companyDataInput",fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DataFile> submissionFiles = new HashSet<>();

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="INPUT_TYPE_ID", nullable=false)
    private InputType inputType;

    @NotNull
    @Column(name = "order_Index", nullable = false)
    private Long orderIndex;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CompanyDataInput name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompanyStatus getStatus() {
        return status;
    }

    public CompanyDataInput status(CompanyStatus companyStatus) {
        this.status = companyStatus;
        return this;
    }

    public void setStatus(CompanyStatus companyStatus) {
        this.status = companyStatus;
    }

    public Company getCompany() {
        return company;
    }

    public CompanyDataInput company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public CompanyDataBundle getCompanyDataBundle() {
        return companyDataBundle;
    }

    public CompanyDataInput companyDataBundle(CompanyDataBundle companyDataBundle) {
        this.companyDataBundle = companyDataBundle;
        return this;
    }

    public void setCompanyDataBundle(CompanyDataBundle companyDataBundle) {
        this.companyDataBundle = companyDataBundle;
    }

    public DataInput getDataInput() {
        return dataInput;
    }

    public CompanyDataInput dataInput(DataInput dataInput) {
        this.dataInput = dataInput;
        return this;
    }

    public void setDataInput(DataInput dataInput) {
        this.dataInput = dataInput;
    }

    public User getCompanyOwner() {
        return companyOwner;
    }

    public CompanyDataInput companyOwner(User user) {
        this.companyOwner = user;
        return this;
    }

    public void setCompanyOwner(User user) {
        this.companyOwner = user;
    }

    public User getCompanyReviewer() {
        return companyReviewer;
    }

    public CompanyDataInput companyReviewer(User user) {
        this.companyReviewer = user;
        return this;
    }

    public void setCompanyReviewer(User user) {
        this.companyReviewer = user;
    }

    public Set<ReviewSignOff> getReviewSignOffs() {
        return reviewSignOffs;
    }

    public CompanyDataInput reviewSignOffs(Set<ReviewSignOff> reviewSignOffs) {
        this.reviewSignOffs = reviewSignOffs;
        return this;
    }

    public CompanyDataInput addReviewSignOff(ReviewSignOff reviewSignOff) {
        this.reviewSignOffs.add(reviewSignOff);
        reviewSignOff.setCompanyDataInput(this);
        return this;
    }

    public CompanyDataInput removeReviewSignOff(ReviewSignOff reviewSignOff) {
        this.reviewSignOffs.remove(reviewSignOff);
        reviewSignOff.setCompanyDataInput(null);
        return this;
    }

    public void setReviewSignOffs(Set<ReviewSignOff> reviewSignOffs) {
        this.reviewSignOffs = reviewSignOffs;
    }

    public Set<DataFile> getSubmissionFiles() {
        return submissionFiles;
    }

    public CompanyDataInput submissionFiles(Set<DataFile> dataFiles) {
        this.submissionFiles = dataFiles;
        return this;
    }

    public CompanyDataInput addSubmissionFiles(DataFile dataFile) {
        this.submissionFiles.add(dataFile);
        dataFile.setCompanyDataInput(this);
        return this;
    }

    public CompanyDataInput removeSubmissionFiles(DataFile dataFile) {
        this.submissionFiles.remove(dataFile);
        dataFile.setCompanyDataInput(null);
        return this;
    }

    public void setSubmissionFiles(Set<DataFile> dataFiles) {
        this.submissionFiles = dataFiles;
    }

    public InputType getInputType() {
        return inputType;
    }

    public CompanyDataInput inputType(InputType inputType) {
        this.inputType = inputType;
        return this;
    }

    public Long getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Long orderIndex) {
        this.orderIndex = orderIndex;
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }

    @NotNull
    @Column(name = "company_data_input_order_Index", nullable = false)
    private Long companyDataInputOrderIndex;

    public Long getCompanyDataInputOrderIndex() {
        return companyDataInputOrderIndex;
    }

    public void setCompanyDataInputOrderIndex(Long companyDataInputOrderIndex) {
        this.companyDataInputOrderIndex = companyDataInputOrderIndex;
    }

    @Override
    public String toString() {
        return "CompanyDataInput{" +
            "id=" + getId() +
            ", name='" + getName() + '\'' +
            ", status=" + getStatus() +
            ", company=" + getCompany() +
            ", companyDataBundle=" + getCompanyDataBundle() +
            ", dataInput=" + getDataInput() +
            ", companyOwner=" + getCompanyOwner() +
            ", companyReviewer=" + getCompanyReviewer() +
            ", reviewSignOffs=" + getReviewSignOffs() +
            ", submissionFiles=" + getSubmissionFiles() +
            ", inputType=" + getInputType() +
            ", orderIndex=" + getOrderIndex() +
            ", companyDataInputOrderIndex=" + getCompanyDataInputOrderIndex() +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyDataInput)) return false;

        CompanyDataInput that = (CompanyDataInput) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getStatus() != null ? !getStatus().equals(that.getStatus()) : that.getStatus() != null) return false;
        if (getCompany() != null ? !getCompany().equals(that.getCompany()) : that.getCompany() != null) return false;
        if (getCompanyDataBundle() != null ? !getCompanyDataBundle().equals(that.getCompanyDataBundle()) : that.getCompanyDataBundle() != null)
            return false;
        if (getDataInput() != null ? !getDataInput().equals(that.getDataInput()) : that.getDataInput() != null)
            return false;
        if (getCompanyOwner() != null ? !getCompanyOwner().equals(that.getCompanyOwner()) : that.getCompanyOwner() != null)
            return false;
        if (getCompanyReviewer() != null ? !getCompanyReviewer().equals(that.getCompanyReviewer()) : that.getCompanyReviewer() != null)
            return false;
        if (getReviewSignOffs() != null ? !getReviewSignOffs().equals(that.getReviewSignOffs()) : that.getReviewSignOffs() != null)
            return false;
        if (getSubmissionFiles() != null ? !getSubmissionFiles().equals(that.getSubmissionFiles()) : that.getSubmissionFiles() != null)
            return false;
        if (getInputType() != null ? !getInputType().equals(that.getInputType()) : that.getInputType() != null)
            return false;
        if (getOrderIndex() != null ? !getOrderIndex().equals(that.getOrderIndex()) : that.getOrderIndex() != null)
            return false;
        return getCompanyDataInputOrderIndex() != null ? getCompanyDataInputOrderIndex().equals(that.getCompanyDataInputOrderIndex()) : that.getCompanyDataInputOrderIndex() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getCompany() != null ? getCompany().hashCode() : 0);
        result = 31 * result + (getCompanyDataBundle() != null ? getCompanyDataBundle().hashCode() : 0);
        result = 31 * result + (getDataInput() != null ? getDataInput().hashCode() : 0);
        result = 31 * result + (getCompanyOwner() != null ? getCompanyOwner().hashCode() : 0);
        result = 31 * result + (getCompanyReviewer() != null ? getCompanyReviewer().hashCode() : 0);
        result = 31 * result + (getReviewSignOffs() != null ? getReviewSignOffs().hashCode() : 0);
        result = 31 * result + (getSubmissionFiles() != null ? getSubmissionFiles().hashCode() : 0);
        result = 31 * result + (getInputType() != null ? getInputType().hashCode() : 0);
        result = 31 * result + (getOrderIndex() != null ? getOrderIndex().hashCode() : 0);
        result = 31 * result + (getCompanyDataInputOrderIndex() != null ? getCompanyDataInputOrderIndex().hashCode() : 0);
        return result;
    }
}
