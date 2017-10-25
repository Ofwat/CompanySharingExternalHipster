package uk.gov.ofwat.external.service.mapper;

import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.service.dto.PublishingStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PublishingStatus and its DTO PublishingStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PublishingStatusMapper extends EntityMapper <PublishingStatusDTO, PublishingStatus> {
    
    
    default PublishingStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        PublishingStatus publishingStatus = new PublishingStatus();
        publishingStatus.setId(id);
        return publishingStatus;
    }
}
