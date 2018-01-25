package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.external.service.InputTypeService;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;
import uk.gov.ofwat.external.service.dto.InputTypeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InputType.
 */
@RestController
@RequestMapping("/api")
public class InputTypeResource {

    private final Logger log = LoggerFactory.getLogger(InputTypeResource.class);

    private static final String ENTITY_NAME = "inputType";

    private final InputTypeService inputTypeService;

    public InputTypeResource(InputTypeService inputTypeService) {
        this.inputTypeService = inputTypeService;
    }

    /**
     * POST  /input-types : Create a new inputType.
     *
     * @param inputTypeDTO the inputTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inputTypeDTO, or with status 400 (Bad Request) if the inputType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/input-types")
    @Timed
    public ResponseEntity<InputTypeDTO> createInputType(@RequestBody InputTypeDTO inputTypeDTO) throws URISyntaxException {
        log.debug("REST request to save InputType : {}", inputTypeDTO);
        if (inputTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new inputType cannot already have an ID")).body(null);
        }
        InputTypeDTO result = inputTypeService.save(inputTypeDTO);
        return ResponseEntity.created(new URI("/api/input-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /input-types : Updates an existing inputType.
     *
     * @param inputTypeDTO the inputTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inputTypeDTO,
     * or with status 400 (Bad Request) if the inputTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the inputTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/input-types")
    @Timed
    public ResponseEntity<InputTypeDTO> updateInputType(@RequestBody InputTypeDTO inputTypeDTO) throws URISyntaxException {
        log.debug("REST request to update InputType : {}", inputTypeDTO);
        if (inputTypeDTO.getId() == null) {
            return createInputType(inputTypeDTO);
        }
        InputTypeDTO result = inputTypeService.save(inputTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inputTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /input-types : get all the inputTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inputTypes in body
     */
    @GetMapping("/input-types")
    @Timed
    public List<InputTypeDTO> getAllInputTypes() {
        log.debug("REST request to get all InputTypes");
        return inputTypeService.findAll();
    }

    /**
     * GET  /input-types/:id : get the "id" inputType.
     *
     * @param id the id of the inputTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inputTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/input-types/{id}")
    @Timed
    public ResponseEntity<InputTypeDTO> getInputType(@PathVariable Long id) {
        log.debug("REST request to get InputType : {}", id);
        InputTypeDTO inputTypeDTO = inputTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inputTypeDTO));
    }

    /**
     * DELETE  /input-types/:id : delete the "id" inputType.
     *
     * @param id the id of the inputTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/input-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteInputType(@PathVariable Long id) {
        log.debug("REST request to delete InputType : {}", id);
        inputTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
