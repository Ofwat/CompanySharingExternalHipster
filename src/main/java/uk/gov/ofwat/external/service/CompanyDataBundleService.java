package uk.gov.ofwat.external.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.ofwat.external.domain.CompanyDataBundle;
import uk.gov.ofwat.external.domain.CompanyDataInput;
import uk.gov.ofwat.external.repository.CompanyDataBundleRepository;
import uk.gov.ofwat.external.repository.CompanyDataInputRepository;
import uk.gov.ofwat.external.service.dto.CompanyDataBundleDTO;
import uk.gov.ofwat.external.service.mapper.CompanyDataBundleMapper;

import java.util.List;


/**
 * Service Implementation for managing CompanyDataBundle.
 */
@Service
@Transactional
public class CompanyDataBundleService {

    private final Logger log = LoggerFactory.getLogger(CompanyDataBundleService.class);

    private final CompanyDataBundleRepository companyDataBundleRepository;

    private final CompanyDataInputRepository companyDataInputRepository;

    private final CompanyDataBundleMapper companyDataBundleMapper;

    public CompanyDataBundleService(CompanyDataBundleRepository companyDataBundleRepository, CompanyDataBundleMapper companyDataBundleMapper,CompanyDataInputRepository companyDataInputRepository) {
        this.companyDataBundleRepository = companyDataBundleRepository;
        this.companyDataBundleMapper = companyDataBundleMapper;
        this.companyDataInputRepository = companyDataInputRepository;
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
     * Save a companyDataBundle.
     *
     * @param companyDataBundle the entity to save
     * @return the persisted entity
     */
    public void save(CompanyDataBundle companyDataBundle) {
        log.debug("Request to save companyDataBundle : {}", companyDataBundle);
        companyDataBundleRepository.save(companyDataBundle);
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

        List<CompanyDataInput> companyDataInputList = null;
        try {
            companyDataInputList = companyDataInputRepository.findByCompanyDataBundle(companyDataBundle.getId());
            CompanyDataInput[] companyDataInputArray = new CompanyDataInput[companyDataInputList.size()];
            companyDataInputArray = companyDataInputList.toArray(companyDataInputArray);
            companyDataBundle.setCompanyDataInputs(companyDataInputArray);
        }catch(Exception e){
            log.debug(e.getMessage());
        }
        return companyDataBundleMapper.toDto(companyDataBundle);
    }

    /**
     *  Get one companyDataBundle by id.
     *
     *  @param bundleId the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public List<CompanyDataBundle> findByCompanyAndBundle( Long bundleId) {
        log.debug("Request to get CompanyDataBundle : {}", bundleId);
        return companyDataBundleRepository.findByCompanyAndBundle(bundleId);
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

    /**
     *  Get one companyDataBundle by id.
     *
     *  @param dataCollectionId, companyId the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Long findByCompanyByDataCollectionAndCompany(Long dataCollectionId, Long companyId) {
        log.debug("Request to get CompanyDataBundle : {}", dataCollectionId.toString());
        return companyDataBundleRepository.findByCompanyByDataCollectionAndCompany(dataCollectionId,companyId);
    }


}
