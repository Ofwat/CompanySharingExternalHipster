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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyDataBundleDTO)) return false;

        CompanyDataBundleDTO that = (CompanyDataBundleDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (companyDeadline != null ? !companyDeadline.equals(that.companyDeadline) : that.companyDeadline != null)
            return false;
        if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) return false;
        if (statusStatus != null ? !statusStatus.equals(that.statusStatus) : that.statusStatus != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        if (companyDataCollectionId != null ? !companyDataCollectionId.equals(that.companyDataCollectionId) : that.companyDataCollectionId != null)
            return false;
        if (companyDataCollectionName != null ? !companyDataCollectionName.equals(that.companyDataCollectionName) : that.companyDataCollectionName != null)
            return false;
        if (companyOwnerId != null ? !companyOwnerId.equals(that.companyOwnerId) : that.companyOwnerId != null)
            return false;
        if (companyOwnerFirstName != null ? !companyOwnerFirstName.equals(that.companyOwnerFirstName) : that.companyOwnerFirstName != null)
            return false;
        if (companyReviewerId != null ? !companyReviewerId.equals(that.companyReviewerId) : that.companyReviewerId != null)
            return false;
        return companyReviewerFirstName != null ? companyReviewerFirstName.equals(that.companyReviewerFirstName) : that.companyReviewerFirstName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (companyDeadline != null ? companyDeadline.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (statusStatus != null ? statusStatus.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (companyDataCollectionId != null ? companyDataCollectionId.hashCode() : 0);
        result = 31 * result + (companyDataCollectionName != null ? companyDataCollectionName.hashCode() : 0);
        result = 31 * result + (companyOwnerId != null ? companyOwnerId.hashCode() : 0);
        result = 31 * result + (companyOwnerFirstName != null ? companyOwnerFirstName.hashCode() : 0);
        result = 31 * result + (companyReviewerId != null ? companyReviewerId.hashCode() : 0);
        result = 31 * result + (companyReviewerFirstName != null ? companyReviewerFirstName.hashCode() : 0);
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
            ", companyOwnerId=" + companyOwnerId +
            ", companyOwnerFirstName='" + companyOwnerFirstName + '\'' +
            ", companyReviewerId=" + companyReviewerId +
            ", companyReviewerFirstName='" + companyReviewerFirstName + '\'' +
            '}';
    }
}
