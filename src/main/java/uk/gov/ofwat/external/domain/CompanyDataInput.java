package uk.gov.ofwat.external.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CompanyDataInput.
 */
@Entity
@Table(name = "company_data_input")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyDataInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    private CompanyStatus status;

    @ManyToOne(optional = false)
    @NotNull
    private Company company;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name="COMPANY_DATA_BUNDLE_ID", nullable=false)
    private CompanyDataBundle companyDataBundle;

    @ManyToOne(optional = false)
    @NotNull
    private DataInput dataInput;

    @ManyToOne(optional = false)
    @NotNull
    private User companyOwner;

    @ManyToOne(optional = false)
    @NotNull
    private User companyReviewer;

    @OneToMany(mappedBy = "companyDataInput")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReviewSignOff> reviewSignOffs = new HashSet<>();

    @OneToMany(mappedBy = "companyDataInput")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DataFile> submissionFiles = new HashSet<>();

    @ManyToOne
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyDataInput)) return false;

        CompanyDataInput that = (CompanyDataInput) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        if (companyDataBundle != null ? !companyDataBundle.equals(that.companyDataBundle) : that.companyDataBundle != null)
            return false;
        if (dataInput != null ? !dataInput.equals(that.dataInput) : that.dataInput != null) return false;
        if (companyOwner != null ? !companyOwner.equals(that.companyOwner) : that.companyOwner != null) return false;
        if (companyReviewer != null ? !companyReviewer.equals(that.companyReviewer) : that.companyReviewer != null)
            return false;
        if (reviewSignOffs != null ? !reviewSignOffs.equals(that.reviewSignOffs) : that.reviewSignOffs != null)
            return false;
        if (submissionFiles != null ? !submissionFiles.equals(that.submissionFiles) : that.submissionFiles != null)
            return false;
        if (inputType != null ? !inputType.equals(that.inputType) : that.inputType != null) return false;
        return orderIndex != null ? orderIndex.equals(that.orderIndex) : that.orderIndex == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (companyDataBundle != null ? companyDataBundle.hashCode() : 0);
        result = 31 * result + (dataInput != null ? dataInput.hashCode() : 0);
        result = 31 * result + (companyOwner != null ? companyOwner.hashCode() : 0);
        result = 31 * result + (companyReviewer != null ? companyReviewer.hashCode() : 0);
        result = 31 * result + (reviewSignOffs != null ? reviewSignOffs.hashCode() : 0);
        result = 31 * result + (submissionFiles != null ? submissionFiles.hashCode() : 0);
        result = 31 * result + (inputType != null ? inputType.hashCode() : 0);
        result = 31 * result + (orderIndex != null ? orderIndex.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CompanyDataInput{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", status=" + status +
            ", company=" + company +
            ", companyDataBundle=" + companyDataBundle +
            ", dataInput=" + dataInput +
            ", companyOwner=" + companyOwner +
            ", companyReviewer=" + companyReviewer +
            ", reviewSignOffs=" + reviewSignOffs +
            ", submissionFiles=" + submissionFiles +
            ", inputType=" + inputType +
            ", orderIndex=" + orderIndex +
            '}';
    }
}
