package uk.gov.ofwat.external.service.dto;


import uk.gov.ofwat.external.domain.CompanyDataInput;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * A DTO for the DataInput entity.
 */
public class DataInputDTO implements Serializable {

    private Long id;
    private Long reportId;
    @NotNull private String name;
    private String description;
    private String guidance;
    private LocalDate defaultDeadline;
    @NotNull private Long orderIndex;
    @NotNull private String fileName;
    @NotNull private String fileLocation;
    private Long statusId;
    private String statusStatus;
    private Long dataBundleId;
    private String dataBundleName;
    private Long ownerId;
    private String ownerFirstName;
    private String ownerLastName;
    private Long reviewerId;
    private String reviewerFirstName;
    private String reviewerLastName;
    private Instant createdDate;
    private String createdBy;
    private Instant lastModifiedDate;
    private String lastModifiedBy;
    private CompanyDataInput[] companyDataInput;

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

    public LocalDate getDefaultDeadline() {
        return defaultDeadline;
    }

    public void setDefaultDeadline(LocalDate defaultDeadline) {
        this.defaultDeadline = defaultDeadline;
    }

    public Long getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Long orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long publishingStatusId) {
        this.statusId = publishingStatusId;
    }

    public String getStatusStatus() {
        return statusStatus;
    }

    public void setStatusStatus(String publishingStatusStatus) {
        this.statusStatus = publishingStatusStatus;
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long userId) {
        this.ownerId = userId;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String userFirstName) {
        this.ownerFirstName = userFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String userLastName) {
        this.ownerLastName = userLastName;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long userId) {
        this.reviewerId = userId;
    }

    public String getReviewerFirstName() {
        return reviewerFirstName;
    }

    public void setReviewerFirstName(String userFirstName) {
        this.reviewerFirstName = userFirstName;
    }

    public String getReviewerLastName() {
        return reviewerLastName;
    }

    public void setReviewerLastName(String userLastName) {
        this.reviewerLastName = userLastName;
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

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public CompanyDataInput[] getCompanyDataInput() {
        return companyDataInput;
    }

    public void setCompanyDataInput(CompanyDataInput[] companyDataInput) {
        this.companyDataInput = companyDataInput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataInputDTO)) return false;

        DataInputDTO that = (DataInputDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getReportId() != null ? !getReportId().equals(that.getReportId()) : that.getReportId() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getGuidance() != null ? !getGuidance().equals(that.getGuidance()) : that.getGuidance() != null)
            return false;
        if (getDefaultDeadline() != null ? !getDefaultDeadline().equals(that.getDefaultDeadline()) : that.getDefaultDeadline() != null)
            return false;
        if (getOrderIndex() != null ? !getOrderIndex().equals(that.getOrderIndex()) : that.getOrderIndex() != null)
            return false;
        if (getFileName() != null ? !getFileName().equals(that.getFileName()) : that.getFileName() != null)
            return false;
        if (getFileLocation() != null ? !getFileLocation().equals(that.getFileLocation()) : that.getFileLocation() != null)
            return false;
        if (getStatusId() != null ? !getStatusId().equals(that.getStatusId()) : that.getStatusId() != null)
            return false;
        if (getStatusStatus() != null ? !getStatusStatus().equals(that.getStatusStatus()) : that.getStatusStatus() != null)
            return false;
        if (getDataBundleId() != null ? !getDataBundleId().equals(that.getDataBundleId()) : that.getDataBundleId() != null)
            return false;
        if (getDataBundleName() != null ? !getDataBundleName().equals(that.getDataBundleName()) : that.getDataBundleName() != null)
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
        if (getCreatedDate() != null ? !getCreatedDate().equals(that.getCreatedDate()) : that.getCreatedDate() != null)
            return false;
        if (getCreatedBy() != null ? !getCreatedBy().equals(that.getCreatedBy()) : that.getCreatedBy() != null)
            return false;
        if (getLastModifiedDate() != null ? !getLastModifiedDate().equals(that.getLastModifiedDate()) : that.getLastModifiedDate() != null)
            return false;
        if (getLastModifiedBy() != null ? !getLastModifiedBy().equals(that.getLastModifiedBy()) : that.getLastModifiedBy() != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(getCompanyDataInput(), that.getCompanyDataInput());
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getReportId() != null ? getReportId().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getGuidance() != null ? getGuidance().hashCode() : 0);
        result = 31 * result + (getDefaultDeadline() != null ? getDefaultDeadline().hashCode() : 0);
        result = 31 * result + (getOrderIndex() != null ? getOrderIndex().hashCode() : 0);
        result = 31 * result + (getFileName() != null ? getFileName().hashCode() : 0);
        result = 31 * result + (getFileLocation() != null ? getFileLocation().hashCode() : 0);
        result = 31 * result + (getStatusId() != null ? getStatusId().hashCode() : 0);
        result = 31 * result + (getStatusStatus() != null ? getStatusStatus().hashCode() : 0);
        result = 31 * result + (getDataBundleId() != null ? getDataBundleId().hashCode() : 0);
        result = 31 * result + (getDataBundleName() != null ? getDataBundleName().hashCode() : 0);
        result = 31 * result + (getOwnerId() != null ? getOwnerId().hashCode() : 0);
        result = 31 * result + (getOwnerFirstName() != null ? getOwnerFirstName().hashCode() : 0);
        result = 31 * result + (getOwnerLastName() != null ? getOwnerLastName().hashCode() : 0);
        result = 31 * result + (getReviewerId() != null ? getReviewerId().hashCode() : 0);
        result = 31 * result + (getReviewerFirstName() != null ? getReviewerFirstName().hashCode() : 0);
        result = 31 * result + (getReviewerLastName() != null ? getReviewerLastName().hashCode() : 0);
        result = 31 * result + (getCreatedDate() != null ? getCreatedDate().hashCode() : 0);
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        result = 31 * result + (getLastModifiedDate() != null ? getLastModifiedDate().hashCode() : 0);
        result = 31 * result + (getLastModifiedBy() != null ? getLastModifiedBy().hashCode() : 0);
        result = 31 * result + Arrays.hashCode(getCompanyDataInput());
        return result;
    }

    @Override
    public String toString() {
        return "DataInputDTO{" +
            "id=" + id +
            ", reportId=" + reportId +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", guidance='" + guidance + '\'' +
            ", defaultDeadline=" + defaultDeadline +
            ", orderIndex=" + orderIndex +
            ", fileName='" + fileName + '\'' +
            ", fileLocation='" + fileLocation + '\'' +
            ", statusId=" + statusId +
            ", statusStatus='" + statusStatus + '\'' +
            ", dataBundleId=" + dataBundleId +
            ", dataBundleName='" + dataBundleName + '\'' +
            ", ownerId=" + ownerId +
            ", ownerFirstName='" + ownerFirstName + '\'' +
            ", ownerLastName='" + ownerLastName + '\'' +
            ", reviewerId=" + reviewerId +
            ", reviewerFirstName='" + reviewerFirstName + '\'' +
            ", reviewerLastName='" + reviewerLastName + '\'' +
            ", createdDate=" + createdDate +
            ", createdBy='" + createdBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", companyDataInput=" + Arrays.toString(companyDataInput) +
            '}';
    }
}
