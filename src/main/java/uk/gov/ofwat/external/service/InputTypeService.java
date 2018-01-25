package uk.gov.ofwat.external.service;

import uk.gov.ofwat.external.domain.InputType;
import uk.gov.ofwat.external.repository.InputTypeRepository;
import uk.gov.ofwat.external.service.dto.InputTypeDTO;
import uk.gov.ofwat.external.service.mapper.InputTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing InputType.
 */
@Service
@Transactional
public class InputTypeService {

    private final Logger log = LoggerFactory.getLogger(InputTypeService.class);

    private final InputTypeRepository inputTypeRepository;

    private final InputTypeMapper inputTypeMapper;

    public InputTypeService(InputTypeRepository inputTypeRepository, InputTypeMapper inputTypeMapper) {
        this.inputTypeRepository = inputTypeRepository;
        this.inputTypeMapper = inputTypeMapper;
    }

    /**
     * Save a inputType.
     *
     * @param inputTypeDTO the entity to save
     * @return the persisted entity
     */
    public InputTypeDTO save(InputTypeDTO inputTypeDTO) {
        log.debug("Request to save InputType : {}", inputTypeDTO);
        InputType inputType = inputTypeMapper.toEntity(inputTypeDTO);
        inputType = inputTypeRepository.save(inputType);
        return inputTypeMapper.toDto(inputType);
    }

    /**
     *  Get all the inputTypes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<InputTypeDTO> findAll() {
        log.debug("Request to get all InputTypes");
        return inputTypeRepository.findAll().stream()
            .map(inputTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one inputType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public InputTypeDTO findOne(Long id) {
        log.debug("Request to get InputType : {}", id);
        InputType inputType = inputTypeRepository.findOne(id);
        return inputTypeMapper.toDto(inputType);
    }

    /**
     *  Delete the  inputType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete InputType : {}", id);
        inputTypeRepository.delete(id);
    }
}
