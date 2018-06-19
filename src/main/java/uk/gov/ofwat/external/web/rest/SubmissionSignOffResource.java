package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.ofwat.external.service.SubmissionSignOffService;
import uk.gov.ofwat.external.service.dto.SubmissionSignOffDTO;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SubmissionSignOff.
 */
@RestController
@RequestMapping("/api")
public class SubmissionSignOffResource {

    private final Logger log = LoggerFactory.getLogger(SubmissionSignOffResource.class);

    private static final String ENTITY_NAME = "submissionSignOff";

    private final SubmissionSignOffService submissionSignOffService;

    public SubmissionSignOffResource(SubmissionSignOffService submissionSignOffService) {
        this.submissionSignOffService = submissionSignOffService;
    }

    /**
     * POST  /submission-sign-offs : Create a new submissionSignOff.
     *
     * @param submissionSignOffDTO the submissionSignOffDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new submissionSignOffDTO, or with status 400 (Bad Request) if the submissionSignOff has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/submission-sign-offs")
    @Timed
    public ResponseEntity<SubmissionSignOffDTO> createSubmissionSignOff(@Valid @RequestBody SubmissionSignOffDTO submissionSignOffDTO) throws URISyntaxException {
        log.debug("REST request to save SubmissionSignOff : {}", submissionSignOffDTO);
        if (submissionSignOffDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new submissionSignOff cannot already have an ID")).body(null);
        }
        SubmissionSignOffDTO result = submissionSignOffService.save(submissionSignOffDTO);
        return ResponseEntity.created(new URI("/api/submission-sign-offs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /submission-sign-offs : Updates an existing submissionSignOff.
     *
     * @param submissionSignOffDTO the submissionSignOffDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated submissionSignOffDTO,
     * or with status 400 (Bad Request) if the submissionSignOffDTO is not valid,
     * or with status 500 (Internal Server Error) if the submissionSignOffDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/submission-sign-offs")
    @Timed
    public ResponseEntity<SubmissionSignOffDTO> updateSubmissionSignOff(@Valid @RequestBody SubmissionSignOffDTO submissionSignOffDTO) throws URISyntaxException {
        log.debug("REST request to update SubmissionSignOff : {}", submissionSignOffDTO);
        if (submissionSignOffDTO.getId() == null) {
            return createSubmissionSignOff(submissionSignOffDTO);
        }
        SubmissionSignOffDTO result = submissionSignOffService.save(submissionSignOffDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, submissionSignOffDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /submission-sign-offs : get all the submissionSignOffs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of submissionSignOffs in body
     */
    @GetMapping("/submission-sign-offs")
    @Timed
    public List<SubmissionSignOffDTO> getAllSubmissionSignOffs() {
        log.debug("REST request to get all SubmissionSignOffs");
        return submissionSignOffService.findAll();
    }

    /**
     * GET  /submission-sign-offs/:id : get the "id" submissionSignOff.
     *
     * @param id the id of the submissionSignOffDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the submissionSignOffDTO, or with status 404 (Not Found)
     */
    @GetMapping("/submission-sign-offs/{id}")
    @Timed
    public ResponseEntity<SubmissionSignOffDTO> getSubmissionSignOff(@PathVariable Long id) {
        log.debug("REST request to get SubmissionSignOff : {}", id);
        SubmissionSignOffDTO submissionSignOffDTO = submissionSignOffService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(submissionSignOffDTO));
    }

    /**
     * DELETE  /submission-sign-offs/:id : delete the "id" submissionSignOff.
     *
     * @param id the id of the submissionSignOffDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/submission-sign-offs/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubmissionSignOff(@PathVariable Long id) {
        log.debug("REST request to delete SubmissionSignOff : {}", id);
        submissionSignOffService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
