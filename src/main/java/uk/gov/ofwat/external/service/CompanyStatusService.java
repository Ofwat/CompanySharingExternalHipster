package uk.gov.ofwat.external.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.domain.CompanyStatus;
import uk.gov.ofwat.external.repository.CompanyStatusRepository;
import uk.gov.ofwat.external.service.dto.CompanyStatusDTO;
import uk.gov.ofwat.external.service.mapper.CompanyStatusMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CompanyStatus.
 */
@Service
@Transactional
public class CompanyStatusService {

    private final Logger log = LoggerFactory.getLogger(CompanyStatusService.class);

    private final CompanyStatusRepository companyStatusRepository;

    private final CompanyStatusMapper companyStatusMapper;

    public CompanyStatusService(CompanyStatusRepository companyStatusRepository, CompanyStatusMapper companyStatusMapper) {
        this.companyStatusRepository = companyStatusRepository;
        this.companyStatusMapper = companyStatusMapper;
    }

    /**
     * Save a companyStatus.
     *
     * @param companyStatusDTO the entity to save
     * @return the persisted entity
     */
    public CompanyStatusDTO save(CompanyStatusDTO companyStatusDTO) {
        log.debug("Request to save CompanyStatus : {}", companyStatusDTO);
        CompanyStatus companyStatus = companyStatusMapper.toEntity(companyStatusDTO);
        companyStatus = companyStatusRepository.save(companyStatus);
        return companyStatusMapper.toDto(companyStatus);
    }

    /**
     *  Get all the companyStatuses.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CompanyStatusDTO> findAll() {
        log.debug("Request to get all CompanyStatuses");
        return companyStatusRepository.findAll().stream()
            .map(companyStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one companyStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CompanyStatusDTO findOne(Long id) {
        log.debug("Request to get CompanyStatus : {}", id);
        CompanyStatus companyStatus = companyStatusRepository.findOne(id);
        return companyStatusMapper.toDto(companyStatus);
    }


    /**
     *  Get one companyStatus by id.
     *
     *  @param status the status of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CompanyStatusDTO findOne(String status) {
        log.debug("Request to get CompanyStatus : {}", status);
        Optional <CompanyStatus> companyStatus = companyStatusRepository.findOneByStatus(status);
        return companyStatusMapper.toDto(companyStatus.get());
    }

    /**
     *  Delete the  companyStatus by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyStatus : {}", id);
        companyStatusRepository.delete(id);
    }
}
