package uk.gov.ofwat.external.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.service.dto.CompanyDTO;

/**
 * Mapper for the entity Company and its DTO CompanyDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper extends EntityMapper <CompanyDTO, Company> {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "companyUserDetails", ignore = true)
    default Company fromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
