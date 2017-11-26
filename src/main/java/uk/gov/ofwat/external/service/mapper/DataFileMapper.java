package uk.gov.ofwat.external.service.mapper;

import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.service.dto.DataFileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DataFile and its DTO DataFileDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DataFileMapper extends EntityMapper <DataFileDTO, DataFile> {
    
    
    default DataFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataFile dataFile = new DataFile();
        dataFile.setId(id);
        return dataFile;
    }
}
