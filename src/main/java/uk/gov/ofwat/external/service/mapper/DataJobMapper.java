package uk.gov.ofwat.external.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uk.gov.ofwat.external.domain.DataInput;
import uk.gov.ofwat.external.domain.DataJob;
import uk.gov.ofwat.external.domain.data.DCSTable;
import uk.gov.ofwat.external.service.dto.DataInputDTO;
import uk.gov.ofwat.external.service.dto.DataJobDto;
import uk.gov.ofwat.external.service.dto.data.TableDto;

/**
 * Mapper for the entity DataInput and its DTO DataInputDTO.
 */
@Mapper(componentModel = "spring", uses = {PublishingStatusMapper.class, DataBundleMapper.class, UserMapper.class, })
public interface DataJobMapper extends EntityMapper <DataJobDto, DataJob> {


//    private Long id;
//    private String uuid;
//    private String jobStatus;
//    private String fountainReportId;
//    private String companyId;
//    private String auditComment;
//    private String runId;
//    private String excelMongoDocId;
//    private Long companyDataInputId;
//    private String rejectedReason;
//    private Boolean updated;
//    private String data;


//    @Mapping(target = "rejectionCodes", ignore = true)
//    @Mapping(target = "createdBy", ignore = true)
//    @Mapping(target = "createdDate", ignore = true)
//    @Mapping(target = "lastModifiedBy", ignore = true)
//    @Mapping(target = "lastModifiedDate", ignore = true)
//    @Mapping(source = "dcsTable.rows", target = "rows")
    DataJobDto toDto(DataJob dataJob);

//    @Mapping(target = "company", ignore = true)
//    @Mapping(source = "tableDto.rows", target = "rows")
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
