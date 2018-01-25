package uk.gov.ofwat.external.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the InputType entity.
 */
public class InputTypeDTO implements Serializable {

    private Long id;

    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InputTypeDTO inputTypeDTO = (InputTypeDTO) o;
        if(inputTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inputTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InputTypeDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
