package uk.gov.ofwat.external.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PublishingStatus entity.
 */
public class PublishingStatusDTO implements Serializable {

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

        PublishingStatusDTO publishingStatusDTO = (PublishingStatusDTO) o;
        if(publishingStatusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), publishingStatusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PublishingStatusDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
