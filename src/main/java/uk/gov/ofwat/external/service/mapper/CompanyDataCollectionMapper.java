package uk.gov.ofwat.external.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uk.gov.ofwat.external.domain.CompanyDataCollection;
import uk.gov.ofwat.external.service.dto.CompanyDataCollectionDTO;

/**
 * Mapper for the entity CompanyDataCollection and its DTO CompanyDataCollectionDTO.
 */
@Mapper(componentModel = "spring", uses = {CompanyStatusMapper.class, CompanyMapper.class, DataCollectionMapper.class, UserMapper.class,CompanyDataBundleMapper.class })
public interface CompanyDataCollectionMapper extends EntityMapper <CompanyDataCollectionDTO, CompanyDataCollection> {

    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.status", target = "statusStatus")

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")

    @Mapping(source = "dataCollection.id", target = "dataCollectionId")
    @Mapping(source = "dataCollection.name", target = "dataCollectionName")

    @Mapping(source = "companyOwner.id", target = "companyOwnerId")
    @Mapping(source = "companyOwner.firstName", target = "companyOwnerFirstName")

    @Mapping(source = "companyReviewer.id", target = "companyReviewerId")
    @Mapping(source = "companyReviewer.firstName", target = "companyReviewerFirstName")
    @Mapping(source = "companyDataCollection.companyDataBundles", target = "companyDataBundles")
    CompanyDataCollectionDTO toDto(CompanyDataCollection companyDataCollection);

    @Mapping(source = "statusId", target = "status")

    @Mapping(source = "companyId", target = "company")

    @Mapping(source = "dataCollectionId", target = "dataCollection")

    @Mapping(source = "companyOwnerId", target = "companyOwner")

    @Mapping(source = "companyReviewerId", target = "companyReviewer")
    @Mapping(source = "companyDataBundles", target = "companyDataBundles")
    CompanyDataCollection toEntity(CompanyDataCollectionDTO companyDataCollectionDTO);

    default CompanyDataCollection fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyDataCollection companyDataCollection = new CompanyDataCollection();
        companyDataCollection.setId(id);
        return companyDataCollection;
    }

}
