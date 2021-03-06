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
import uk.gov.ofwat.external.service.DataFileService;
import uk.gov.ofwat.external.service.dto.DataFileDTO;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;
import uk.gov.ofwat.external.web.rest.util.PaginationUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DataFile.
 */
@RestController
@RequestMapping("/api")
public class DataFileResource {

    private final Logger log = LoggerFactory.getLogger(DataFileResource.class);

    private static final String ENTITY_NAME = "dataFile";

    private final DataFileService dataFileService;

    public DataFileResource(DataFileService dataFileService) {
        this.dataFileService = dataFileService;
    }

    /**
     * POST  /data-files : Create a new dataFile.
     *
     * @param dataFileDTO the dataFileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dataFileDTO, or with status 400 (Bad Request) if the dataFile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/data-files")
    @Timed
    public ResponseEntity<DataFileDTO> createDataFile(@RequestBody DataFileDTO dataFileDTO) throws URISyntaxException {
        log.debug("REST request to save DataFile : {}", dataFileDTO);
        if (dataFileDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new dataFile cannot already have an ID")).body(null);
        }
        DataFileDTO result = dataFileService.save(dataFileDTO);
        return ResponseEntity.created(new URI("/api/data-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /data-files : Updates an existing dataFile.
     *
     * @param dataFileDTO the dataFileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataFileDTO,
     * or with status 400 (Bad Request) if the dataFileDTO is not valid,
     * or with status 500 (Internal Server Error) if the dataFileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/data-files")
    @Timed
    public ResponseEntity<DataFileDTO> updateDataFile(@RequestBody DataFileDTO dataFileDTO) throws URISyntaxException {
        log.debug("REST request to update DataFile : {}", dataFileDTO);
        if (dataFileDTO.getId() == null) {
            return createDataFile(dataFileDTO);
        }
        DataFileDTO result = dataFileService.save(dataFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dataFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /data-files : get all the dataFiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dataFiles in body
     */
    @GetMapping("/data-files")
    @Timed
    public ResponseEntity<List<DataFileDTO>> getAllDataFiles(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of DataFiles");
        Page<DataFileDTO> page = dataFileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/data-files");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /data-files/:id : get the "id" dataFile.
     *
     * @param id the id of the dataFileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dataFileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/data-files/{id}")
    @Timed
    public ResponseEntity<DataFileDTO> getDataFile(@PathVariable Long id) {
        log.debug("REST request to get DataFile : {}", id);
        DataFileDTO dataFileDTO = dataFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dataFileDTO));
    }

    /**
     * DELETE  /data-files/:id : delete the "id" dataFile.
     *
     * @param id the id of the dataFileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/data-files/{id}")
    @Timed
    public ResponseEntity<Void> deleteDataFile(@PathVariable Long id) {
        log.debug("REST request to delete DataFile : {}", id);
        dataFileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
