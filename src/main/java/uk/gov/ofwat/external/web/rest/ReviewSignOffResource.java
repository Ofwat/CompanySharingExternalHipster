package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.external.service.ReviewSignOffService;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;
import uk.gov.ofwat.external.service.dto.ReviewSignOffDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ReviewSignOff.
 */
@RestController
@RequestMapping("/api")
public class ReviewSignOffResource {

    private final Logger log = LoggerFactory.getLogger(ReviewSignOffResource.class);

    private static final String ENTITY_NAME = "reviewSignOff";

    private final ReviewSignOffService reviewSignOffService;

    public ReviewSignOffResource(ReviewSignOffService reviewSignOffService) {
        this.reviewSignOffService = reviewSignOffService;
    }

    /**
     * POST  /review-sign-offs : Create a new reviewSignOff.
     *
     * @param reviewSignOffDTO the reviewSignOffDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reviewSignOffDTO, or with status 400 (Bad Request) if the reviewSignOff has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/review-sign-offs")
    @Timed
    public ResponseEntity<ReviewSignOffDTO> createReviewSignOff(@Valid @RequestBody ReviewSignOffDTO reviewSignOffDTO) throws URISyntaxException {
        log.debug("REST request to save ReviewSignOff : {}", reviewSignOffDTO);
        if (reviewSignOffDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new reviewSignOff cannot already have an ID")).body(null);
        }
        ReviewSignOffDTO result = reviewSignOffService.save(reviewSignOffDTO);
        return ResponseEntity.created(new URI("/api/review-sign-offs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /review-sign-offs : Updates an existing reviewSignOff.
     *
     * @param reviewSignOffDTO the reviewSignOffDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reviewSignOffDTO,
     * or with status 400 (Bad Request) if the reviewSignOffDTO is not valid,
     * or with status 500 (Internal Server Error) if the reviewSignOffDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/review-sign-offs")
    @Timed
    public ResponseEntity<ReviewSignOffDTO> updateReviewSignOff(@Valid @RequestBody ReviewSignOffDTO reviewSignOffDTO) throws URISyntaxException {
        log.debug("REST request to update ReviewSignOff : {}", reviewSignOffDTO);
        if (reviewSignOffDTO.getId() == null) {
            return createReviewSignOff(reviewSignOffDTO);
        }
        ReviewSignOffDTO result = reviewSignOffService.save(reviewSignOffDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reviewSignOffDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /review-sign-offs : get all the reviewSignOffs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of reviewSignOffs in body
     */
    @GetMapping("/review-sign-offs")
    @Timed
    public List<ReviewSignOffDTO> getAllReviewSignOffs() {
        log.debug("REST request to get all ReviewSignOffs");
        return reviewSignOffService.findAll();
    }

    /**
     * GET  /review-sign-offs/:id : get the "id" reviewSignOff.
     *
     * @param id the id of the reviewSignOffDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reviewSignOffDTO, or with status 404 (Not Found)
     */
    @GetMapping("/review-sign-offs/{id}")
    @Timed
    public ResponseEntity<ReviewSignOffDTO> getReviewSignOff(@PathVariable Long id) {
        log.debug("REST request to get ReviewSignOff : {}", id);
        ReviewSignOffDTO reviewSignOffDTO = reviewSignOffService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reviewSignOffDTO));
    }

    /**
     * DELETE  /review-sign-offs/:id : delete the "id" reviewSignOff.
     *
     * @param id the id of the reviewSignOffDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/review-sign-offs/{id}")
    @Timed
    public ResponseEntity<Void> deleteReviewSignOff(@PathVariable Long id) {
        log.debug("REST request to delete ReviewSignOff : {}", id);
        reviewSignOffService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
