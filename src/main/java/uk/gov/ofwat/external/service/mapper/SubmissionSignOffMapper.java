package uk.gov.ofwat.external.service.mapper;

import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.service.dto.SubmissionSignOffDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SubmissionSignOff and its DTO SubmissionSignOffDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CompanyDataBundleMapper.class, })
public interface SubmissionSignOffMapper extends EntityMapper <SubmissionSignOffDTO, SubmissionSignOff> {

    @Mapping(source = "signatory.id", target = "signatoryId")
    @Mapping(source = "signatory.firstName", target = "signatoryFirstName")

    @Mapping(source = "companyDataBundle.id", target = "companyDataBundleId")
    @Mapping(source = "companyDataBundle.name", target = "companyDataBundleName")
    SubmissionSignOffDTO toDto(SubmissionSignOff submissionSignOff); 

    @Mapping(source = "signatoryId", target = "signatory")

    @Mapping(source = "companyDataBundleId", target = "companyDataBundle")
    SubmissionSignOff toEntity(SubmissionSignOffDTO submissionSignOffDTO); 
    default SubmissionSignOff fromId(Long id) {
        if (id == null) {
            return null;
        }
        SubmissionSignOff submissionSignOff = new SubmissionSignOff();
        submissionSignOff.setId(id);
        return submissionSignOff;
    }
}
