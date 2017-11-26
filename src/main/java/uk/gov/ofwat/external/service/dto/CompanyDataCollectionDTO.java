package uk.gov.ofwat.external.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CompanyDataCollection entity.
 */
public class CompanyDataCollectionDTO implements Serializable {

    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDataCollectionDTO companyDataCollectionDTO = (CompanyDataCollectionDTO) o;
        if(companyDataCollectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyDataCollectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyDataCollectionDTO{" +
            "id=" + getId() +
            "}";
    }
}
