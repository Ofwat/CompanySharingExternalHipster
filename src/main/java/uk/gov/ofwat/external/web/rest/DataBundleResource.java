package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.ofwat.external.domain.*;
import uk.gov.ofwat.external.repository.*;
import uk.gov.ofwat.external.service.DataBundleService;
import uk.gov.ofwat.external.service.PublishingService;
import uk.gov.ofwat.external.service.PublishingStateTransformationService;
import uk.gov.ofwat.external.web.rest.errors.DcsException;
import uk.gov.ofwat.external.web.rest.errors.DcsServerMessage;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;
import uk.gov.ofwat.external.web.rest.util.PaginationUtil;
import uk.gov.ofwat.external.service.dto.DataBundleDTO;
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
 * REST controller for managing DataBundle.
 */
@RestController
@RequestMapping("/api")
public class DataBundleResource {

    private final Logger log = LoggerFactory.getLogger(DataBundleResource.class);
    private static final String ENTITY_NAME = "dataBundle";
    private final DataBundleService dataBundleService;
    private final PublishingStatusRepository publishingStatusRepository;
    private final PublishingStateTransformationService publishingStateTransformationService;

    public DataBundleResource(PublishingStateTransformationService publishingStateTransformationService,DataBundleService dataBundleService, PublishingStatusRepository publishingStatusRepository
    ) {
        this.publishingStateTransformationService=publishingStateTransformationService;
        this.dataBundleService = dataBundleService;
        this.publishingStatusRepository = publishingStatusRepository;
    }

    /**
     * POST  /data-bundles : Create a new dataBundle.
     *
     * @param dataBundleDTO the dataBundleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dataBundleDTO, or with status 400 (Bad Request) if the dataBundle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/data-bundles")
    @Timed
    public ResponseEntity<DataBundleDTO> createDataBundle(@Valid @RequestBody DataBundleDTO dataBundleDTO) throws URISyntaxException {
        log.debug("REST request to save DataBundle : {}", dataBundleDTO);
        if (dataBundleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new dataBundle cannot already have an ID")).body(null);
        }

        Long maxOrderIndex = dataBundleService.getMaxOrderIndex(dataBundleDTO.getDataCollectionId());
        log.error("maxOrderIndex = " + maxOrderIndex);
        Optional<PublishingStatus> optionalPublishingStatus = publishingStatusRepository.findOneByStatus("DRAFT");
        if (!optionalPublishingStatus.isPresent()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "publishingStatusMissing", "Publishing status 'Draft' not found in database."))
                .body(null);
        }
        dataBundleDTO.setStatusId(optionalPublishingStatus.get().getId());
        dataBundleDTO.setStatusStatus(optionalPublishingStatus.get().getStatus());
        dataBundleDTO.setOrderIndex(new Long(maxOrderIndex + 1));

        DataBundleDTO result = dataBundleService.save(dataBundleDTO);
        return ResponseEntity.created(new URI("/api/data-bundles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /data-bundles : Updates an existing dataBundle.
     *
     * @param dataBundleDTO the dataBundleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataBundleDTO,
     * or with status 400 (Bad Request) if the dataBundleDTO is not valid,
     * or with status 500 (Internal Server Error) if the dataBundleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/data-bundles")
    @Timed
    public ResponseEntity<DataBundleDTO> updateDataBundle(@Valid @RequestBody DataBundleDTO dataBundleDTO) throws URISyntaxException,DcsException {
        log.debug("REST request to update DataBundle : {}", dataBundleDTO);
        if (dataBundleDTO.getId() == null) {
            return createDataBundle(dataBundleDTO);
        }
        //When status has been changed to publish
        if (dataBundleDTO.getStatusId().equals(new Long(4))) {
            publishingStateTransformationService.publishDataBundleStatus(dataBundleDTO);
        }
        DataBundleDTO result = dataBundleService.save(dataBundleDTO);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dataBundleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /data-bundles : get all the dataBundles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dataBundles in body
     */
    @GetMapping("/data-bundles")
    @Timed
    public ResponseEntity<List<DataBundleDTO>> getAllDataBundles(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DataBundles");
        Page<DataBundleDTO> page = dataBundleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/data-bundles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /data-bundles/:id : get the "id" dataBundle.
     *
     * @param id the id of the dataBundleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dataBundleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/data-bundles/{id}")
    @Timed
    public ResponseEntity<DataBundleDTO> getDataBundle(@PathVariable Long id) {
        log.debug("REST request to get DataBundle : {}", id);
        DataBundleDTO dataBundleDTO = dataBundleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dataBundleDTO));
    }

    /**
     * DELETE  /data-bundles/:id : delete the "id" dataBundle.
     *
     * @param id the id of the dataBundleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/data-bundles/{id}")
    @Timed
    public ResponseEntity<Void> deleteDataBundle(@PathVariable Long id) {
        log.debug("REST request to delete DataBundle : {}", id);
        dataBundleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(final Exception exception) throws JsonProcessingException {
        log.debug("handling Exception", exception);
        return buildResponseEntity(new DcsServerMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), new Throwable(new Exception("exception"))));
    }

    private ResponseEntity<String> buildResponseEntity(DcsServerMessage dcsServerMessage) throws JsonProcessingException {
        ObjectMapper obm = new ObjectMapper();
        String x = obm.writeValueAsString(dcsServerMessage);
        return new ResponseEntity<>(x, dcsServerMessage.getStatus());
    }
}
