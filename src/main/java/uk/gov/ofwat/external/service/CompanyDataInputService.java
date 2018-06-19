package uk.gov.ofwat.external.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.domain.CompanyDataInput;
import uk.gov.ofwat.external.repository.CompanyDataInputRepository;
import uk.gov.ofwat.external.service.dto.CompanyDataInputDTO;
import uk.gov.ofwat.external.service.mapper.CompanyDataInputMapper;


/**
 * Service Implementation for managing CompanyDataInput.
 */
@Service
@Transactional
public class CompanyDataInputService {

    private final Logger log = LoggerFactory.getLogger(CompanyDataInputService.class);

    private final CompanyDataInputRepository companyDataInputRepository;

    private final CompanyDataInputMapper companyDataInputMapper;


    public CompanyDataInputService(CompanyDataInputRepository companyDataInputRepository, CompanyDataInputMapper companyDataInputMapper) {
        this.companyDataInputRepository = companyDataInputRepository;
        this.companyDataInputMapper = companyDataInputMapper;
    }

    /**
     * Save a companyDataInput.
     *
     * @param companyDataInputDTO the entity to save
     * @return the persisted entity
     */
    public CompanyDataInputDTO save(CompanyDataInputDTO companyDataInputDTO) {
        log.debug("Request to save CompanyDataInput : {}", companyDataInputDTO);
        CompanyDataInput companyDataInput = companyDataInputMapper.toEntity(companyDataInputDTO);
        companyDataInput = companyDataInputRepository.save(companyDataInput);
        return companyDataInputMapper.toDto(companyDataInput);
    }

    /**
     *  Get all the companyDataInputs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CompanyDataInputDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyDataInputs");
        return companyDataInputRepository.findAll(pageable)
            .map(companyDataInputMapper::toDto);
    }

    /**
     *  Get one companyDataInput by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CompanyDataInputDTO findOne(Long id) {
        log.debug("Request to get CompanyDataInput : {}", id);
        CompanyDataInput companyDataInput = companyDataInputRepository.findOne(id);
        return companyDataInputMapper.toDto(companyDataInput);
    }


    /**
     *  Get one companyDataInput by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CompanyDataInput findCompanyDataInput(Long id) {
        log.debug("Request to get CompanyDataInput : {}", id);
        CompanyDataInput companyDataInput = companyDataInputRepository.findOne(id);
        return companyDataInput;
    }
    /**
     *  Delete the  companyDataInput by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyDataInput : {}", id);
        companyDataInputRepository.delete(id);
    }
}
