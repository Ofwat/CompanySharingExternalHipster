package uk.gov.ofwat.external.web.rest;

import uk.gov.ofwat.external.CompanySharingExternalApp;

import uk.gov.ofwat.external.domain.SmsTemplate;
import uk.gov.ofwat.external.repository.SmsTemplateRepository;
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
public class SmsTemplateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_ID = "BBBBBBBBBB";

    @Autowired
    private SmsTemplateRepository smsTemplateRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSmsTemplateMockMvc;

    private SmsTemplate smsTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SmsTemplateResource smsTemplateResource = new SmsTemplateResource(smsTemplateRepository);
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
    public static SmsTemplate createEntity(EntityManager em) {
        SmsTemplate smsTemplate = new SmsTemplate()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .templateId(DEFAULT_TEMPLATE_ID);
        return smsTemplate;
    }

    @Before
    public void initTest() {
        smsTemplate = createEntity(em);
    }

    @Test
    @Transactional
    public void createSmsTemplate() throws Exception {
        int databaseSizeBeforeCreate = smsTemplateRepository.findAll().size();

        // Create the SmsTemplate
        restSmsTemplateMockMvc.perform(post("/api/sms-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsTemplate)))
            .andExpect(status().isCreated());

        // Validate the SmsTemplate in the database
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        SmsTemplate testSmsTemplate = smsTemplateList.get(smsTemplateList.size() - 1);
        assertThat(testSmsTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSmsTemplate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSmsTemplate.getTemplateId()).isEqualTo(DEFAULT_TEMPLATE_ID);
    }

    @Test
    @Transactional
    public void createSmsTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = smsTemplateRepository.findAll().size();

        // Create the SmsTemplate with an existing ID
        smsTemplate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSmsTemplateMockMvc.perform(post("/api/sms-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsTemplate)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsTemplateRepository.findAll().size();
        // set the field null
        smsTemplate.setName(null);

        // Create the SmsTemplate, which fails.

        restSmsTemplateMockMvc.perform(post("/api/sms-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsTemplate)))
            .andExpect(status().isBadRequest());

        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsTemplateRepository.findAll().size();
        // set the field null
        smsTemplate.setDescription(null);

        // Create the SmsTemplate, which fails.

        restSmsTemplateMockMvc.perform(post("/api/sms-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsTemplate)))
            .andExpect(status().isBadRequest());

        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemplateIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = smsTemplateRepository.findAll().size();
        // set the field null
        smsTemplate.setTemplateId(null);

        // Create the SmsTemplate, which fails.

        restSmsTemplateMockMvc.perform(post("/api/sms-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsTemplate)))
            .andExpect(status().isBadRequest());

        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSmsTemplates() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get all the smsTemplateList
        restSmsTemplateMockMvc.perform(get("/api/sms-templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(smsTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].templateId").value(hasItem(DEFAULT_TEMPLATE_ID.toString())));
    }

    @Test
    @Transactional
    public void getSmsTemplate() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);

        // Get the smsTemplate
        restSmsTemplateMockMvc.perform(get("/api/sms-templates/{id}", smsTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(smsTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.templateId").value(DEFAULT_TEMPLATE_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSmsTemplate() throws Exception {
        // Get the smsTemplate
        restSmsTemplateMockMvc.perform(get("/api/sms-templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmsTemplate() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);
        int databaseSizeBeforeUpdate = smsTemplateRepository.findAll().size();

        // Update the smsTemplate
        SmsTemplate updatedSmsTemplate = smsTemplateRepository.findOne(smsTemplate.getId());
        updatedSmsTemplate
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .templateId(UPDATED_TEMPLATE_ID);

        restSmsTemplateMockMvc.perform(put("/api/sms-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSmsTemplate)))
            .andExpect(status().isOk());

        // Validate the SmsTemplate in the database
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeUpdate);
        SmsTemplate testSmsTemplate = smsTemplateList.get(smsTemplateList.size() - 1);
        assertThat(testSmsTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSmsTemplate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSmsTemplate.getTemplateId()).isEqualTo(UPDATED_TEMPLATE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSmsTemplate() throws Exception {
        int databaseSizeBeforeUpdate = smsTemplateRepository.findAll().size();

        // Create the SmsTemplate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSmsTemplateMockMvc.perform(put("/api/sms-templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(smsTemplate)))
            .andExpect(status().isCreated());

        // Validate the SmsTemplate in the database
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSmsTemplate() throws Exception {
        // Initialize the database
        smsTemplateRepository.saveAndFlush(smsTemplate);
        int databaseSizeBeforeDelete = smsTemplateRepository.findAll().size();

        // Get the smsTemplate
        restSmsTemplateMockMvc.perform(delete("/api/sms-templates/{id}", smsTemplate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SmsTemplate> smsTemplateList = smsTemplateRepository.findAll();
        assertThat(smsTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SmsTemplate.class);
        SmsTemplate smsTemplate1 = new SmsTemplate();
        smsTemplate1.setId(1L);
        SmsTemplate smsTemplate2 = new SmsTemplate();
        smsTemplate2.setId(smsTemplate1.getId());
        assertThat(smsTemplate1).isEqualTo(smsTemplate2);
        smsTemplate2.setId(2L);
        assertThat(smsTemplate1).isNotEqualTo(smsTemplate2);
        smsTemplate1.setId(null);
        assertThat(smsTemplate1).isNotEqualTo(smsTemplate2);
    }
}
