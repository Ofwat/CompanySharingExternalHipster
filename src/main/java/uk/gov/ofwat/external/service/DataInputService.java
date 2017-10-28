package uk.gov.ofwat.external.service;

import uk.gov.ofwat.external.domain.DataInput;
import uk.gov.ofwat.external.repository.DataInputRepository;
import uk.gov.ofwat.external.service.dto.DataInputDTO;
import uk.gov.ofwat.external.service.mapper.DataInputMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing DataInput.
 */
@Service
@Transactional
public class DataInputService {

    private final Logger log = LoggerFactory.getLogger(DataInputService.class);

    private final DataInputRepository dataInputRepository;

    private final DataInputMapper dataInputMapper;

    public DataInputService(DataInputRepository dataInputRepository, DataInputMapper dataInputMapper) {
        this.dataInputRepository = dataInputRepository;
        this.dataInputMapper = dataInputMapper;
    }

    /**
     * Save a dataInput.
     *
     * @param dataInputDTO the entity to save
     * @return the persisted entity
     */
    public DataInputDTO save(DataInputDTO dataInputDTO) {
        log.debug("Request to save DataInput : {}", dataInputDTO);
        DataInput dataInput = dataInputMapper.toEntity(dataInputDTO);
        dataInput = dataInputRepository.save(dataInput);
        return dataInputMapper.toDto(dataInput);
    }

    /**
     *  Get all the dataInputs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DataInputDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DataInputs");
        return dataInputRepository.findAll(pageable)
            .map(dataInputMapper::toDto);
    }

    /**
     *  Get one dataInput by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public DataInputDTO findOne(Long id) {
        log.debug("Request to get DataInput : {}", id);
        DataInput dataInput = dataInputRepository.findOne(id);
        return dataInputMapper.toDto(dataInput);
    }

    /**
     *  Delete the  dataInput by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DataInput : {}", id);
        dataInputRepository.delete(id);
    }
}
