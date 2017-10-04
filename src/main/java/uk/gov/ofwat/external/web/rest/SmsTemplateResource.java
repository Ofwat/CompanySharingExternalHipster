package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import uk.gov.ofwat.external.domain.SmsTemplate;

import uk.gov.ofwat.external.repository.SmsTemplateRepository;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;
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
 * REST controller for managing SmsTemplate.
 */
@RestController
@RequestMapping("/api")
public class SmsTemplateResource {

    private final Logger log = LoggerFactory.getLogger(SmsTemplateResource.class);

    private static final String ENTITY_NAME = "smsTemplate";

    private final SmsTemplateRepository smsTemplateRepository;

    public SmsTemplateResource(SmsTemplateRepository smsTemplateRepository) {
        this.smsTemplateRepository = smsTemplateRepository;
    }

    /**
     * POST  /sms-templates : Create a new smsTemplate.
     *
     * @param smsTemplate the smsTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new smsTemplate, or with status 400 (Bad Request) if the smsTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sms-templates")
    @Timed
    public ResponseEntity<SmsTemplate> createSmsTemplate(@Valid @RequestBody SmsTemplate smsTemplate) throws URISyntaxException {
        log.debug("REST request to save SmsTemplate : {}", smsTemplate);
        if (smsTemplate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new smsTemplate cannot already have an ID")).body(null);
        }
        SmsTemplate result = smsTemplateRepository.save(smsTemplate);
        return ResponseEntity.created(new URI("/api/sms-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sms-templates : Updates an existing smsTemplate.
     *
     * @param smsTemplate the smsTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated smsTemplate,
     * or with status 400 (Bad Request) if the smsTemplate is not valid,
     * or with status 500 (Internal Server Error) if the smsTemplate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sms-templates")
    @Timed
    public ResponseEntity<SmsTemplate> updateSmsTemplate(@Valid @RequestBody SmsTemplate smsTemplate) throws URISyntaxException {
        log.debug("REST request to update SmsTemplate : {}", smsTemplate);
        if (smsTemplate.getId() == null) {
            return createSmsTemplate(smsTemplate);
        }
        SmsTemplate result = smsTemplateRepository.save(smsTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, smsTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sms-templates : get all the smsTemplates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of smsTemplates in body
     */
    @GetMapping("/sms-templates")
    @Timed
    public List<SmsTemplate> getAllSmsTemplates() {
        log.debug("REST request to get all SmsTemplates");
        return smsTemplateRepository.findAll();
    }

    /**
     * GET  /sms-templates/:id : get the "id" smsTemplate.
     *
     * @param id the id of the smsTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the smsTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/sms-templates/{id}")
    @Timed
    public ResponseEntity<SmsTemplate> getSmsTemplate(@PathVariable Long id) {
        log.debug("REST request to get SmsTemplate : {}", id);
        SmsTemplate smsTemplate = smsTemplateRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(smsTemplate));
    }

    /**
     * DELETE  /sms-templates/:id : delete the "id" smsTemplate.
     *
     * @param id the id of the smsTemplate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sms-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteSmsTemplate(@PathVariable Long id) {
        log.debug("REST request to delete SmsTemplate : {}", id);
        smsTemplateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
