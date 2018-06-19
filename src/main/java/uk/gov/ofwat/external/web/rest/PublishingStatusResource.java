package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.ofwat.external.service.PublishingStatusService;
import uk.gov.ofwat.external.service.dto.PublishingStatusDTO;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PublishingStatus.
 */
@RestController
@RequestMapping("/api")
public class PublishingStatusResource {

    private final Logger log = LoggerFactory.getLogger(PublishingStatusResource.class);

    private static final String ENTITY_NAME = "publishingStatus";

    private final PublishingStatusService publishingStatusService;

    public PublishingStatusResource(PublishingStatusService publishingStatusService) {
        this.publishingStatusService = publishingStatusService;
    }

    /**
     * POST  /publishing-statuses : Create a new publishingStatus.
     *
     * @param publishingStatusDTO the publishingStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new publishingStatusDTO, or with status 400 (Bad Request) if the publishingStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/publishing-statuses")
    @Timed
    public ResponseEntity<PublishingStatusDTO> createPublishingStatus(@RequestBody PublishingStatusDTO publishingStatusDTO) throws URISyntaxException {
        log.debug("REST request to save PublishingStatus : {}", publishingStatusDTO);
        if (publishingStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new publishingStatus cannot already have an ID")).body(null);
        }
        PublishingStatusDTO result = publishingStatusService.save(publishingStatusDTO);
        return ResponseEntity.created(new URI("/api/publishing-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /publishing-statuses : Updates an existing publishingStatus.
     *
     * @param publishingStatusDTO the publishingStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated publishingStatusDTO,
     * or with status 400 (Bad Request) if the publishingStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the publishingStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/publishing-statuses")
    @Timed
    public ResponseEntity<PublishingStatusDTO> updatePublishingStatus(@RequestBody PublishingStatusDTO publishingStatusDTO) throws URISyntaxException {
        log.debug("REST request to update PublishingStatus : {}", publishingStatusDTO);
        if (publishingStatusDTO.getId() == null) {
            return createPublishingStatus(publishingStatusDTO);
        }
        PublishingStatusDTO result = publishingStatusService.save(publishingStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, publishingStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /publishing-statuses : get all the publishingStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of publishingStatuses in body
     */
    @GetMapping("/publishing-statuses")
    @Timed
    public List<PublishingStatusDTO> getAllPublishingStatuses() {
        log.debug("REST request to get all PublishingStatuses");
        return publishingStatusService.findAll();
    }

    /**
     * GET  /publishing-statuses/:id : get the "id" publishingStatus.
     *
     * @param id the id of the publishingStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the publishingStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/publishing-statuses/{id}")
    @Timed
    public ResponseEntity<PublishingStatusDTO> getPublishingStatus(@PathVariable Long id) {
        log.debug("REST request to get PublishingStatus : {}", id);
        PublishingStatusDTO publishingStatusDTO = publishingStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(publishingStatusDTO));
    }

    /**
     * DELETE  /publishing-statuses/:id : delete the "id" publishingStatus.
     *
     * @param id the id of the publishingStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/publishing-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deletePublishingStatus(@PathVariable Long id) {
        log.debug("REST request to delete PublishingStatus : {}", id);
        publishingStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
