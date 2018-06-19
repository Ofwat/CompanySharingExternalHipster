package uk.gov.ofwat.external.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uk.gov.ofwat.external.domain.ReviewSignOff;
import uk.gov.ofwat.external.service.dto.ReviewSignOffDTO;

/**
 * Mapper for the entity ReviewSignOff and its DTO ReviewSignOffDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CompanyDataInputMapper.class, })
public interface ReviewSignOffMapper extends EntityMapper <ReviewSignOffDTO, ReviewSignOff> {

    @Mapping(source = "signatory.id", target = "signatoryId")
    @Mapping(source = "signatory.firstName", target = "signatoryFirstName")

    @Mapping(source = "companyDataInput.id", target = "companyDataInputId")
    @Mapping(source = "companyDataInput.name", target = "companyDataInputName")
    ReviewSignOffDTO toDto(ReviewSignOff reviewSignOff);

    @Mapping(source = "signatoryId", target = "signatory")

    @Mapping(source = "companyDataInputId", target = "companyDataInput")
    ReviewSignOff toEntity(ReviewSignOffDTO reviewSignOffDTO);
    default ReviewSignOff fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReviewSignOff reviewSignOff = new ReviewSignOff();
        reviewSignOff.setId(id);
        return reviewSignOff;
    }
}
