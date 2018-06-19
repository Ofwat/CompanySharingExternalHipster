package uk.gov.ofwat.external.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uk.gov.ofwat.external.domain.DataFile;
import uk.gov.ofwat.external.service.dto.DataFileDTO;

/**
 * Mapper for the entity DataFile and its DTO DataFileDTO.
 */
@Mapper(componentModel = "spring", uses = {CompanyDataInputMapper.class, })
public interface DataFileMapper extends EntityMapper <DataFileDTO, DataFile> {

    @Mapping(source = "companyDataInput.id", target = "companyDataInputId")
    @Mapping(source = "companyDataInput.name", target = "companyDataInputName")
    DataFileDTO toDto(DataFile dataFile);

    @Mapping(source = "companyDataInputId", target = "companyDataInput")
    DataFile toEntity(DataFileDTO dataFileDTO);
    default DataFile fromId(Long id) {
        if (id == null) {
            return null;
        }
        DataFile dataFile = new DataFile();
        dataFile.setId(id);
        return dataFile;
    }
}
