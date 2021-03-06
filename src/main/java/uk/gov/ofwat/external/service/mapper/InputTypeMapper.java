package uk.gov.ofwat.external.service.mapper;

import org.mapstruct.Mapper;
import uk.gov.ofwat.external.domain.InputType;
import uk.gov.ofwat.external.service.dto.InputTypeDTO;

/**
 * Mapper for the entity InputType and its DTO InputTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InputTypeMapper extends EntityMapper <InputTypeDTO, InputType> {


    default InputType fromId(Long id) {
        if (id == null) {
            return null;
        }
        InputType inputType = new InputType();
        inputType.setId(id);
        return inputType;
    }
}
