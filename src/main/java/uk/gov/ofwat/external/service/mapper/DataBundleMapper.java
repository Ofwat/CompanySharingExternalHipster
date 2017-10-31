package uk.gov.ofwat.external.service.mapper;

import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.service.dto.DataBundleDTO;

import org.mapstruct.*;
import uk.gov.ofwat.external.service.dto.DataCollectionDTO;

/**
 * Mapper for the entity DataBundle and its DTO DataBundleDTO.
 */
@Mapper(componentModel = "spring", uses = {PublishingStatusMapper.class, UserMapper.class, DataCollectionMapper.class, DataInputMapper.class, })
public interface DataBundleMapper extends EntityMapper <DataBundleDTO, DataBundle> {

    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.status", target = "statusStatus")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.firstName", target = "ownerFirstName")
    @Mapping(source = "owner.lastName", target = "ownerLastName")
    @Mapping(source = "reviewer.id", target = "reviewerId")
    @Mapping(source = "reviewer.firstName", target = "reviewerFirstName")
    @Mapping(source = "reviewer.lastName", target = "reviewerLastName")
    @Mapping(source = "dataCollection.id", target = "dataCollectionId")
    @Mapping(source = "dataCollection.name", target = "dataCollectionName")
    @Mapping(source = "dataBundle.dataInputs", target = "dataInputs")
    DataBundleDTO toDto(DataBundle dataBundle);

    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "reviewerId", target = "reviewer")
    @Mapping(source = "dataCollectionId", target = "dataCollection")
    @Mapping(source = "dataInputs", target = "dataInputs")
    DataBundle toEntity(DataBundleDTO dataBundleDTO);

    default DataBundle fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataBundle dataBundle = new DataBundle();
        dataBundle.setId(id);
        return dataBundle;
    }
}
