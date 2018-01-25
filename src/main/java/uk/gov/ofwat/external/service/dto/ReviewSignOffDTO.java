package uk.gov.ofwat.external.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ReviewSignOff entity.
 */
public class ReviewSignOffDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean status;

    private String reason;

    private Long signatoryId;

    private String signatoryFirstName;

    private Long companyDataInputId;

    private String companyDataInputName;

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

    public Long getCompanyDataInputId() {
        return companyDataInputId;
    }

    public void setCompanyDataInputId(Long companyDataInputId) {
        this.companyDataInputId = companyDataInputId;
    }

    public String getCompanyDataInputName() {
        return companyDataInputName;
    }

    public void setCompanyDataInputName(String companyDataInputName) {
        this.companyDataInputName = companyDataInputName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReviewSignOffDTO reviewSignOffDTO = (ReviewSignOffDTO) o;
        if(reviewSignOffDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reviewSignOffDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReviewSignOffDTO{" +
            "id=" + getId() +
            ", status='" + isStatus() + "'" +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
