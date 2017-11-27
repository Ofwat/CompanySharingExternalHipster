package uk.gov.ofwat.external.service.mapper;

import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.service.dto.CompanyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Company and its DTO CompanyDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface CompanyMapper extends EntityMapper <CompanyDTO, Company> {
    
    
    default Company fromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
