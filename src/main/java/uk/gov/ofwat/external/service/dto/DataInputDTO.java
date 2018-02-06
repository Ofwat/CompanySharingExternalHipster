package uk.gov.ofwat.external.service.dto;


import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataInputDTO)) return false;

        DataInputDTO that = (DataInputDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (reportId != null ? !reportId.equals(that.reportId) : that.reportId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (guidance != null ? !guidance.equals(that.guidance) : that.guidance != null) return false;
        if (defaultDeadline != null ? !defaultDeadline.equals(that.defaultDeadline) : that.defaultDeadline != null)
            return false;
        if (orderIndex != null ? !orderIndex.equals(that.orderIndex) : that.orderIndex != null) return false;
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (fileLocation != null ? !fileLocation.equals(that.fileLocation) : that.fileLocation != null) return false;
        if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) return false;
        if (statusStatus != null ? !statusStatus.equals(that.statusStatus) : that.statusStatus != null) return false;
        if (dataBundleId != null ? !dataBundleId.equals(that.dataBundleId) : that.dataBundleId != null) return false;
        if (dataBundleName != null ? !dataBundleName.equals(that.dataBundleName) : that.dataBundleName != null)
            return false;
        if (ownerId != null ? !ownerId.equals(that.ownerId) : that.ownerId != null) return false;
        if (ownerFirstName != null ? !ownerFirstName.equals(that.ownerFirstName) : that.ownerFirstName != null)
            return false;
        if (ownerLastName != null ? !ownerLastName.equals(that.ownerLastName) : that.ownerLastName != null)
            return false;
        if (reviewerId != null ? !reviewerId.equals(that.reviewerId) : that.reviewerId != null) return false;
        if (reviewerFirstName != null ? !reviewerFirstName.equals(that.reviewerFirstName) : that.reviewerFirstName != null)
            return false;
        if (reviewerLastName != null ? !reviewerLastName.equals(that.reviewerLastName) : that.reviewerLastName != null)
            return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (lastModifiedDate != null ? !lastModifiedDate.equals(that.lastModifiedDate) : that.lastModifiedDate != null)
            return false;
        return lastModifiedBy != null ? lastModifiedBy.equals(that.lastModifiedBy) : that.lastModifiedBy == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (reportId != null ? reportId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (guidance != null ? guidance.hashCode() : 0);
        result = 31 * result + (defaultDeadline != null ? defaultDeadline.hashCode() : 0);
        result = 31 * result + (orderIndex != null ? orderIndex.hashCode() : 0);
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (fileLocation != null ? fileLocation.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (statusStatus != null ? statusStatus.hashCode() : 0);
        result = 31 * result + (dataBundleId != null ? dataBundleId.hashCode() : 0);
        result = 31 * result + (dataBundleName != null ? dataBundleName.hashCode() : 0);
        result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0);
        result = 31 * result + (ownerFirstName != null ? ownerFirstName.hashCode() : 0);
        result = 31 * result + (ownerLastName != null ? ownerLastName.hashCode() : 0);
        result = 31 * result + (reviewerId != null ? reviewerId.hashCode() : 0);
        result = 31 * result + (reviewerFirstName != null ? reviewerFirstName.hashCode() : 0);
        result = 31 * result + (reviewerLastName != null ? reviewerLastName.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
        result = 31 * result + (lastModifiedBy != null ? lastModifiedBy.hashCode() : 0);
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
            '}';
    }
}
