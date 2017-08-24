package uk.gov.ofwat.external.service;

import uk.gov.ofwat.external.domain.DataCollection;
import uk.gov.ofwat.external.repository.DataCollectionRepository;
import uk.gov.ofwat.external.service.dto.DataCollectionDTO;
import uk.gov.ofwat.external.service.mapper.DataCollectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing DataCollection.
 */
@Service
@Transactional
public class DataCollectionService {

    private final Logger log = LoggerFactory.getLogger(DataCollectionService.class);

    private final DataCollectionRepository dataCollectionRepository;

    private final DataCollectionMapper dataCollectionMapper;

    public DataCollectionService(DataCollectionRepository dataCollectionRepository, DataCollectionMapper dataCollectionMapper) {
        this.dataCollectionRepository = dataCollectionRepository;
        this.dataCollectionMapper = dataCollectionMapper;
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
    public DataCollectionDTO findOne(Long id) {
        log.debug("Request to get DataCollection : {}", id);
        DataCollection dataCollection = dataCollectionRepository.findOne(id);
        return dataCollectionMapper.toDto(dataCollection);
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
}
