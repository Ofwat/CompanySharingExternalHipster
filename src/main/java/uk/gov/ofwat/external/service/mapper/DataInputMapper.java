package uk.gov.ofwat.external.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uk.gov.ofwat.external.domain.DataInput;
import uk.gov.ofwat.external.service.dto.DataInputDTO;

/**
 * Mapper for the entity DataInput and its DTO DataInputDTO.
 */
@Mapper(componentModel = "spring", uses = {PublishingStatusMapper.class, DataBundleMapper.class, UserMapper.class, })
public interface DataInputMapper extends EntityMapper <DataInputDTO, DataInput> {

    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.status", target = "statusStatus")
    @Mapping(source = "dataBundle.id", target = "dataBundleId")
    @Mapping(source = "dataBundle.name", target = "dataBundleName")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.firstName", target = "ownerFirstName")
    @Mapping(source = "owner.lastName", target = "ownerLastName")
    @Mapping(source = "reviewer.id", target = "reviewerId")
    @Mapping(source = "reviewer.firstName", target = "reviewerFirstName")
    @Mapping(source = "reviewer.lastName", target = "reviewerLastName")
    @Mapping(source = "dataInput.companyDataInput", target = "companyDataInput")
    DataInputDTO toDto(DataInput dataInput);

    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "dataBundleId", target = "dataBundle")
    @Mapping(source = "ownerId", target = "owner")
    @Mapping(source = "reviewerId", target = "reviewer")
    @Mapping(source = "companyDataInput", target = "companyDataInput")
    DataInput toEntity(DataInputDTO dataInputDTO);

    default DataInput fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataInput dataInput = new DataInput();
        dataInput.setId(id);
        return dataInput;
    }
}
