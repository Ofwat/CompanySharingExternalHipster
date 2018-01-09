package uk.gov.ofwat.external.service.mapper;

import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.service.dto.CompanyDataInputDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CompanyDataInput and its DTO CompanyDataInputDTO.
 */
@Mapper(componentModel = "spring", uses = {CompanyStatusMapper.class, CompanyMapper.class, CompanyDataCollectionMapper.class,CompanyDataBundleMapper.class, DataInputMapper.class, UserMapper.class, InputTypeMapper.class, })
public interface CompanyDataInputMapper extends EntityMapper <CompanyDataInputDTO, CompanyDataInput> {

    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "status.status", target = "statusStatus")

    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")

    @Mapping(source = "companyDataBundle.id", target = "companyDataBundleId")
    @Mapping(source = "companyDataBundle.name", target = "companyDataBundleName")

    @Mapping(source = "dataInput.id", target = "dataInputId")
    @Mapping(source = "dataInput.name", target = "dataInputName")

    @Mapping(source = "companyOwner.id", target = "companyOwnerId")
    @Mapping(source = "companyOwner.firstName", target = "companyOwnerFirstName")

    @Mapping(source = "companyReviewer.id", target = "companyReviewerId")
    @Mapping(source = "companyReviewer.firstName", target = "companyReviewerFirstName")

    @Mapping(source = "inputType.id", target = "inputTypeId")
    @Mapping(source = "inputType.type", target = "inputTypeType")
    CompanyDataInputDTO toDto(CompanyDataInput companyDataInput);

    @Mapping(source = "statusId", target = "status")

    @Mapping(source = "companyId", target = "company")

    @Mapping(source = "companyDataBundleId", target = "companyDataBundle")

    @Mapping(source = "dataInputId", target = "dataInput")

    @Mapping(source = "companyOwnerId", target = "companyOwner")

    @Mapping(source = "companyReviewerId", target = "companyReviewer")
    @Mapping(target = "reviewSignOffs", ignore = true)
    @Mapping(target = "submissionFiles", ignore = true)

    @Mapping(source = "inputTypeId", target = "inputType")
    CompanyDataInput toEntity(CompanyDataInputDTO companyDataInputDTO);
    default CompanyDataInput fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyDataInput companyDataInput = new CompanyDataInput();
        companyDataInput.setId(id);
        return companyDataInput;
    }
}
