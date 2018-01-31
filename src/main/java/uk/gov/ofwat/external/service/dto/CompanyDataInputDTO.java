package uk.gov.ofwat.external.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDataInputDTO companyDataInputDTO = (CompanyDataInputDTO) o;
        if(companyDataInputDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyDataInputDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyDataInputDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
