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
public class RejectionResourceDTO implements Serializable {

    private Long id;

    @NotNull
    private String jobStatus;

    private String rejectedReason;

    private String companyName;

    private String companyDataInputName;

    private String companyDataBundleName;

    private String companyDeadline;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDataInputName() {
        return companyDataInputName;
    }

    public void setCompanyDataInputName(String companyDataInputName) {
        this.companyDataInputName = companyDataInputName;
    }

    public String getCompanyDataBundleName() {
        return companyDataBundleName;
    }

    public void setCompanyDataBundleName(String companyDataBundleName) {
        this.companyDataBundleName = companyDataBundleName;
    }

    public String getCompanyDeadline() {
        return companyDeadline;
    }

    public void setCompanyDeadline(String companyDeadline) {
        this.companyDeadline = companyDeadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RejectionResourceDTO)) return false;

        RejectionResourceDTO that = (RejectionResourceDTO) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getJobStatus() != null ? !getJobStatus().equals(that.getJobStatus()) : that.getJobStatus() != null)
            return false;
        if (getRejectedReason() != null ? !getRejectedReason().equals(that.getRejectedReason()) : that.getRejectedReason() != null)
            return false;
        if (getCompanyName() != null ? !getCompanyName().equals(that.getCompanyName()) : that.getCompanyName() != null)
            return false;
        if (getCompanyDataInputName() != null ? !getCompanyDataInputName().equals(that.getCompanyDataInputName()) : that.getCompanyDataInputName() != null)
            return false;
        if (getCompanyDataBundleName() != null ? !getCompanyDataBundleName().equals(that.getCompanyDataBundleName()) : that.getCompanyDataBundleName() != null)
            return false;
        return getCompanyDeadline() != null ? getCompanyDeadline().equals(that.getCompanyDeadline()) : that.getCompanyDeadline() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getJobStatus() != null ? getJobStatus().hashCode() : 0);
        result = 31 * result + (getRejectedReason() != null ? getRejectedReason().hashCode() : 0);
        result = 31 * result + (getCompanyName() != null ? getCompanyName().hashCode() : 0);
        result = 31 * result + (getCompanyDataInputName() != null ? getCompanyDataInputName().hashCode() : 0);
        result = 31 * result + (getCompanyDataBundleName() != null ? getCompanyDataBundleName().hashCode() : 0);
        result = 31 * result + (getCompanyDeadline() != null ? getCompanyDeadline().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RejectionResourceDTO{" +
            "id=" + id +
            ", jobStatus='" + jobStatus + '\'' +
            ", rejectedReason='" + rejectedReason + '\'' +
            ", companyName='" + companyName + '\'' +
            ", companyDataInputName='" + companyDataInputName + '\'' +
            ", companyDataBundleName='" + companyDataBundleName + '\'' +
            ", companyDeadline='" + companyDeadline + '\'' +
            '}';
    }
}
