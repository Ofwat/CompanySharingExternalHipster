package uk.gov.ofwat.external.service;

import uk.gov.ofwat.external.domain.DataFile;
import uk.gov.ofwat.external.repository.DataFileRepository;
import uk.gov.ofwat.external.service.dto.DataFileDTO;
import uk.gov.ofwat.external.service.mapper.DataFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing DataFile.
 */
@Service
@Transactional
public class DataFileService {

    private final Logger log = LoggerFactory.getLogger(DataFileService.class);

    private final DataFileRepository dataFileRepository;

    private final DataFileMapper dataFileMapper;

    public DataFileService(DataFileRepository dataFileRepository, DataFileMapper dataFileMapper) {
        this.dataFileRepository = dataFileRepository;
        this.dataFileMapper = dataFileMapper;
    }

    /**
     * Save a dataFile.
     *
     * @param dataFileDTO the entity to save
     * @return the persisted entity
     */
    public DataFileDTO save(DataFileDTO dataFileDTO) {
        log.debug("Request to save DataFile : {}", dataFileDTO);
        DataFile dataFile = dataFileMapper.toEntity(dataFileDTO);
        dataFile = dataFileRepository.save(dataFile);
        return dataFileMapper.toDto(dataFile);
    }

    /**
     *  Get all the dataFiles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DataFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DataFiles");
        return dataFileRepository.findAll(pageable)
            .map(dataFileMapper::toDto);
    }

    /**
     *  Get one dataFile by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public DataFileDTO findOne(Long id) {
        log.debug("Request to get DataFile : {}", id);
        DataFile dataFile = dataFileRepository.findOne(id);
        return dataFileMapper.toDto(dataFile);
    }

    /**
     *  Delete the  dataFile by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DataFile : {}", id);
        dataFileRepository.delete(id);
    }
}
