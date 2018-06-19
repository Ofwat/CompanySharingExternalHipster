package uk.gov.ofwat.external.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.domain.PublishingStatus;
import uk.gov.ofwat.external.repository.PublishingStatusRepository;
import uk.gov.ofwat.external.service.dto.PublishingStatusDTO;
import uk.gov.ofwat.external.service.mapper.PublishingStatusMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PublishingStatus.
 */
@Service
@Transactional
public class PublishingStatusService {

    private final Logger log = LoggerFactory.getLogger(PublishingStatusService.class);

    private final PublishingStatusRepository publishingStatusRepository;

    private final PublishingStatusMapper publishingStatusMapper;

    public PublishingStatusService(PublishingStatusRepository publishingStatusRepository, PublishingStatusMapper publishingStatusMapper) {
        this.publishingStatusRepository = publishingStatusRepository;
        this.publishingStatusMapper = publishingStatusMapper;
    }

    /**
     * Save a publishingStatus.
     *
     * @param publishingStatusDTO the entity to save
     * @return the persisted entity
     */
    public PublishingStatusDTO save(PublishingStatusDTO publishingStatusDTO) {
        log.debug("Request to save PublishingStatus : {}", publishingStatusDTO);
        PublishingStatus publishingStatus = publishingStatusMapper.toEntity(publishingStatusDTO);
        publishingStatus = publishingStatusRepository.save(publishingStatus);
        return publishingStatusMapper.toDto(publishingStatus);
    }

    /**
     *  Get all the publishingStatuses.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PublishingStatusDTO> findAll() {
        log.debug("Request to get all PublishingStatuses");
        return publishingStatusRepository.findAll().stream()
            .map(publishingStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one publishingStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PublishingStatusDTO findOne(Long id) {
        log.debug("Request to get PublishingStatus : {}", id);
        PublishingStatus publishingStatus = publishingStatusRepository.findOne(id);
        return publishingStatusMapper.toDto(publishingStatus);
    }

    /**
     *  Delete the  publishingStatus by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PublishingStatus : {}", id);
        publishingStatusRepository.delete(id);
    }
}
