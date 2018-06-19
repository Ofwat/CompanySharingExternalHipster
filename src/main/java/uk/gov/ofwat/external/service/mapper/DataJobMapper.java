package uk.gov.ofwat.external.service.mapper;

import org.mapstruct.Mapper;
import uk.gov.ofwat.external.domain.DataJob;
import uk.gov.ofwat.external.service.dto.DataJobDto;

/**
 * Mapper for the entity DataInput and its DTO DataInputDTO.
 */
@Mapper(componentModel = "spring", uses = {PublishingStatusMapper.class, DataBundleMapper.class, UserMapper.class, })
public interface DataJobMapper extends EntityMapper <DataJobDto, DataJob> {

    DataJobDto toDto(DataJob dataJob);
    DataJob toEntity(DataJobDto dataJobDto);

    default DataJob fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataJob dataJob = new DataJob();
        dataJob.setId(id);
        return dataJob;
    }
}
