package uk.gov.ofwat.external.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CompanyStatus entity.
 */
public class CompanyStatusDTO implements Serializable {

    private Long id;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyStatusDTO companyStatusDTO = (CompanyStatusDTO) o;
        if(companyStatusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyStatusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyStatusDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
