package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.ofwat.external.service.CompanyDataBundleService;
import uk.gov.ofwat.external.service.dto.CompanyDataBundleDTO;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;
import uk.gov.ofwat.external.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CompanyDataBundle.
 */
@RestController
@RequestMapping("/api")
public class CompanyDataBundleResource {

    private final Logger log = LoggerFactory.getLogger(CompanyDataBundleResource.class);

    private static final String ENTITY_NAME = "companyDataBundle";

    private final CompanyDataBundleService companyDataBundleService;

    public CompanyDataBundleResource(CompanyDataBundleService companyDataBundleService) {
        this.companyDataBundleService = companyDataBundleService;
    }

    /**
     * POST  /company-data-bundles : Create a new companyDataBundle.
     *
     * @param companyDataBundleDTO the companyDataBundleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyDataBundleDTO, or with status 400 (Bad Request) if the companyDataBundle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-data-bundles")
    @Timed
    public ResponseEntity<CompanyDataBundleDTO> createCompanyDataBundle(@Valid @RequestBody CompanyDataBundleDTO companyDataBundleDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyDataBundle : {}", companyDataBundleDTO);
        if (companyDataBundleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new companyDataBundle cannot already have an ID")).body(null);
        }
        CompanyDataBundleDTO result = companyDataBundleService.save(companyDataBundleDTO);
        return ResponseEntity.created(new URI("/api/company-data-bundles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-data-bundles : Updates an existing companyDataBundle.
     *
     * @param companyDataBundleDTO the companyDataBundleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyDataBundleDTO,
     * or with status 400 (Bad Request) if the companyDataBundleDTO is not valid,
     * or with status 500 (Internal Server Error) if the companyDataBundleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-data-bundles")
    @Timed
    public ResponseEntity<CompanyDataBundleDTO> updateCompanyDataBundle(@Valid @RequestBody CompanyDataBundleDTO companyDataBundleDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyDataBundle : {}", companyDataBundleDTO);
        if (companyDataBundleDTO.getId() == null) {
            return createCompanyDataBundle(companyDataBundleDTO);
        }
        CompanyDataBundleDTO result = companyDataBundleService.save(companyDataBundleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyDataBundleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-data-bundles : get all the companyDataBundles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of companyDataBundles in body
     */
    @GetMapping("/company-data-bundles")
    @Timed
    public ResponseEntity<List<CompanyDataBundleDTO>> getAllCompanyDataBundles(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CompanyDataBundles");
        Page<CompanyDataBundleDTO> page = companyDataBundleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/company-data-bundles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /company-data-bundles/:id : get the "id" companyDataBundle.
     *
     * @param id the id of the companyDataBundleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyDataBundleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/company-data-bundles/{id}")
    @Timed
    public ResponseEntity<CompanyDataBundleDTO> getCompanyDataBundle(@PathVariable Long id) {
        log.debug("REST request to get CompanyDataBundle : {}", id);
        CompanyDataBundleDTO companyDataBundleDTO = companyDataBundleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyDataBundleDTO));
    }

    /**
     * DELETE  /company-data-bundles/:id : delete the "id" companyDataBundle.
     *
     * @param id the id of the companyDataBundleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-data-bundles/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyDataBundle(@PathVariable Long id) {
        log.debug("REST request to delete CompanyDataBundle : {}", id);
        companyDataBundleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
