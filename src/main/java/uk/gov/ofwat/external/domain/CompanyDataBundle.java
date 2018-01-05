package uk.gov.ofwat.external.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CompanyDataBundle.
 */
@Entity
@Table(name = "company_data_bundle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompanyDataBundle implements Serializable {

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
    private CompanyStatus status;

    @ManyToOne(optional = false)
    @NotNull
    private Company company;

    @ManyToOne(optional = false)
    @NotNull
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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnore
    @JoinColumn(name="COMPANY_DATA_COLLECTION_ID", nullable=false)
    private CompanyDataCollection companyDataCollection;


    @OneToMany(mappedBy = "companyDataBundle")
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
}
