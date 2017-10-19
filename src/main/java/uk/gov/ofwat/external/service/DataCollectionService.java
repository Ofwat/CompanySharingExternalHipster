package uk.gov.ofwat.external.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.domain.DataCollection;
import uk.gov.ofwat.external.domain.PublishingStatus;
import uk.gov.ofwat.external.repository.DataCollectionRepository;
import uk.gov.ofwat.external.repository.PublishingStatusRepository;
import uk.gov.ofwat.external.service.dto.DataCollectionDTO;
import uk.gov.ofwat.external.service.mapper.DataCollectionMapper;

import java.util.Optional;


/**
 * Service Implementation for managing DataCollection.
 */
@Service
@Transactional
public class DataCollectionService {

    private final Logger log = LoggerFactory.getLogger(DataCollectionService.class);

    private final DataCollectionRepository dataCollectionRepository;
    private final DataCollectionMapper dataCollectionMapper;
    private final PublishingStatusRepository publishingStatusRepository;

    public DataCollectionService(DataCollectionRepository dataCollectionRepository, DataCollectionMapper dataCollectionMapper, PublishingStatusRepository publishingStatusRepository) {
        this.dataCollectionRepository = dataCollectionRepository;
        this.dataCollectionMapper = dataCollectionMapper;
        this.publishingStatusRepository = publishingStatusRepository;
    }

    /**
     * Save a dataCollection.
     *
     * @param dataCollectionDTO the entity to save
     * @return the persisted entity
     */
    public DataCollectionDTO save(DataCollectionDTO dataCollectionDTO) {
        log.debug("Request to save DataCollection : {}", dataCollectionDTO);
        DataCollection dataCollection = dataCollectionMapper.toEntity(dataCollectionDTO);
        dataCollection = dataCollectionRepository.save(dataCollection);
        return dataCollectionMapper.toDto(dataCollection);
    }


    /**
     * Save a new dataCollection.
     *
     * @param dataCollectionDTO the entity to save
     * @return the persisted entity
     */
    public DataCollectionDTO saveNew(DataCollectionDTO dataCollectionDTO) {
        log.debug("Request to save new DataCollection : {}", dataCollectionDTO);
        return save(dataCollectionDTO);
    }

    /**
     *  Get all the dataCollections.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DataCollectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DataCollections");
        return dataCollectionRepository.findAll(pageable)
            .map(dataCollectionMapper::toDto);
    }

    /**
     *  Get one dataCollection by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DataCollection> findOne(Long id) {
        log.debug("Request to get DataCollection : {}", id);
        return Optional.of(dataCollectionRepository.findOne((Long)id));
    }

    @Transactional(readOnly = true)
    public Optional<DataCollection> findOneByName(String name) {
        log.debug("Request to get DataCollection : {}", name);
        return dataCollectionRepository.findOneByName(name);
    }

    /**
     *  Delete the  dataCollection by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DataCollection : {}", id);
        dataCollectionRepository.delete(id);
    }

    public void deleteByName(String name) {
        log.debug("Request to delete DataCollection : {}", name);
        dataCollectionRepository.deleteByName(name);
    }
}
