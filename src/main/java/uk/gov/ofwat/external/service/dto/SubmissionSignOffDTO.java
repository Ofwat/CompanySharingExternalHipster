package uk.gov.ofwat.external.service.dto;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SubmissionSignOff entity.
 */
public class SubmissionSignOffDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean status;

    private String reason;

    private Long signatoryId;

    private String signatoryFirstName;

    private Long companyDataBundleId;

    private String companyDataBundleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getSignatoryId() {
        return signatoryId;
    }

    public void setSignatoryId(Long userId) {
        this.signatoryId = userId;
    }

    public String getSignatoryFirstName() {
        return signatoryFirstName;
    }

    public void setSignatoryFirstName(String userFirstName) {
        this.signatoryFirstName = userFirstName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubmissionSignOffDTO submissionSignOffDTO = (SubmissionSignOffDTO) o;
        if(submissionSignOffDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), submissionSignOffDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubmissionSignOffDTO{" +
            "id=" + getId() +
            ", status='" + isStatus() + "'" +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
