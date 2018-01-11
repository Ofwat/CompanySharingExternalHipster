package uk.gov.ofwat.external.web.rest;

import uk.gov.ofwat.external.CompanySharingExternalApp;

import uk.gov.ofwat.external.domain.message.MessageConstants;
import uk.gov.ofwat.external.domain.message.NotifyMessageTemplate;
import uk.gov.ofwat.external.repository.NotifyMessageTemplateRepository;
import uk.gov.ofwat.external.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SmsTemplateResource REST controller.
 *
 * @see SmsTemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class NotifyMessageTemplateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_ID = "BBBBBBBBBB";

    @Autowired
    private NotifyMessageTemplateRepository notifyMessageTemplateRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSmsTemplateMockMvc;

    private NotifyMessageTemplate notifyMessageTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SmsTemplateResource smsTemplateResource = new SmsTemplateResource(notifyMessageTemplateRepository);
        this.restSmsTemplateMockMvc = MockMvcBuilders.standaloneSetup(smsTemplateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotifyMessageTemplate createEntity(EntityManager em) {
        NotifyMessageTemplate notifyMessageTemplate = new NotifyMessageTemplate()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .templateId(DEFAULT_TEMPLATE_ID)
            .type(MessageConstants.EMAIL);
        return notifyMessageTemplate;
    }

    @Before
    public void initTest() {
        notifyMessageTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createSmsTemplate() throws Exception {
        int databaseSizeBeforeCreate = notifyMessageTemplateRepository.findAll().size();

        // Create the NotifyMessageTemplate
        restSmsTemplateMockMvc.perform(post("/api/sms-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyMessageTemplate)))
            .andExpect(status().isCreated());

        // Validate the NotifyMessageTemplate in the database
        List<NotifyMessageTemplate> notifyMessageTemplateList = notifyMessageTemplateRepository.findAll();
        assertThat(notifyMessageTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        NotifyMessageTemplate testNotifyMessageTemplate = notifyMessageTemplateList.get(notifyMessageTemplateList.size() - 1);
        assertThat(testNotifyMessageTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNotifyMessageTemplate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNotifyMessageTemplate.getTemplateId()).isEqualTo(DEFAULT_TEMPLATE_ID);
    }

    @Test
    @Transactional
    public void createSmsTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notifyMessageTemplateRepository.findAll().size();

        // Create the NotifyMessageTemplate with an existing ID
        notifyMessageTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSmsTemplateMockMvc.perform(post("/api/sms-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyMessageTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<NotifyMessageTemplate> notifyMessageTemplateList = notifyMessageTemplateRepository.findAll();
        assertThat(notifyMessageTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = notifyMessageTemplateRepository.findAll().size();
        // set the field null
        notifyMessageTemplate.setName(null);

        // Create the NotifyMessageTemplate, which fails.

        restSmsTemplateMockMvc.perform(post("/api/sms-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyMessageTemplate)))
            .andExpect(status().isBadRequest());

        List<NotifyMessageTemplate> notifyMessageTemplateList = notifyMessageTemplateRepository.findAll();
        assertThat(notifyMessageTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = notifyMessageTemplateRepository.findAll().size();
        // set the field null
        notifyMessageTemplate.setDescription(null);

        // Create the NotifyMessageTemplate, which fails.

        restSmsTemplateMockMvc.perform(post("/api/sms-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyMessageTemplate)))
            .andExpect(status().isBadRequest());

        List<NotifyMessageTemplate> notifyMessageTemplateList = notifyMessageTemplateRepository.findAll();
        assertThat(notifyMessageTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemplateIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = notifyMessageTemplateRepository.findAll().size();
        // set the field null
        notifyMessageTemplate.setTemplateId(null);

        // Create the NotifyMessageTemplate, which fails.

        restSmsTemplateMockMvc.perform(post("/api/sms-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyMessageTemplate)))
            .andExpect(status().isBadRequest());

        List<NotifyMessageTemplate> notifyMessageTemplateList = notifyMessageTemplateRepository.findAll();
        assertThat(notifyMessageTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSmsTemplates() throws Exception {
        // Initialize the database
        notifyMessageTemplateRepository.saveAndFlush(notifyMessageTemplate);

        // Get all the smsTemplateList
        restSmsTemplateMockMvc.perform(get("/api/sms-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notifyMessageTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].templateId").value(hasItem(DEFAULT_TEMPLATE_ID.toString())));
    }

    @Test
    @Transactional
    public void getSmsTemplate() throws Exception {
        // Initialize the database
        notifyMessageTemplateRepository.saveAndFlush(notifyMessageTemplate);

        // Get the notifyMessageTemplate
        restSmsTemplateMockMvc.perform(get("/api/sms-templates/{id}", notifyMessageTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(notifyMessageTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.templateId").value(DEFAULT_TEMPLATE_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSmsTemplate() throws Exception {
        // Get the notifyMessageTemplate
        restSmsTemplateMockMvc.perform(get("/api/sms-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmsTemplate() throws Exception {
        // Initialize the database
        notifyMessageTemplateRepository.saveAndFlush(notifyMessageTemplate);
        int databaseSizeBeforeUpdate = notifyMessageTemplateRepository.findAll().size();

        // Update the notifyMessageTemplate
        NotifyMessageTemplate updatedNotifyMessageTemplate = notifyMessageTemplateRepository.findOne(notifyMessageTemplate.getId());
        updatedNotifyMessageTemplate
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .templateId(UPDATED_TEMPLATE_ID);

        restSmsTemplateMockMvc.perform(put("/api/sms-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNotifyMessageTemplate)))
            .andExpect(status().isOk());

        // Validate the NotifyMessageTemplate in the database
        List<NotifyMessageTemplate> notifyMessageTemplateList = notifyMessageTemplateRepository.findAll();
        assertThat(notifyMessageTemplateList).hasSize(databaseSizeBeforeUpdate);
        NotifyMessageTemplate testNotifyMessageTemplate = notifyMessageTemplateList.get(notifyMessageTemplateList.size() - 1);
        assertThat(testNotifyMessageTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNotifyMessageTemplate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNotifyMessageTemplate.getTemplateId()).isEqualTo(UPDATED_TEMPLATE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSmsTemplate() throws Exception {
        int databaseSizeBeforeUpdate = notifyMessageTemplateRepository.findAll().size();

        // Create the NotifyMessageTemplate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSmsTemplateMockMvc.perform(put("/api/sms-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notifyMessageTemplate)))
            .andExpect(status().isCreated());

        // Validate the NotifyMessageTemplate in the database
        List<NotifyMessageTemplate> notifyMessageTemplateList = notifyMessageTemplateRepository.findAll();
        assertThat(notifyMessageTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSmsTemplate() throws Exception {
        // Initialize the database
        notifyMessageTemplateRepository.saveAndFlush(notifyMessageTemplate);
        int databaseSizeBeforeDelete = notifyMessageTemplateRepository.findAll().size();

        // Get the notifyMessageTemplate
        restSmsTemplateMockMvc.perform(delete("/api/sms-templates/{id}", notifyMessageTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NotifyMessageTemplate> notifyMessageTemplateList = notifyMessageTemplateRepository.findAll();
        assertThat(notifyMessageTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotifyMessageTemplate.class);
        NotifyMessageTemplate notifyMessageTemplate1 = new NotifyMessageTemplate();
        notifyMessageTemplate1.setId(1L);
        NotifyMessageTemplate notifyMessageTemplate2 = new NotifyMessageTemplate();
        notifyMessageTemplate2.setId(notifyMessageTemplate1.getId());
        assertThat(notifyMessageTemplate1).isEqualTo(notifyMessageTemplate2);
        notifyMessageTemplate2.setId(2L);
        assertThat(notifyMessageTemplate1).isNotEqualTo(notifyMessageTemplate2);
        notifyMessageTemplate1.setId(null);
        assertThat(notifyMessageTemplate1).isNotEqualTo(notifyMessageTemplate2);
    }
}
