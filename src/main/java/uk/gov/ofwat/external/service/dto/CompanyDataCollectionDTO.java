package uk.gov.ofwat.external.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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

    private CompanyDataBundleDTO[] companyDataBundles;

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

    public CompanyDataBundleDTO[] getCompanyDataBundles() {
        return companyDataBundles;
    }

    public void setCompanyDataBundles(CompanyDataBundleDTO[] companyDataBundles) {
        this.companyDataBundles = companyDataBundles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyDataCollectionDTO)) return false;

        CompanyDataCollectionDTO that = (CompanyDataCollectionDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) return false;
        if (statusStatus != null ? !statusStatus.equals(that.statusStatus) : that.statusStatus != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        if (dataCollectionId != null ? !dataCollectionId.equals(that.dataCollectionId) : that.dataCollectionId != null)
            return false;
        if (dataCollectionName != null ? !dataCollectionName.equals(that.dataCollectionName) : that.dataCollectionName != null)
            return false;
        if (companyOwnerId != null ? !companyOwnerId.equals(that.companyOwnerId) : that.companyOwnerId != null)
            return false;
        if (companyOwnerFirstName != null ? !companyOwnerFirstName.equals(that.companyOwnerFirstName) : that.companyOwnerFirstName != null)
            return false;
        if (companyReviewerId != null ? !companyReviewerId.equals(that.companyReviewerId) : that.companyReviewerId != null)
            return false;
        if (companyReviewerFirstName != null ? !companyReviewerFirstName.equals(that.companyReviewerFirstName) : that.companyReviewerFirstName != null)
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(companyDataBundles, that.companyDataBundles);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
        result = 31 * result + (statusStatus != null ? statusStatus.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (dataCollectionId != null ? dataCollectionId.hashCode() : 0);
        result = 31 * result + (dataCollectionName != null ? dataCollectionName.hashCode() : 0);
        result = 31 * result + (companyOwnerId != null ? companyOwnerId.hashCode() : 0);
        result = 31 * result + (companyOwnerFirstName != null ? companyOwnerFirstName.hashCode() : 0);
        result = 31 * result + (companyReviewerId != null ? companyReviewerId.hashCode() : 0);
        result = 31 * result + (companyReviewerFirstName != null ? companyReviewerFirstName.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(companyDataBundles);
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
            ", companyDataBundles=" + Arrays.toString(companyDataBundles) +
            '}';
    }
}
