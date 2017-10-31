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
import uk.gov.ofwat.external.repository.DataCollectionRepository;
import uk.gov.ofwat.external.service.DataCollectionService;
import uk.gov.ofwat.external.domain.PublishingStatus;
import uk.gov.ofwat.external.repository.PublishingStatusRepository;
import uk.gov.ofwat.external.service.PublishingStatusService;
import uk.gov.ofwat.external.service.UserService;
import uk.gov.ofwat.external.service.dto.DataCollectionDTO;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;
import uk.gov.ofwat.external.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DataCollection.
 */
@RestController
@RequestMapping("/api")
public class DataCollectionResource {

    private final Logger log = LoggerFactory.getLogger(DataCollectionResource.class);

    private static final String ENTITY_NAME = "dataCollection";

    private final DataCollectionService dataCollectionService;
    private final DataCollectionRepository dataCollectionRepository;
    private final UserService userService;
    private final PublishingStatusService publishingStatusService;
    private final PublishingStatusRepository publishingStatusRepository;


    public DataCollectionResource(DataCollectionService dataCollectionService, DataCollectionRepository dataCollectionRepository, UserService userService, PublishingStatusService publishingStatusService, PublishingStatusRepository publishingStatusRepository) {
        this.dataCollectionService = dataCollectionService;
        this.dataCollectionRepository = dataCollectionRepository;
        this.userService = userService;
        this.publishingStatusService = publishingStatusService;
        this.publishingStatusRepository = publishingStatusRepository;
    }

    /**
     * POST  /data-collections : Create a new dataCollection.
     *
     * @param dataCollectionDTO the dataCollectionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dataCollectionDTO, or with status 400 (Bad Request) if the dataCollection has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/data-collections")
    @Timed
    public ResponseEntity<DataCollectionDTO> createDataCollection(@Valid @RequestBody DataCollectionDTO dataCollectionDTO) throws URISyntaxException {
        log.debug("REST request to save DataCollection : {}", dataCollectionDTO);

        if (dataCollectionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new dataCollection cannot already have an ID")).body(null);
        }
        else if (Optional.ofNullable(dataCollectionService.findOneByName(dataCollectionDTO.getName())).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "datacollectionexists", "DataCollection is already in use")).body(null);
        }

        Optional<PublishingStatus> optionalPublishingStatus = publishingStatusRepository.findOneByStatus("DRAFT");
        if (!optionalPublishingStatus.isPresent()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "publishingStatusMissing", "Publishing status 'Draft' not found in database."))
                .body(null);
        }
        dataCollectionDTO.setPublishingStatus(optionalPublishingStatus.get());

        DataCollectionDTO newDataCollectionDTO = dataCollectionService.saveNew(dataCollectionDTO);
        return ResponseEntity.created(new URI("/api/data-collections/" + newDataCollectionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, newDataCollectionDTO.getId().toString()))
            .body(newDataCollectionDTO);
    }

    /**
     * PUT  /data-collections : Updates an existing dataCollection.
     *
     * @param dataCollectionDTO the dataCollectionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataCollectionDTO,
     * or with status 400 (Bad Request) if the dataCollectionDTO is not valid,
     * or with status 500 (Internal Server Error) if the dataCollectionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/data-collections")
    @Timed
    public ResponseEntity<DataCollectionDTO> updateDataCollection(@Valid @RequestBody DataCollectionDTO dataCollectionDTO) throws URISyntaxException {
        log.debug("REST request to update DataCollection : {}", dataCollectionDTO);
        if (dataCollectionDTO.getId() == null) {
            return createDataCollection(dataCollectionDTO);
        }
        DataCollectionDTO result = dataCollectionService.save(dataCollectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dataCollectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /data-collections : get all the dataCollections.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dataCollections in body
     */
    @GetMapping("/data-collections")
    @Timed
    public ResponseEntity<List<DataCollectionDTO>> getAllDataCollections(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DataCollections");
        Page<DataCollectionDTO> page = dataCollectionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/data-collections");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /data-collections/:id : get the "id" dataCollection.
     *
     * @param id the id of the dataCollectionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dataCollectionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/data-collections/{id}")
    @Timed
    public ResponseEntity<DataCollectionDTO> getDataCollection(@PathVariable Long id) {
        log.debug("REST request to get DataCollection : {}", id);
        DataCollectionDTO dataCollectionDTO = dataCollectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dataCollectionDTO));
    }

    /**
     */
    @GetMapping(value="/data-collections", params="name")
    @Timed
    public ResponseEntity<DataCollectionDTO> getDataCollectionByName(@RequestParam("name") String name) {
        log.debug("REST request to get DataCollection : {" + name + "}");
        DataCollectionDTO dataCollectionDTO = dataCollectionService.findOneByName(name);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dataCollectionDTO));
    }

    /**
     * DELETE  /data-collections/:id : delete the "id" dataCollection.
     *
     * @param id the id of the dataCollectionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/data-collections/{id}")
    @Timed
    public ResponseEntity<Void> deleteDataCollection(@PathVariable Long id) {
        log.debug("REST request to delete DataCollection : {}", id);
        dataCollectionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     */
//    @DeleteMapping(value="/data-collections", params="name")
//    @Timed
//    public ResponseEntity<Void> deleteDataCollectionByName(@RequestParam("name") String name) {
//        log.debug("REST request to delete DataCollection : {" + name + "}");
//        dataCollectionService.deleteByName(name);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, name)).build();
//    }

}
