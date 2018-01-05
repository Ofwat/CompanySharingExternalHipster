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

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyDataInput companyDataInput = (CompanyDataInput) o;
        if (companyDataInput.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyDataInput.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyDataInput{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
