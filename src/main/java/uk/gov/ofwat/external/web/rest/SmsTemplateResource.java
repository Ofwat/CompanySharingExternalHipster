package uk.gov.ofwat.external.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.ofwat.external.domain.message.NotifyMessageTemplate;
import uk.gov.ofwat.external.repository.NotifyMessageTemplateRepository;
import uk.gov.ofwat.external.web.rest.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NotifyMessageTemplate.
 */
@RestController
@RequestMapping("/api")
public class SmsTemplateResource {

    private final Logger log = LoggerFactory.getLogger(SmsTemplateResource.class);

    private static final String ENTITY_NAME = "smsTemplate";

    private final NotifyMessageTemplateRepository notifyMessageTemplateRepository;

    public SmsTemplateResource(NotifyMessageTemplateRepository notifyMessageTemplateRepository) {
        this.notifyMessageTemplateRepository = notifyMessageTemplateRepository;
    }

    /**
     * POST  /sms-templates : Create a new notifyMessageTemplate.
     *
     * @param notifyMessageTemplate the notifyMessageTemplate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new notifyMessageTemplate, or with status 400 (Bad Request) if the notifyMessageTemplate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sms-templates")
    @Timed
    public ResponseEntity<NotifyMessageTemplate> createSmsTemplate(@Valid @RequestBody NotifyMessageTemplate notifyMessageTemplate) throws URISyntaxException {
        log.debug("REST request to save NotifyMessageTemplate : {}", notifyMessageTemplate);
        if (notifyMessageTemplate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new notifyMessageTemplate cannot already have an ID")).body(null);
        }
        NotifyMessageTemplate result = notifyMessageTemplateRepository.save(notifyMessageTemplate);
        return ResponseEntity.created(new URI("/api/sms-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sms-templates : Updates an existing notifyMessageTemplate.
     *
     * @param notifyMessageTemplate the notifyMessageTemplate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated notifyMessageTemplate,
     * or with status 400 (Bad Request) if the notifyMessageTemplate is not valid,
     * or with status 500 (Internal Server Error) if the notifyMessageTemplate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sms-templates")
    @Timed
    public ResponseEntity<NotifyMessageTemplate> updateSmsTemplate(@Valid @RequestBody NotifyMessageTemplate notifyMessageTemplate) throws URISyntaxException {
        log.debug("REST request to update NotifyMessageTemplate : {}", notifyMessageTemplate);
        if (notifyMessageTemplate.getId() == null) {
            return createSmsTemplate(notifyMessageTemplate);
        }
        NotifyMessageTemplate result = notifyMessageTemplateRepository.save(notifyMessageTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, notifyMessageTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sms-templates : get all the smsTemplates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of smsTemplates in body
     */
    @GetMapping("/sms-templates")
    @Timed
    public List<NotifyMessageTemplate> getAllSmsTemplates() {
        log.debug("REST request to get all SmsTemplates");
        return notifyMessageTemplateRepository.findAll();
    }

    /**
     * GET  /sms-templates/:id : get the "id" smsTemplate.
     *
     * @param id the id of the smsTemplate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the smsTemplate, or with status 404 (Not Found)
     */
    @GetMapping("/sms-templates/{id}")
    @Timed
    public ResponseEntity<NotifyMessageTemplate> getSmsTemplate(@PathVariable Long id) {
        log.debug("REST request to get NotifyMessageTemplate : {}", id);
        NotifyMessageTemplate notifyMessageTemplate = notifyMessageTemplateRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(notifyMessageTemplate));
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
        log.debug("REST request to delete NotifyMessageTemplate : {}", id);
        notifyMessageTemplateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
