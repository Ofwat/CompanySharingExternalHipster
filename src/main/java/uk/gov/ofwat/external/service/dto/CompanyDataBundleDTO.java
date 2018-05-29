package uk.gov.ofwat.external.service.dto;


import uk.gov.ofwat.external.domain.CompanyDataBundle;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

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

    private Long orderIndex;

    private CompanyDataInputDTO[]  companyDataInputs;

    private Long companyDataBundleOrderIndex;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;

    public CompanyDataBundleDTO() {

    }

    public CompanyDataBundleDTO(CompanyDataBundle companyDataBundle) {
        this(companyDataBundle.getId(),companyDataBundle.getName(),companyDataBundle.getCompanyDeadline(),companyDataBundle.getStatus().getId(),companyDataBundle.getStatus().getStatus(),
            companyDataBundle.getCompany().getId(),companyDataBundle.getCompany().getName(),companyDataBundle.getCompanyDataCollection().getId(),
            companyDataBundle.getCompanyDataCollection().getName(), companyDataBundle.getDataBundle().getId(), companyDataBundle.getDataBundle().getName(),
            companyDataBundle.getCompanyOwner().getId(),companyDataBundle.getCompanyOwner().getFirstName(),
            companyDataBundle.getCompanyReviewer().getId(),companyDataBundle.getCompanyReviewer().getFirstName(),
            companyDataBundle.getOrderIndex(), Arrays.stream(companyDataBundle.getCompanyDataInputs()).map(CompanyDataInputDTO::new).collect(Collectors.toList()).toArray(new CompanyDataInputDTO[Arrays.stream(companyDataBundle.getCompanyDataInputs()).map(CompanyDataInputDTO::new).collect(Collectors.toList()).size()]),companyDataBundle.getCompanyDataBundleOrderIndex(),
            companyDataBundle.getCreatedDate(),companyDataBundle.getCreatedBy(),companyDataBundle.getLastModifiedDate(),companyDataBundle.getLastModifiedBy()
            );
    }

    public CompanyDataBundleDTO(Long id, String name, LocalDate companyDeadline, Long statusId, String statusStatus,
                                Long companyId, String companyName, Long companyDataCollectionId,
                                String companyDataCollectionName, Long dataBundleId, String dataBundleName,
                                Long companyOwnerId, String companyOwnerFirstName, Long companyReviewerId,
                                String companyReviewerFirstName, Long orderIndex, CompanyDataInputDTO[] companyDataInputs,
                                Long companyDataBundleOrderIndex, Instant createdDate, String createdBy,
                                Instant lastModifiedDate, String lastModifiedBy) {
        this.id = id;
        this.name = name;
        this.companyDeadline = companyDeadline;
        this.statusId = statusId;
        this.statusStatus = statusStatus;
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyDataCollectionId = companyDataCollectionId;
        this.companyDataCollectionName = companyDataCollectionName;
        this.dataBundleId = dataBundleId;
        this.dataBundleName = dataBundleName;
        this.companyOwnerId = companyOwnerId;
        this.companyOwnerFirstName = companyOwnerFirstName;
        this.companyReviewerId = companyReviewerId;
        this.companyReviewerFirstName = companyReviewerFirstName;
        this.orderIndex = orderIndex;
        this.companyDataInputs = companyDataInputs;
        this.companyDataBundleOrderIndex = companyDataBundleOrderIndex;
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

    public CompanyDataInputDTO[] getCompanyDataInputs() {
        return companyDataInputs;
    }

    public void setCompanyDataInputs(CompanyDataInputDTO[] companyDataInputs) {
        this.companyDataInputs = companyDataInputs;
    }

    public Long getCompanyDataBundleOrderIndex() {
        return companyDataBundleOrderIndex;
    }

    public void setCompanyDataBundleOrderIndex(Long companyDataBundleOrderIndex) {
        this.companyDataBundleOrderIndex = companyDataBundleOrderIndex;
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
        if (!(o instanceof CompanyDataBundleDTO)) return false;

        CompanyDataBundleDTO that = (CompanyDataBundleDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getCompanyDeadline() != null ? !getCompanyDeadline().equals(that.getCompanyDeadline()) : that.getCompanyDeadline() != null)
            return false;
        if (getStatusId() != null ? !getStatusId().equals(that.getStatusId()) : that.getStatusId() != null)
            return false;
        if (getStatusStatus() != null ? !getStatusStatus().equals(that.getStatusStatus()) : that.getStatusStatus() != null)
            return false;
        if (getCompanyId() != null ? !getCompanyId().equals(that.getCompanyId()) : that.getCompanyId() != null)
            return false;
        if (getCompanyName() != null ? !getCompanyName().equals(that.getCompanyName()) : that.getCompanyName() != null)
            return false;
        if (getCompanyDataCollectionId() != null ? !getCompanyDataCollectionId().equals(that.getCompanyDataCollectionId()) : that.getCompanyDataCollectionId() != null)
            return false;
        if (getCompanyDataCollectionName() != null ? !getCompanyDataCollectionName().equals(that.getCompanyDataCollectionName()) : that.getCompanyDataCollectionName() != null)
            return false;
        if (getDataBundleId() != null ? !getDataBundleId().equals(that.getDataBundleId()) : that.getDataBundleId() != null)
            return false;
        if (getDataBundleName() != null ? !getDataBundleName().equals(that.getDataBundleName()) : that.getDataBundleName() != null)
            return false;
        if (getCompanyOwnerId() != null ? !getCompanyOwnerId().equals(that.getCompanyOwnerId()) : that.getCompanyOwnerId() != null)
            return false;
        if (getCompanyOwnerFirstName() != null ? !getCompanyOwnerFirstName().equals(that.getCompanyOwnerFirstName()) : that.getCompanyOwnerFirstName() != null)
            return false;
        if (getCompanyReviewerId() != null ? !getCompanyReviewerId().equals(that.getCompanyReviewerId()) : that.getCompanyReviewerId() != null)
            return false;
        if (getCompanyReviewerFirstName() != null ? !getCompanyReviewerFirstName().equals(that.getCompanyReviewerFirstName()) : that.getCompanyReviewerFirstName() != null)
            return false;
        if (getOrderIndex() != null ? !getOrderIndex().equals(that.getOrderIndex()) : that.getOrderIndex() != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(getCompanyDataInputs(), that.getCompanyDataInputs())) return false;
        if (getCompanyDataBundleOrderIndex() != null ? !getCompanyDataBundleOrderIndex().equals(that.getCompanyDataBundleOrderIndex()) : that.getCompanyDataBundleOrderIndex() != null)
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
        result = 31 * result + (getCompanyDeadline() != null ? getCompanyDeadline().hashCode() : 0);
        result = 31 * result + (getStatusId() != null ? getStatusId().hashCode() : 0);
        result = 31 * result + (getStatusStatus() != null ? getStatusStatus().hashCode() : 0);
        result = 31 * result + (getCompanyId() != null ? getCompanyId().hashCode() : 0);
        result = 31 * result + (getCompanyName() != null ? getCompanyName().hashCode() : 0);
        result = 31 * result + (getCompanyDataCollectionId() != null ? getCompanyDataCollectionId().hashCode() : 0);
        result = 31 * result + (getCompanyDataCollectionName() != null ? getCompanyDataCollectionName().hashCode() : 0);
        result = 31 * result + (getDataBundleId() != null ? getDataBundleId().hashCode() : 0);
        result = 31 * result + (getDataBundleName() != null ? getDataBundleName().hashCode() : 0);
        result = 31 * result + (getCompanyOwnerId() != null ? getCompanyOwnerId().hashCode() : 0);
        result = 31 * result + (getCompanyOwnerFirstName() != null ? getCompanyOwnerFirstName().hashCode() : 0);
        result = 31 * result + (getCompanyReviewerId() != null ? getCompanyReviewerId().hashCode() : 0);
        result = 31 * result + (getCompanyReviewerFirstName() != null ? getCompanyReviewerFirstName().hashCode() : 0);
        result = 31 * result + (getOrderIndex() != null ? getOrderIndex().hashCode() : 0);
        result = 31 * result + Arrays.hashCode(getCompanyDataInputs());
        result = 31 * result + (getCompanyDataBundleOrderIndex() != null ? getCompanyDataBundleOrderIndex().hashCode() : 0);
        result = 31 * result + (getCreatedDate() != null ? getCreatedDate().hashCode() : 0);
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        result = 31 * result + (getLastModifiedDate() != null ? getLastModifiedDate().hashCode() : 0);
        result = 31 * result + (getLastModifiedBy() != null ? getLastModifiedBy().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CompanyDataBundleDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", companyDeadline=" + companyDeadline +
            ", statusId=" + statusId +
            ", statusStatus='" + statusStatus + '\'' +
            ", companyId=" + companyId +
            ", companyName='" + companyName + '\'' +
            ", companyDataCollectionId=" + companyDataCollectionId +
            ", companyDataCollectionName='" + companyDataCollectionName + '\'' +
            ", dataBundleId=" + dataBundleId +
            ", dataBundleName='" + dataBundleName + '\'' +
            ", companyOwnerId=" + companyOwnerId +
            ", companyOwnerFirstName='" + companyOwnerFirstName + '\'' +
            ", companyReviewerId=" + companyReviewerId +
            ", companyReviewerFirstName='" + companyReviewerFirstName + '\'' +
            ", orderIndex=" + orderIndex +
            ", companyDataInputs=" + Arrays.toString(companyDataInputs) +
            ", companyDataBundleOrderIndex=" + companyDataBundleOrderIndex +
            ", createdDate=" + createdDate +
            ", createdBy='" + createdBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            '}';
    }
}
