package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.ofwat.external.service.CompanyStatusService;
import uk.gov.ofwat.external.service.dto.CompanyStatusDTO;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CompanyStatus.
 */
@RestController
@RequestMapping("/api")
public class CompanyStatusResource {

    private final Logger log = LoggerFactory.getLogger(CompanyStatusResource.class);

    private static final String ENTITY_NAME = "companyStatus";

    private final CompanyStatusService companyStatusService;

    public CompanyStatusResource(CompanyStatusService companyStatusService) {
        this.companyStatusService = companyStatusService;
    }

    /**
     * POST  /company-statuses : Create a new companyStatus.
     *
     * @param companyStatusDTO the companyStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyStatusDTO, or with status 400 (Bad Request) if the companyStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-statuses")
    @Timed
    public ResponseEntity<CompanyStatusDTO> createCompanyStatus(@RequestBody CompanyStatusDTO companyStatusDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyStatus : {}", companyStatusDTO);
        if (companyStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new companyStatus cannot already have an ID")).body(null);
        }
        CompanyStatusDTO result = companyStatusService.save(companyStatusDTO);
        return ResponseEntity.created(new URI("/api/company-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-statuses : Updates an existing companyStatus.
     *
     * @param companyStatusDTO the companyStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyStatusDTO,
     * or with status 400 (Bad Request) if the companyStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the companyStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-statuses")
    @Timed
    public ResponseEntity<CompanyStatusDTO> updateCompanyStatus(@RequestBody CompanyStatusDTO companyStatusDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyStatus : {}", companyStatusDTO);
        if (companyStatusDTO.getId() == null) {
            return createCompanyStatus(companyStatusDTO);
        }
        CompanyStatusDTO result = companyStatusService.save(companyStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-statuses : get all the companyStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of companyStatuses in body
     */
    @GetMapping("/company-statuses")
    @Timed
    public List<CompanyStatusDTO> getAllCompanyStatuses() {
        log.debug("REST request to get all CompanyStatuses");
        return companyStatusService.findAll();
    }

    /**
     * GET  /company-statuses/:id : get the "id" companyStatus.
     *
     * @param id the id of the companyStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/company-statuses/{id}")
    @Timed
    public ResponseEntity<CompanyStatusDTO> getCompanyStatus(@PathVariable Long id) {
        log.debug("REST request to get CompanyStatus : {}", id);
        CompanyStatusDTO companyStatusDTO = companyStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyStatusDTO));
    }

    /**
     * DELETE  /company-statuses/:id : delete the "id" companyStatus.
     *
     * @param id the id of the companyStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyStatus(@PathVariable Long id) {
        log.debug("REST request to delete CompanyStatus : {}", id);
        companyStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
