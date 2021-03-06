package uk.gov.ofwat.external.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uk.gov.ofwat.external.domain.CompanyDataBundle;
import uk.gov.ofwat.external.service.dto.CompanyDataBundleDTO;

/**
 * Mapper for the entity CompanyDataBundle and its DTO CompanyDataBundleDTO.
 */
@Mapper(componentModel = "spring", uses = {CompanyStatusMapper.class, CompanyMapper.class, CompanyDataCollectionMapper.class, DataBundleMapper.class,UserMapper.class,CompanyDataInputMapper.class})
public interface CompanyDataBundleMapper extends EntityMapper <CompanyDataBundleDTO, CompanyDataBundle> {

    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.status", target = "statusStatus")

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")

    @Mapping(source = "companyDataCollection.id", target = "companyDataCollectionId")
    @Mapping(source = "companyDataCollection.name", target = "companyDataCollectionName")

    @Mapping(source = "dataBundle.id", target = "dataBundleId")
    @Mapping(source = "dataBundle.name", target = "dataBundleName")

    @Mapping(source = "companyOwner.id", target = "companyOwnerId")
    @Mapping(source = "companyOwner.firstName", target = "companyOwnerFirstName")

    @Mapping(source = "companyReviewer.id", target = "companyReviewerId")
    @Mapping(source = "companyReviewer.firstName", target = "companyReviewerFirstName")

    @Mapping(source = "companyDataBundle.companyDataInputs", target = "companyDataInputs")
    CompanyDataBundleDTO toDto(CompanyDataBundle companyDataBundle);

    @Mapping(source = "statusId", target = "status")

    @Mapping(source = "companyId", target = "company")

    @Mapping(source = "companyDataCollectionId", target = "companyDataCollection")

    @Mapping(source = "dataBundleId", target = "dataBundle")

    @Mapping(source = "companyOwnerId", target = "companyOwner")

    @Mapping(source = "companyReviewerId", target = "companyReviewer")
    @Mapping(target = "submissionSignOffs", ignore = true)

    @Mapping(source = "companyDataInputs", target = "companyDataInputs")
    CompanyDataBundle toEntity(CompanyDataBundleDTO companyDataBundleDTO);

    default CompanyDataBundle fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyDataBundle companyDataBundle = new CompanyDataBundle();
        companyDataBundle.setId(id);
        return companyDataBundle;
    }
}
