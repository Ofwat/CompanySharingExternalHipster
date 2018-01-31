package uk.gov.ofwat.external.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uk.gov.ofwat.external.domain.data.DCSCell;
import uk.gov.ofwat.external.service.dto.data.CellDto;

@Mapper(componentModel = "spring")
public interface DCSCellMapper extends EntityMapper <CellDto, DCSCell> {

    @Mapping(target = "row", ignore = true)
    CellDto toDto(DCSCell dcsCell);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "row", ignore = true)
    DCSCell toEntity(CellDto cellDto);

    default DCSCell fromId(Long id) {
        if (id == null) {
            return null;
        }
        DCSCell dcsCell = new DCSCell();
        dcsCell.setId(id);
        return dcsCell;
    }
}
