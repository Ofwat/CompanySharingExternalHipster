package uk.gov.ofwat.external.service.mapper;

import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.service.dto.DataCollectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DataCollection and its DTO DataCollectionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DataCollectionMapper extends EntityMapper <DataCollectionDTO, DataCollection> {
    
    
    default DataCollection fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataCollection dataCollection = new DataCollection();
        dataCollection.setId(id);
        return dataCollection;
    }
}
