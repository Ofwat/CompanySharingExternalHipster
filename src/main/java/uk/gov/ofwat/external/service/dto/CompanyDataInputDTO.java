package uk.gov.ofwat.external.service.dto;


import uk.gov.ofwat.external.domain.CompanyDataInput;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the CompanyDataInput entity.
 */
public class CompanyDataInputDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Long statusId;

    private String statusStatus;

    private Long companyId;

    private String companyName;

    private Long companyDataBundleId;

    private String companyDataBundleName;

    private Long dataInputId;

    private String dataInputName;

    private Long companyOwnerId;

    private String companyOwnerFirstName;

    private Long companyReviewerId;

    private String companyReviewerFirstName;

    private Long inputTypeId;

    private String inputTypeType;

    private Long orderIndex;

    private Long companyDataInputOrderIndex;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;

    public CompanyDataInputDTO() {

    }

    public CompanyDataInputDTO(CompanyDataInput companyDataInput) {

        this(companyDataInput.getId(),companyDataInput.getName(),companyDataInput.getStatus().getId(),companyDataInput.getStatus().getStatus(),
            companyDataInput.getCompany().getId(), companyDataInput.getCompany().getName(),companyDataInput.getCompanyDataBundle().getId(),
            companyDataInput.getCompanyDataBundle().getName(),companyDataInput.getDataInput().getId(),companyDataInput.getDataInput().getName(),
            companyDataInput.getCompanyOwner().getId(),companyDataInput.getCompanyOwner().getFirstName(),companyDataInput.getCompanyReviewer().getId(),
            companyDataInput.getCompanyReviewer().getFirstName(),companyDataInput.getInputType().getId(),companyDataInput.getInputType().getType(),
            companyDataInput.getOrderIndex(),companyDataInput.getCompanyDataInputOrderIndex(),companyDataInput.getCreatedDate(),companyDataInput.getCreatedBy(),
            companyDataInput.getLastModifiedDate(),companyDataInput.getLastModifiedBy());
    }

    public CompanyDataInputDTO(Long id, String name, Long statusId, String statusStatus, Long companyId, String companyName,
                               Long companyDataBundleId, String companyDataBundleName, Long dataInputId, String dataInputName,
                               Long companyOwnerId, String companyOwnerFirstName, Long companyReviewerId,
                               String companyReviewerFirstName, Long inputTypeId, String inputTypeType, Long orderIndex,
                               Long companyDataInputOrderIndex, Instant createdDate, String createdBy, Instant lastModifiedDate,
                               String lastModifiedBy) {
        this.id = id;
        this.name = name;
        this.statusId = statusId;
        this.statusStatus = statusStatus;
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyDataBundleId = companyDataBundleId;
        this.companyDataBundleName = companyDataBundleName;
        this.dataInputId = dataInputId;
        this.dataInputName = dataInputName;
        this.companyOwnerId = companyOwnerId;
        this.companyOwnerFirstName = companyOwnerFirstName;
        this.companyReviewerId = companyReviewerId;
        this.companyReviewerFirstName = companyReviewerFirstName;
        this.inputTypeId = inputTypeId;
        this.inputTypeType = inputTypeType;
        this.orderIndex = orderIndex;
        this.companyDataInputOrderIndex = companyDataInputOrderIndex;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastModifiedDate = lastModifiedDate;
        this.lastModifiedBy = lastModifiedBy;
    }

    public Long getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Long orderIndex) {
        this.orderIndex = orderIndex;
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

    public Long getCompanyDataBundleId() {
        return companyDataBundleId;
    }

    public void setCompanyDataBundleId(Long companyDataBundleId) {
        this.companyDataBundleId = companyDataBundleId;
    }

    public String getCompanyDataBundleName() {
        return companyDataBundleName;
    }

    public void setCompanyDataBundleName(String companyDataBundleName) {
        this.companyDataBundleName = companyDataBundleName;
    }

    public Long getDataInputId() {
        return dataInputId;
    }

    public void setDataInputId(Long dataInputId) {
        this.dataInputId = dataInputId;
    }

    public String getDataInputName() {
        return dataInputName;
    }

    public void setDataInputName(String dataInputName) {
        this.dataInputName = dataInputName;
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

    public Long getInputTypeId() {
        return inputTypeId;
    }

    public void setInputTypeId(Long inputTypeId) {
        this.inputTypeId = inputTypeId;
    }

    public String getInputTypeType() {
        return inputTypeType;
    }

    public void setInputTypeType(String inputTypeType) {
        this.inputTypeType = inputTypeType;
    }

    public Long getCompanyDataInputOrderIndex() {
        return companyDataInputOrderIndex;
    }

    public void setCompanyDataInputOrderIndex(Long companyDataInputOrderIndex) {
        this.companyDataInputOrderIndex = companyDataInputOrderIndex;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyDataInputDTO)) return false;

        CompanyDataInputDTO that = (CompanyDataInputDTO) o;

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
        if (getCompanyDataBundleId() != null ? !getCompanyDataBundleId().equals(that.getCompanyDataBundleId()) : that.getCompanyDataBundleId() != null)
            return false;
        if (getCompanyDataBundleName() != null ? !getCompanyDataBundleName().equals(that.getCompanyDataBundleName()) : that.getCompanyDataBundleName() != null)
            return false;
        if (getDataInputId() != null ? !getDataInputId().equals(that.getDataInputId()) : that.getDataInputId() != null)
            return false;
        if (getDataInputName() != null ? !getDataInputName().equals(that.getDataInputName()) : that.getDataInputName() != null)
            return false;
        if (getCompanyOwnerId() != null ? !getCompanyOwnerId().equals(that.getCompanyOwnerId()) : that.getCompanyOwnerId() != null)
            return false;
        if (getCompanyOwnerFirstName() != null ? !getCompanyOwnerFirstName().equals(that.getCompanyOwnerFirstName()) : that.getCompanyOwnerFirstName() != null)
            return false;
        if (getCompanyReviewerId() != null ? !getCompanyReviewerId().equals(that.getCompanyReviewerId()) : that.getCompanyReviewerId() != null)
            return false;
        if (getCompanyReviewerFirstName() != null ? !getCompanyReviewerFirstName().equals(that.getCompanyReviewerFirstName()) : that.getCompanyReviewerFirstName() != null)
            return false;
        if (getInputTypeId() != null ? !getInputTypeId().equals(that.getInputTypeId()) : that.getInputTypeId() != null)
            return false;
        if (getInputTypeType() != null ? !getInputTypeType().equals(that.getInputTypeType()) : that.getInputTypeType() != null)
            return false;
        if (getOrderIndex() != null ? !getOrderIndex().equals(that.getOrderIndex()) : that.getOrderIndex() != null)
            return false;
        if (getCompanyDataInputOrderIndex() != null ? !getCompanyDataInputOrderIndex().equals(that.getCompanyDataInputOrderIndex()) : that.getCompanyDataInputOrderIndex() != null)
            return false;
        if (getCreatedDate() != null ? !getCreatedDate().equals(that.getCreatedDate()) : that.getCreatedDate() != null)
            return false;
        if (getCreatedBy() != null ? !getCreatedBy().equals(that.getCreatedBy()) : that.getCreatedBy() != null)
            return false;
        if (getLastModifiedDate() != null ? !getLastModifiedDate().equals(that.getLastModifiedDate()) : that.getLastModifiedDate() != null)
            return false;
        return getLastModifiedBy() != null ? getLastModifiedBy().equals(that.getLastModifiedBy()) : that.getLastModifiedBy() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getStatusId() != null ? getStatusId().hashCode() : 0);
        result = 31 * result + (getStatusStatus() != null ? getStatusStatus().hashCode() : 0);
        result = 31 * result + (getCompanyId() != null ? getCompanyId().hashCode() : 0);
        result = 31 * result + (getCompanyName() != null ? getCompanyName().hashCode() : 0);
        result = 31 * result + (getCompanyDataBundleId() != null ? getCompanyDataBundleId().hashCode() : 0);
        result = 31 * result + (getCompanyDataBundleName() != null ? getCompanyDataBundleName().hashCode() : 0);
        result = 31 * result + (getDataInputId() != null ? getDataInputId().hashCode() : 0);
        result = 31 * result + (getDataInputName() != null ? getDataInputName().hashCode() : 0);
        result = 31 * result + (getCompanyOwnerId() != null ? getCompanyOwnerId().hashCode() : 0);
        result = 31 * result + (getCompanyOwnerFirstName() != null ? getCompanyOwnerFirstName().hashCode() : 0);
        result = 31 * result + (getCompanyReviewerId() != null ? getCompanyReviewerId().hashCode() : 0);
        result = 31 * result + (getCompanyReviewerFirstName() != null ? getCompanyReviewerFirstName().hashCode() : 0);
        result = 31 * result + (getInputTypeId() != null ? getInputTypeId().hashCode() : 0);
        result = 31 * result + (getInputTypeType() != null ? getInputTypeType().hashCode() : 0);
        result = 31 * result + (getOrderIndex() != null ? getOrderIndex().hashCode() : 0);
        result = 31 * result + (getCompanyDataInputOrderIndex() != null ? getCompanyDataInputOrderIndex().hashCode() : 0);
        result = 31 * result + (getCreatedDate() != null ? getCreatedDate().hashCode() : 0);
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        result = 31 * result + (getLastModifiedDate() != null ? getLastModifiedDate().hashCode() : 0);
        result = 31 * result + (getLastModifiedBy() != null ? getLastModifiedBy().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CompanyDataInputDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", statusId=" + statusId +
            ", statusStatus='" + statusStatus + '\'' +
            ", companyId=" + companyId +
            ", companyName='" + companyName + '\'' +
            ", companyDataBundleId=" + companyDataBundleId +
            ", companyDataBundleName='" + companyDataBundleName + '\'' +
            ", dataInputId=" + dataInputId +
            ", dataInputName='" + dataInputName + '\'' +
            ", companyOwnerId=" + companyOwnerId +
            ", companyOwnerFirstName='" + companyOwnerFirstName + '\'' +
            ", companyReviewerId=" + companyReviewerId +
            ", companyReviewerFirstName='" + companyReviewerFirstName + '\'' +
            ", inputTypeId=" + inputTypeId +
            ", inputTypeType='" + inputTypeType + '\'' +
            ", orderIndex=" + orderIndex +
            ", companyDataInputOrderIndex=" + companyDataInputOrderIndex +
            ", createdDate=" + createdDate +
            ", createdBy='" + createdBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            '}';
    }
}
