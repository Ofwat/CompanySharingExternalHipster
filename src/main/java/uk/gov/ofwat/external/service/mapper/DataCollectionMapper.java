package uk.gov.ofwat.external.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uk.gov.ofwat.external.domain.DataCollection;
import uk.gov.ofwat.external.service.dto.DataCollectionDTO;

/**
 * Mapper for the entity DataCollection and its DTO DataCollectionDTO.
 */
@Mapper(componentModel = "spring", uses = {PublishingStatusMapper.class, UserMapper.class, DataBundleMapper.class})
public interface DataCollectionMapper extends EntityMapper <DataCollectionDTO, DataCollection> {

    @Mapping(source = "publishingStatus.id", target = "statusId")
    @Mapping(source = "publishingStatus.status", target = "statusStatus")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.firstName", target = "ownerFirstName")
    @Mapping(source = "owner.lastName", target = "ownerLastName")
    @Mapping(source = "reviewer.id", target = "reviewerId")
    @Mapping(source = "reviewer.firstName", target = "reviewerFirstName")
    @Mapping(source = "reviewer.lastName", target = "reviewerLastName")
    @Mapping(source = "dataCollection.dataBundles", target = "dataBundles")
    DataCollectionDTO toDto(DataCollection dataCollection);


    @Mapping(source = "statusId", target = "publishingStatus")
    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "reviewerId", target = "reviewer")
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
