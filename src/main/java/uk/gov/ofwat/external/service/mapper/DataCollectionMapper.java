package uk.gov.ofwat.external.service.mapper;

import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.service.dto.DataBundleDTO;
import uk.gov.ofwat.external.service.dto.DataCollectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DataCollection and its DTO DataCollectionDTO.
 */
@Mapper(componentModel = "spring", uses = {PublishingStatusMapper.class, UserMapper.class, DataBundleMapper.class})
public interface DataCollectionMapper extends EntityMapper <DataCollectionDTO, DataCollection> {

    @Mapping(source = "dataCollection.dataBundles", target = "dataBundles")
    DataCollectionDTO toDto(DataCollection dataCollection);

    @Mapping(source = "dataBundles", target = "dataBundles")
    DataCollection toEntity(DataCollectionDTO dataCollectionDTO);

    default DataCollection fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataCollection dataCollection = new DataCollection();
        dataCollection.setId(id);
        return dataCollection;
    }
}
