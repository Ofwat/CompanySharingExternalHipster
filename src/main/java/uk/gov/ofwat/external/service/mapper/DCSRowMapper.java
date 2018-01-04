package uk.gov.ofwat.external.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uk.gov.ofwat.external.domain.data.DCSRow;
import uk.gov.ofwat.external.domain.data.DCSTable;
import uk.gov.ofwat.external.service.dto.data.RowDto;
import uk.gov.ofwat.external.service.dto.data.TableDto;

/**
 * Mapper for the entity DCSTable and its DTO TableDto.
 */
@Mapper(componentModel = "spring", uses = {DCSCellMapper.class})
public interface DCSRowMapper extends EntityMapper <RowDto, DCSRow> {

    @Mapping(source = "dcsRow.cells", target = "cells")
    RowDto toDto(DCSRow dcsRow);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "table", ignore = true)
    @Mapping(source = "rowDto.cells", target = "cells")
    DCSRow toEntity(RowDto rowDto);

    default DCSRow fromId(Long id) {
        if (id == null) {
            return null;
        }
        DCSRow dcsRow = new DCSRow();
        dcsRow.setId(id);
        return dcsRow;
    }
}
