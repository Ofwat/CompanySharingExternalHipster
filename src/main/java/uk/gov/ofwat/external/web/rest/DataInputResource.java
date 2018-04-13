package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import uk.gov.ofwat.external.domain.PublishingStatus;
import uk.gov.ofwat.external.repository.PublishingStatusRepository;
import uk.gov.ofwat.external.service.DataInputService;
import uk.gov.ofwat.external.service.ExcelReaderService;
import uk.gov.ofwat.external.service.PublishingStateTransformationService;
import uk.gov.ofwat.external.service.dto.DataInputDTO;
import uk.gov.ofwat.external.web.rest.errors.DcsException;
import uk.gov.ofwat.external.web.rest.errors.DcsServerMessage;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;
import uk.gov.ofwat.external.web.rest.util.PaginationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DataInput.
 */
@RestController
@RequestMapping("/api")
public class DataInputResource {

    private final Logger log = LoggerFactory.getLogger(DataInputResource.class);
    private static final String ENTITY_NAME = "dataInput";
    private final DataInputService dataInputService;
    private final PublishingStatusRepository publishingStatusRepository;
    private final ExcelReaderService excelReaderService;
    private final PublishingStateTransformationService publishingStateTransformationService;


    public DataInputResource(DataInputService dataInputService, PublishingStatusRepository publishingStatusRepository,
                             PublishingStateTransformationService publishingStateTransformationService, ExcelReaderService excelReaderService) {
        this.dataInputService = dataInputService;
        this.publishingStatusRepository = publishingStatusRepository;
        this.publishingStateTransformationService = publishingStateTransformationService;
        this.excelReaderService = excelReaderService;

    }

    /**
     * POST  /data-inputs : Create a new dataInput.
     *
     * @param dataInputDTO the dataInputDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dataInputDTO, or with status 400 (Bad Request) if the dataInput has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/data-inputs")
    @Timed
    public ResponseEntity<DataInputDTO> createDataInput(@Valid @RequestBody DataInputDTO dataInputDTO) throws URISyntaxException {
        log.debug("REST request to save DataInput : {}", dataInputDTO);
        if (dataInputDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new dataInput cannot already have an ID")).body(null);
        }

        Long maxOrderIndex = dataInputService.getMaxOrderIndex(dataInputDTO.getDataBundleId());
        log.error("maxOrderIndex = " + maxOrderIndex);
        Optional<PublishingStatus> optionalPublishingStatus = publishingStatusRepository.findOneByStatus("DRAFT");
        if (!optionalPublishingStatus.isPresent()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "publishingStatusMissing", "Publishing status 'Draft' not found in database."))
                .body(null);
        }
        dataInputDTO.setStatusId(optionalPublishingStatus.get().getId());
        dataInputDTO.setStatusStatus(optionalPublishingStatus.get().getStatus());
        dataInputDTO.setOrderIndex(new Long(maxOrderIndex + 1));

        DataInputDTO result = dataInputService.save(dataInputDTO);

        return ResponseEntity.created(new URI("/api/data-inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * PUT  /data-inputs : Updates an existing dataInput.
     *
     * @param dataInputDTO the dataInputDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataInputDTO,
     * or with status 400 (Bad Request) if the dataInputDTO is not valid,
     * or with status 500 (Internal Server Error) if the dataInputDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/data-inputs")
    @Timed
    public ResponseEntity<DataInputDTO> updateDataInput(@Valid @RequestBody DataInputDTO dataInputDTO) throws URISyntaxException, DcsException {
        log.debug("REST request to update DataInput : {}", dataInputDTO);
        if (!Double.valueOf(dataInputDTO.getReportId().toString()).isNaN()) {
            new DcsServerMessage(HttpStatus.INTERNAL_SERVER_ERROR, "ReportId is numeric", new Throwable(new Exception("exception")));
        }

        if (dataInputDTO.getId() == null) {
            return createDataInput(dataInputDTO);
        }
        //When status has been changed to publish
        if (dataInputDTO.getStatusId().equals(new Long(4))) {
            publishingStateTransformationService.publishDataInputStatus(dataInputDTO);
        }

        DataInputDTO result = dataInputService.save(dataInputDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dataInputDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /data-inputs : get all the dataInputs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dataInputs in body
     */
    @GetMapping("/data-inputs")
    @Timed
    public ResponseEntity<List<DataInputDTO>> getAllDataInputs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DataInputs");
        Page<DataInputDTO> page = dataInputService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/data-inputs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /data-inputs/:id : get the "id" dataInput.
     *
     * @param id the id of the dataInputDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dataInputDTO, or with status 404 (Not Found)
     */
    @GetMapping("/data-inputs/{id}")
    @Timed
    public ResponseEntity<DataInputDTO> getDataInput(@PathVariable Long id) {
        log.debug("REST request to get DataInput : {}", id);
        DataInputDTO dataInputDTO = dataInputService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dataInputDTO));
    }

    /**
     * DELETE  /data-inputs/:id : delete the "id" dataInput.
     *
     * @param id the id of the dataInputDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/data-inputs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDataInput(@PathVariable Long id) {
        log.debug("REST request to delete DataInput : {}", id);
        dataInputService.delete(id);
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
