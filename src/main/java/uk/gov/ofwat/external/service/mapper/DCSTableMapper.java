package uk.gov.ofwat.external.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uk.gov.ofwat.external.domain.data.DCSTable;
import uk.gov.ofwat.external.service.dto.data.TableDto;

/**
 * Mapper for the entity DCSTable and its DTO TableDto.
 */
@Mapper(componentModel = "spring", uses = {DCSRowMapper.class})
public interface DCSTableMapper extends EntityMapper <TableDto, DCSTable> {

    @Mapping(target = "excelDocMongoId", ignore = true)
    @Mapping(source = "dcsTable.rows", target = "rows")
    TableDto toDto(DCSTable dcsTable);

    @Mapping(target = "fountainReportId", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "tableDto.rows", target = "rows")
    DCSTable toEntity(TableDto tableDto);

    default DCSTable fromId(Long id) {
        if (id == null) {
            return null;
        }
        DCSTable dcsTable = new DCSTable();
        dcsTable.setId(id);
        return dcsTable;
    }
}
