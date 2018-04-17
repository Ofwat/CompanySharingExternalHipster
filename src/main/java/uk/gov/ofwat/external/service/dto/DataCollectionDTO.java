package uk.gov.ofwat.external.service.dto;


import uk.gov.ofwat.external.domain.CompanyDataCollection;
import uk.gov.ofwat.external.domain.PublishingStatus;
import uk.gov.ofwat.external.domain.User;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;

/**
 * A DTO for the DataCollection entity.
 */
public class DataCollectionDTO implements Serializable {

    private Long id;
    @NotNull
    private String name;
    private Long statusId;
    private String statusStatus;
    private Long ownerId;
    private String ownerFirstName;
    private String ownerLastName;
    private Long reviewerId;
    private String reviewerFirstName;
    private String reviewerLastName;
    private String description;
    private String guidance;
    private Instant createdDate;
    private String createdBy;
    private Instant lastModifiedDate;
    private String lastModifiedBy;
    private DataBundleDTO[] dataBundles;
    private CompanyDataCollection[] companyDataCollections;

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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewerFirstName() {
        return reviewerFirstName;
    }

    public void setReviewerFirstName(String reviewerFirstName) {
        this.reviewerFirstName = reviewerFirstName;
    }

    public String getReviewerLastName() {
        return reviewerLastName;
    }

    public void setReviewerLastName(String reviewerLastName) {
        this.reviewerLastName = reviewerLastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGuidance() {
        return guidance;
    }

    public void setGuidance(String guidance) {
        this.guidance = guidance;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public DataBundleDTO[] getDataBundles() {
        return dataBundles;
    }

    public void setDataBundles(DataBundleDTO[] dataBundles) {
        this.dataBundles = dataBundles;
    }

    public CompanyDataCollection[] getCompanyDataCollections() {
        return companyDataCollections;
    }

    public void setCompanyDataCollections(CompanyDataCollection[] companyDataCollections) {
        this.companyDataCollections = companyDataCollections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataCollectionDTO)) return false;

        DataCollectionDTO that = (DataCollectionDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getStatusId() != null ? !getStatusId().equals(that.getStatusId()) : that.getStatusId() != null)
            return false;
        if (getStatusStatus() != null ? !getStatusStatus().equals(that.getStatusStatus()) : that.getStatusStatus() != null)
            return false;
        if (getOwnerId() != null ? !getOwnerId().equals(that.getOwnerId()) : that.getOwnerId() != null) return false;
        if (getOwnerFirstName() != null ? !getOwnerFirstName().equals(that.getOwnerFirstName()) : that.getOwnerFirstName() != null)
            return false;
        if (getOwnerLastName() != null ? !getOwnerLastName().equals(that.getOwnerLastName()) : that.getOwnerLastName() != null)
            return false;
        if (getReviewerId() != null ? !getReviewerId().equals(that.getReviewerId()) : that.getReviewerId() != null)
            return false;
        if (getReviewerFirstName() != null ? !getReviewerFirstName().equals(that.getReviewerFirstName()) : that.getReviewerFirstName() != null)
            return false;
        if (getReviewerLastName() != null ? !getReviewerLastName().equals(that.getReviewerLastName()) : that.getReviewerLastName() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getGuidance() != null ? !getGuidance().equals(that.getGuidance()) : that.getGuidance() != null)
            return false;
        if (getCreatedDate() != null ? !getCreatedDate().equals(that.getCreatedDate()) : that.getCreatedDate() != null)
            return false;
        if (getCreatedBy() != null ? !getCreatedBy().equals(that.getCreatedBy()) : that.getCreatedBy() != null)
            return false;
        if (getLastModifiedDate() != null ? !getLastModifiedDate().equals(that.getLastModifiedDate()) : that.getLastModifiedDate() != null)
            return false;
        if (getLastModifiedBy() != null ? !getLastModifiedBy().equals(that.getLastModifiedBy()) : that.getLastModifiedBy() != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(getDataBundles(), that.getDataBundles())) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(getCompanyDataCollections(), that.getCompanyDataCollections());
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getStatusId() != null ? getStatusId().hashCode() : 0);
        result = 31 * result + (getStatusStatus() != null ? getStatusStatus().hashCode() : 0);
        result = 31 * result + (getOwnerId() != null ? getOwnerId().hashCode() : 0);
        result = 31 * result + (getOwnerFirstName() != null ? getOwnerFirstName().hashCode() : 0);
        result = 31 * result + (getOwnerLastName() != null ? getOwnerLastName().hashCode() : 0);
        result = 31 * result + (getReviewerId() != null ? getReviewerId().hashCode() : 0);
        result = 31 * result + (getReviewerFirstName() != null ? getReviewerFirstName().hashCode() : 0);
        result = 31 * result + (getReviewerLastName() != null ? getReviewerLastName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getGuidance() != null ? getGuidance().hashCode() : 0);
        result = 31 * result + (getCreatedDate() != null ? getCreatedDate().hashCode() : 0);
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        result = 31 * result + (getLastModifiedDate() != null ? getLastModifiedDate().hashCode() : 0);
        result = 31 * result + (getLastModifiedBy() != null ? getLastModifiedBy().hashCode() : 0);
        result = 31 * result + Arrays.hashCode(getDataBundles());
        result = 31 * result + Arrays.hashCode(getCompanyDataCollections());
        return result;
    }

    @Override
    public String toString() {
        return "DataCollectionDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", statusId=" + statusId +
            ", statusStatus='" + statusStatus + '\'' +
            ", ownerId=" + ownerId +
            ", ownerFirstName='" + ownerFirstName + '\'' +
            ", ownerLastName='" + ownerLastName + '\'' +
            ", reviewerId=" + reviewerId +
            ", reviewerFirstName='" + reviewerFirstName + '\'' +
            ", reviewerLastName='" + reviewerLastName + '\'' +
            ", description='" + description + '\'' +
            ", guidance='" + guidance + '\'' +
            ", createdDate=" + createdDate +
            ", createdBy='" + createdBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", dataBundles=" + Arrays.toString(dataBundles) +
            ", companyDataCollections=" + Arrays.toString(companyDataCollections) +
            '}';
    }
}
