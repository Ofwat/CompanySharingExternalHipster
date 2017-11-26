package uk.gov.ofwat.external.service;

import uk.gov.ofwat.external.domain.CompanyDataCollection;
import uk.gov.ofwat.external.repository.CompanyDataCollectionRepository;
import uk.gov.ofwat.external.service.dto.CompanyDataCollectionDTO;
import uk.gov.ofwat.external.service.mapper.CompanyDataCollectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CompanyDataCollection.
 */
@Service
@Transactional
public class CompanyDataCollectionService {

    private final Logger log = LoggerFactory.getLogger(CompanyDataCollectionService.class);

    private final CompanyDataCollectionRepository companyDataCollectionRepository;

    private final CompanyDataCollectionMapper companyDataCollectionMapper;

    public CompanyDataCollectionService(CompanyDataCollectionRepository companyDataCollectionRepository, CompanyDataCollectionMapper companyDataCollectionMapper) {
        this.companyDataCollectionRepository = companyDataCollectionRepository;
        this.companyDataCollectionMapper = companyDataCollectionMapper;
    }

    /**
     * Save a companyDataCollection.
     *
     * @param companyDataCollectionDTO the entity to save
     * @return the persisted entity
     */
    public CompanyDataCollectionDTO save(CompanyDataCollectionDTO companyDataCollectionDTO) {
        log.debug("Request to save CompanyDataCollection : {}", companyDataCollectionDTO);
        CompanyDataCollection companyDataCollection = companyDataCollectionMapper.toEntity(companyDataCollectionDTO);
        companyDataCollection = companyDataCollectionRepository.save(companyDataCollection);
        return companyDataCollectionMapper.toDto(companyDataCollection);
    }

    /**
     *  Get all the companyDataCollections.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CompanyDataCollectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyDataCollections");
        return companyDataCollectionRepository.findAll(pageable)
            .map(companyDataCollectionMapper::toDto);
    }

    /**
     *  Get one companyDataCollection by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CompanyDataCollectionDTO findOne(Long id) {
        log.debug("Request to get CompanyDataCollection : {}", id);
        CompanyDataCollection companyDataCollection = companyDataCollectionRepository.findOne(id);
        return companyDataCollectionMapper.toDto(companyDataCollection);
    }

    /**
     *  Delete the  companyDataCollection by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyDataCollection : {}", id);
        companyDataCollectionRepository.delete(id);
    }
}
