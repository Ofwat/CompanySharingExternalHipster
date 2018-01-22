package uk.gov.ofwat.external.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.domain.DataBundle;
import uk.gov.ofwat.external.domain.DataCollection;
import uk.gov.ofwat.external.domain.DataInput;
import uk.gov.ofwat.external.repository.DataCollectionRepository;
import uk.gov.ofwat.external.repository.PublishingStatusRepository;
import uk.gov.ofwat.external.service.dto.DataCollectionDTO;
import uk.gov.ofwat.external.service.mapper.DataCollectionMapper;


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
    private final DataBundleService dataBundleService;


    public DataCollectionService(DataCollectionRepository dataCollectionRepository, DataCollectionMapper dataCollectionMapper, PublishingStatusRepository publishingStatusRepository, DataBundleService dataBundleService) {
        this.dataCollectionRepository = dataCollectionRepository;
        this.dataCollectionMapper = dataCollectionMapper;
        this.publishingStatusRepository = publishingStatusRepository;
        this.dataBundleService = dataBundleService;
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
    public DataCollectionDTO findOne(Long id) {
        log.debug("Request to get DataCollection : {}", id);
        DataCollection dataCollection = dataCollectionRepository.findOne(id);
        return dataCollectionMapper.toDto(dataCollection);
    }

    @Transactional(readOnly = true)
    public DataCollectionDTO findOneByName(String name) {
        log.debug("Request to get DataCollection : {}", name);
        DataCollection dataCollection = dataCollectionRepository.findOneByName(name);
        return dataCollectionMapper.toDto(dataCollection);
    }

    /**
     *  Delete the  dataCollection by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        DataCollection dataCollection = dataCollectionRepository.findOne(id);
        delete(dataCollection);
    }

    private void delete(DataCollection dataCollection) {
        log.debug("Request to delete DataCollection : {}", dataCollection.getId());
        deleteDataBundles(dataCollection);
        dataCollectionRepository.delete(dataCollection.getId());
    }

    private void deleteDataBundles(DataCollection dataCollection) {
        for (DataBundle db : dataCollection.getDataBundles()) {
            dataBundleService.delete(db.getId());
        }
    }

    public void deleteByName(String name) {
        DataCollection dataCollection = dataCollectionRepository.findOneByName(name);
        delete(dataCollection);
    }
}
