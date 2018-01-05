package uk.gov.ofwat.external.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CompanyDataBundle entity.
 */
public class CompanyDataBundleDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private LocalDate companyDeadline;

    private Long statusId;

    private String statusStatus;

    private Long companyId;

    private String companyName;

    private Long companyDataCollectionId;

    private String companyDataCollectionName;

    private Long dataBundleId;

    private String dataBundleName;

    private Long companyOwnerId;

    private String companyOwnerFirstName;

    private Long companyReviewerId;

    private String companyReviewerFirstName;

    private CompanyDataInputDTO[]  companyDataInput;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCompanyDeadline() {
        return companyDeadline;
    }

    public void setCompanyDeadline(LocalDate companyDeadline) {
        this.companyDeadline = companyDeadline;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusStatus() {
        return statusStatus;
    }

    public void setStatusStatus(String statusStatus) {
        this.statusStatus = statusStatus;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCompanyDataCollectionId() {
        return companyDataCollectionId;
    }

    public void setCompanyDataCollectionId(Long companyDataCollectionId) {
        this.companyDataCollectionId = companyDataCollectionId;
    }

    public String getCompanyDataCollectionName() {
        return companyDataCollectionName;
    }

    public void setCompanyDataCollectionName(String companyDataCollectionName) {
        this.companyDataCollectionName = companyDataCollectionName;
    }

    public Long getDataBundleId() {
        return dataBundleId;
    }

    public void setDataBundleId(Long dataBundleId) {
        this.dataBundleId = dataBundleId;
    }

    public String getDataBundleName() {
        return dataBundleName;
    }

    public void setDataBundleName(String dataBundleName) {
        this.dataBundleName = dataBundleName;
    }

    public Long getCompanyOwnerId() {
        return companyOwnerId;
    }

    public void setCompanyOwnerId(Long companyOwnerId) {
        this.companyOwnerId = companyOwnerId;
    }

    public String getCompanyOwnerFirstName() {
        return companyOwnerFirstName;
    }

    public void setCompanyOwnerFirstName(String companyOwnerFirstName) {
        this.companyOwnerFirstName = companyOwnerFirstName;
    }

    public Long getCompanyReviewerId() {
        return companyReviewerId;
    }

    public void setCompanyReviewerId(Long companyReviewerId) {
        this.companyReviewerId = companyReviewerId;
    }

    public String getCompanyReviewerFirstName() {
        return companyReviewerFirstName;
    }

    public void setCompanyReviewerFirstName(String companyReviewerFirstName) {
        this.companyReviewerFirstName = companyReviewerFirstName;
    }

    public CompanyDataInputDTO[] getCompanyDataInput() {
        return companyDataInput;
    }

    public void setCompanyDataInput(CompanyDataInputDTO[] companyDataInput) {
        this.companyDataInput = companyDataInput;
    }
}
