package uk.gov.ofwat.external.service.mapper;

import org.mapstruct.Mapper;
import uk.gov.ofwat.external.domain.CompanyStatus;
import uk.gov.ofwat.external.service.dto.CompanyStatusDTO;

/**
 * Mapper for the entity CompanyStatus and its DTO CompanyStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyStatusMapper extends EntityMapper <CompanyStatusDTO, CompanyStatus> {


    default CompanyStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyStatus companyStatus = new CompanyStatus();
        companyStatus.setId(id);
        return companyStatus;
    }
}
