package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.external.service.CompanyDataCollectionService;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;
import uk.gov.ofwat.external.web.rest.util.PaginationUtil;
import uk.gov.ofwat.external.service.dto.CompanyDataCollectionDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CompanyDataCollection.
 */
@RestController
@RequestMapping("/api")
public class CompanyDataCollectionResource {

    private final Logger log = LoggerFactory.getLogger(CompanyDataCollectionResource.class);

    private static final String ENTITY_NAME = "companyDataCollection";

    private final CompanyDataCollectionService companyDataCollectionService;

    public CompanyDataCollectionResource(CompanyDataCollectionService companyDataCollectionService) {
        this.companyDataCollectionService = companyDataCollectionService;
    }

    /**
     * POST  /company-data-collections : Create a new companyDataCollection.
     *
     * @param companyDataCollectionDTO the companyDataCollectionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyDataCollectionDTO, or with status 400 (Bad Request) if the companyDataCollection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-data-collections")
    @Timed
    public ResponseEntity<CompanyDataCollectionDTO> createCompanyDataCollection(@Valid @RequestBody CompanyDataCollectionDTO companyDataCollectionDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyDataCollection : {}", companyDataCollectionDTO);
        if (companyDataCollectionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new companyDataCollection cannot already have an ID")).body(null);
        }
        CompanyDataCollectionDTO result = companyDataCollectionService.save(companyDataCollectionDTO);
        return ResponseEntity.created(new URI("/api/company-data-collections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-data-collections : Updates an existing companyDataCollection.
     *
     * @param companyDataCollectionDTO the companyDataCollectionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyDataCollectionDTO,
     * or with status 400 (Bad Request) if the companyDataCollectionDTO is not valid,
     * or with status 500 (Internal Server Error) if the companyDataCollectionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-data-collections")
    @Timed
    public ResponseEntity<CompanyDataCollectionDTO> updateCompanyDataCollection(@Valid @RequestBody CompanyDataCollectionDTO companyDataCollectionDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyDataCollection : {}", companyDataCollectionDTO);
        if (companyDataCollectionDTO.getId() == null) {
            return createCompanyDataCollection(companyDataCollectionDTO);
        }
        CompanyDataCollectionDTO result = companyDataCollectionService.save(companyDataCollectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyDataCollectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-data-collections : get all the companyDataCollections.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of companyDataCollections in body
     */
    @GetMapping("/company-data-collections")
    @Timed
    public ResponseEntity<List<CompanyDataCollectionDTO>> getAllCompanyDataCollections(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CompanyDataCollections");
        Page<CompanyDataCollectionDTO> page = companyDataCollectionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/company-data-collections");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /company-data-collections/:id : get the "id" companyDataCollection.
     *
     * @param id the id of the companyDataCollectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyDataCollectionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/company-data-collections/{id}")
    @Timed
    public ResponseEntity<CompanyDataCollectionDTO> getCompanyDataCollection(@PathVariable Long id) {
        log.debug("REST request to get CompanyDataCollection : {}", id);
        CompanyDataCollectionDTO companyDataCollectionDTO = companyDataCollectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyDataCollectionDTO));
    }

    /**
     * DELETE  /company-data-collections/:id : delete the "id" companyDataCollection.
     *
     * @param id the id of the companyDataCollectionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-data-collections/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyDataCollection(@PathVariable Long id) {
        log.debug("REST request to delete CompanyDataCollection : {}", id);
        companyDataCollectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
