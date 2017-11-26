package uk.gov.ofwat.external.service;

import uk.gov.ofwat.external.domain.CompanyDataBundle;
import uk.gov.ofwat.external.repository.CompanyDataBundleRepository;
import uk.gov.ofwat.external.service.dto.CompanyDataBundleDTO;
import uk.gov.ofwat.external.service.mapper.CompanyDataBundleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CompanyDataBundle.
 */
@Service
@Transactional
public class CompanyDataBundleService {

    private final Logger log = LoggerFactory.getLogger(CompanyDataBundleService.class);

    private final CompanyDataBundleRepository companyDataBundleRepository;

    private final CompanyDataBundleMapper companyDataBundleMapper;

    public CompanyDataBundleService(CompanyDataBundleRepository companyDataBundleRepository, CompanyDataBundleMapper companyDataBundleMapper) {
        this.companyDataBundleRepository = companyDataBundleRepository;
        this.companyDataBundleMapper = companyDataBundleMapper;
    }

    /**
     * Save a companyDataBundle.
     *
     * @param companyDataBundleDTO the entity to save
     * @return the persisted entity
     */
    public CompanyDataBundleDTO save(CompanyDataBundleDTO companyDataBundleDTO) {
        log.debug("Request to save CompanyDataBundle : {}", companyDataBundleDTO);
        CompanyDataBundle companyDataBundle = companyDataBundleMapper.toEntity(companyDataBundleDTO);
        companyDataBundle = companyDataBundleRepository.save(companyDataBundle);
        return companyDataBundleMapper.toDto(companyDataBundle);
    }

    /**
     *  Get all the companyDataBundles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CompanyDataBundleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyDataBundles");
        return companyDataBundleRepository.findAll(pageable)
            .map(companyDataBundleMapper::toDto);
    }

    /**
     *  Get one companyDataBundle by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CompanyDataBundleDTO findOne(Long id) {
        log.debug("Request to get CompanyDataBundle : {}", id);
        CompanyDataBundle companyDataBundle = companyDataBundleRepository.findOne(id);
        return companyDataBundleMapper.toDto(companyDataBundle);
    }

    /**
     *  Delete the  companyDataBundle by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CompanyDataBundle : {}", id);
        companyDataBundleRepository.delete(id);
    }
}
