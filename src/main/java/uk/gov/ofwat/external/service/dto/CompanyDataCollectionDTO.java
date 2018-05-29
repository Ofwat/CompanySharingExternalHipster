package uk.gov.ofwat.external.service.dto;


import uk.gov.ofwat.external.domain.CompanyDataCollection;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * A DTO for the CompanyDataCollection entity.
 */
public class CompanyDataCollectionDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Long statusId;

    private String statusStatus;

    private Long companyId;

    private String companyName;

    private Long dataCollectionId;

    private String dataCollectionName;

    private Long companyOwnerId;

    private String companyOwnerFirstName;

    private Long companyReviewerId;

    private String companyReviewerFirstName;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;

    private CompanyDataBundleDTO[] companyDataBundles;

    private Long companyDataCollectionOrderIndex;

    public CompanyDataCollectionDTO() {

    }


    public CompanyDataCollectionDTO(CompanyDataCollection companyDataCollection) {
        this(companyDataCollection.getId(), companyDataCollection.getName(),companyDataCollection.getStatus().getId(),
            companyDataCollection.getStatus().getStatus(),companyDataCollection.getCompany().getId(),
            companyDataCollection.getCompany().getName(),companyDataCollection.getDataCollection().getId(),
            companyDataCollection.getDataCollection().getName(),companyDataCollection.getCompanyOwner().getId(),
            companyDataCollection.getCompanyOwner().getFirstName(),companyDataCollection.getCompanyReviewer().getId(),
            companyDataCollection.getCompanyReviewer().getFirstName(), companyDataCollection.getCreatedDate(),
            companyDataCollection.getCreatedBy(),companyDataCollection.getLastModifiedDate(),companyDataCollection.getLastModifiedBy(),
            Arrays.stream(companyDataCollection.getCompanyDataBundles()).map(CompanyDataBundleDTO::new).collect(Collectors.toList()).toArray(new CompanyDataBundleDTO[Arrays.stream(companyDataCollection.getCompanyDataBundles()).map(CompanyDataBundleDTO::new).collect(Collectors.toList()).size()]),companyDataCollection.getCompanyDataCollectionOrderIndex());
    }


    public CompanyDataCollectionDTO(Long id, String name, Long statusId, String statusStatus, Long companyId,
                                    String companyName, Long dataCollectionId, String dataCollectionName, Long companyOwnerId,
                                    String companyOwnerFirstName, Long companyReviewerId, String companyReviewerFirstName,
                                    Instant createdDate, String createdBy, Instant lastModifiedDate, String lastModifiedBy,
                                    CompanyDataBundleDTO[] companyDataBundles, Long companyDataCollectionOrderIndex) {
        this.id = id;
        this.name = name;
        this.statusId = statusId;
        this.statusStatus = statusStatus;
        this.companyId = companyId;
        this.companyName = companyName;
        this.dataCollectionId = dataCollectionId;
        this.dataCollectionName = dataCollectionName;
        this.companyOwnerId = companyOwnerId;
        this.companyOwnerFirstName = companyOwnerFirstName;
        this.companyReviewerId = companyReviewerId;
        this.companyReviewerFirstName = companyReviewerFirstName;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastModifiedDate = lastModifiedDate;
        this.lastModifiedBy = lastModifiedBy;
        this.companyDataBundles = companyDataBundles;
        this.companyDataCollectionOrderIndex = companyDataCollectionOrderIndex;
    }

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

    public void setStatusId(Long companyStatusId) {
        this.statusId = companyStatusId;
    }

    public String getStatusStatus() {
        return statusStatus;
    }

    public void setStatusStatus(String companyStatusStatus) {
        this.statusStatus = companyStatusStatus;
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

    public Long getDataCollectionId() {
        return dataCollectionId;
    }

    public void setDataCollectionId(Long dataCollectionId) {
        this.dataCollectionId = dataCollectionId;
    }

    public String getDataCollectionName() {
        return dataCollectionName;
    }

    public void setDataCollectionName(String dataCollectionName) {
        this.dataCollectionName = dataCollectionName;
    }

    public Long getCompanyOwnerId() {
        return companyOwnerId;
    }

    public void setCompanyOwnerId(Long userId) {
        this.companyOwnerId = userId;
    }

    public String getCompanyOwnerFirstName() {
        return companyOwnerFirstName;
    }

    public void setCompanyOwnerFirstName(String userFirstName) {
        this.companyOwnerFirstName = userFirstName;
    }

    public Long getCompanyReviewerId() {
        return companyReviewerId;
    }

    public void setCompanyReviewerId(Long userId) {
        this.companyReviewerId = userId;
    }

    public String getCompanyReviewerFirstName() {
        return companyReviewerFirstName;
    }

    public void setCompanyReviewerFirstName(String userFirstName) {
        this.companyReviewerFirstName = userFirstName;
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

    public CompanyDataBundleDTO[] getCompanyDataBundles() {
        return companyDataBundles;
    }

    public void setCompanyDataBundles(CompanyDataBundleDTO[] companyDataBundles) {
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
        if (!(o instanceof CompanyDataCollectionDTO)) return false;

        CompanyDataCollectionDTO that = (CompanyDataCollectionDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getStatusId() != null ? !getStatusId().equals(that.getStatusId()) : that.getStatusId() != null)
            return false;
        if (getStatusStatus() != null ? !getStatusStatus().equals(that.getStatusStatus()) : that.getStatusStatus() != null)
            return false;
        if (getCompanyId() != null ? !getCompanyId().equals(that.getCompanyId()) : that.getCompanyId() != null)
            return false;
        if (getCompanyName() != null ? !getCompanyName().equals(that.getCompanyName()) : that.getCompanyName() != null)
            return false;
        if (getDataCollectionId() != null ? !getDataCollectionId().equals(that.getDataCollectionId()) : that.getDataCollectionId() != null)
            return false;
        if (getDataCollectionName() != null ? !getDataCollectionName().equals(that.getDataCollectionName()) : that.getDataCollectionName() != null)
            return false;
        if (getCompanyOwnerId() != null ? !getCompanyOwnerId().equals(that.getCompanyOwnerId()) : that.getCompanyOwnerId() != null)
            return false;
        if (getCompanyOwnerFirstName() != null ? !getCompanyOwnerFirstName().equals(that.getCompanyOwnerFirstName()) : that.getCompanyOwnerFirstName() != null)
            return false;
        if (getCompanyReviewerId() != null ? !getCompanyReviewerId().equals(that.getCompanyReviewerId()) : that.getCompanyReviewerId() != null)
            return false;
        if (getCompanyReviewerFirstName() != null ? !getCompanyReviewerFirstName().equals(that.getCompanyReviewerFirstName()) : that.getCompanyReviewerFirstName() != null)
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
        if (!Arrays.equals(getCompanyDataBundles(), that.getCompanyDataBundles())) return false;
        return getCompanyDataCollectionOrderIndex() != null ? getCompanyDataCollectionOrderIndex().equals(that.getCompanyDataCollectionOrderIndex()) : that.getCompanyDataCollectionOrderIndex() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getStatusId() != null ? getStatusId().hashCode() : 0);
        result = 31 * result + (getStatusStatus() != null ? getStatusStatus().hashCode() : 0);
        result = 31 * result + (getCompanyId() != null ? getCompanyId().hashCode() : 0);
        result = 31 * result + (getCompanyName() != null ? getCompanyName().hashCode() : 0);
        result = 31 * result + (getDataCollectionId() != null ? getDataCollectionId().hashCode() : 0);
        result = 31 * result + (getDataCollectionName() != null ? getDataCollectionName().hashCode() : 0);
        result = 31 * result + (getCompanyOwnerId() != null ? getCompanyOwnerId().hashCode() : 0);
        result = 31 * result + (getCompanyOwnerFirstName() != null ? getCompanyOwnerFirstName().hashCode() : 0);
        result = 31 * result + (getCompanyReviewerId() != null ? getCompanyReviewerId().hashCode() : 0);
        result = 31 * result + (getCompanyReviewerFirstName() != null ? getCompanyReviewerFirstName().hashCode() : 0);
        result = 31 * result + (getCreatedDate() != null ? getCreatedDate().hashCode() : 0);
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        result = 31 * result + (getLastModifiedDate() != null ? getLastModifiedDate().hashCode() : 0);
        result = 31 * result + (getLastModifiedBy() != null ? getLastModifiedBy().hashCode() : 0);
        result = 31 * result + Arrays.hashCode(getCompanyDataBundles());
        result = 31 * result + (getCompanyDataCollectionOrderIndex() != null ? getCompanyDataCollectionOrderIndex().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CompanyDataCollectionDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", statusId=" + statusId +
            ", statusStatus='" + statusStatus + '\'' +
            ", companyId=" + companyId +
            ", companyName='" + companyName + '\'' +
            ", dataCollectionId=" + dataCollectionId +
            ", dataCollectionName='" + dataCollectionName + '\'' +
            ", companyOwnerId=" + companyOwnerId +
            ", companyOwnerFirstName='" + companyOwnerFirstName + '\'' +
            ", companyReviewerId=" + companyReviewerId +
            ", companyReviewerFirstName='" + companyReviewerFirstName + '\'' +
            ", createdDate=" + createdDate +
            ", createdBy='" + createdBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", companyDataBundles=" + Arrays.toString(companyDataBundles) +
            ", companyDataCollectionOrderIndex=" + companyDataCollectionOrderIndex +
            '}';
    }
}
