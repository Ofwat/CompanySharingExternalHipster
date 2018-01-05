package uk.gov.ofwat.external.service.mapper;

import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.service.dto.CompanyStatusDTO;

import org.mapstruct.*;

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
