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
import uk.gov.ofwat.external.service.CompanyDataInputService;
import uk.gov.ofwat.external.service.dto.CompanyDataInputDTO;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;
import uk.gov.ofwat.external.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CompanyDataInput.
 */
@RestController
@RequestMapping("/api")
public class CompanyDataInputResource {

    private final Logger log = LoggerFactory.getLogger(CompanyDataInputResource.class);

    private static final String ENTITY_NAME = "companyDataInput";

    private final CompanyDataInputService companyDataInputService;



    private static final String ENTITY_NAME_2 = "dataDownloadDTO";
    private static final String directoryName = "C:\\Files\\";
    Charset charset = StandardCharsets.UTF_8;


    public CompanyDataInputResource(CompanyDataInputService companyDataInputService) {
        this.companyDataInputService = companyDataInputService;
     }

    /**
     * POST  /company-data-inputs : Create a new companyDataInput.
     *
     * @param companyDataInputDTO the companyDataInputDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyDataInputDTO, or with status 400 (Bad Request) if the companyDataInput has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-data-inputs")
    @Timed
    public ResponseEntity<CompanyDataInputDTO> createCompanyDataInput(@Valid @RequestBody CompanyDataInputDTO companyDataInputDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyDataInput : {}", companyDataInputDTO);
        if (companyDataInputDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new companyDataInput cannot already have an ID")).body(null);
        }
        CompanyDataInputDTO result = companyDataInputService.save(companyDataInputDTO);
        return ResponseEntity.created(new URI("/api/company-data-inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-data-inputs : Updates an existing companyDataInput.
     *
     * @param companyDataInputDTO the companyDataInputDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyDataInputDTO,
     * or with status 400 (Bad Request) if the companyDataInputDTO is not valid,
     * or with status 500 (Internal Server Error) if the companyDataInputDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-data-inputs")
    @Timed
    public ResponseEntity<CompanyDataInputDTO> updateCompanyDataInput(@Valid @RequestBody CompanyDataInputDTO companyDataInputDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyDataInput : {}", companyDataInputDTO);
        if (companyDataInputDTO.getId() == null) {
            return createCompanyDataInput(companyDataInputDTO);
        }
        CompanyDataInputDTO result = companyDataInputService.save(companyDataInputDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyDataInputDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-data-inputs : get all the companyDataInputs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of companyDataInputs in body
     */
    @GetMapping("/company-data-inputs")
    @Timed
    public ResponseEntity<List<CompanyDataInputDTO>> getAllCompanyDataInputs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CompanyDataInputs");
        Page<CompanyDataInputDTO> page = companyDataInputService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/company-data-inputs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /company-data-inputs/:id : get the "id" companyDataInput.
     *
     * @param id the id of the companyDataInputDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyDataInputDTO, or with status 404 (Not Found)
     */
    @GetMapping("/company-data-inputs/{id}")
    @Timed
    public ResponseEntity<CompanyDataInputDTO> getCompanyDataInput(@PathVariable Long id) {
        log.debug("REST request to get CompanyDataInput : {}", id);
        CompanyDataInputDTO companyDataInputDTO = companyDataInputService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyDataInputDTO));
    }

    /**
     * DELETE  /company-data-inputs/:id : delete the "id" companyDataInput.
     *
     * @param id the id of the companyDataInputDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-data-inputs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyDataInput(@PathVariable Long id) {
        log.debug("REST request to delete CompanyDataInput : {}", id);
        companyDataInputService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
