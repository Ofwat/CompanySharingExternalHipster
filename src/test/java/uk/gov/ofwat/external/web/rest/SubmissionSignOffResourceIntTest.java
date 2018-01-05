package uk.gov.ofwat.external.web.rest;

import uk.gov.ofwat.external.CompanySharingExternalApp;

import uk.gov.ofwat.external.domain.SubmissionSignOff;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.domain.CompanyDataBundle;
import uk.gov.ofwat.external.repository.SubmissionSignOffRepository;
import uk.gov.ofwat.external.service.SubmissionSignOffService;
import uk.gov.ofwat.external.service.dto.SubmissionSignOffDTO;
import uk.gov.ofwat.external.service.mapper.SubmissionSignOffMapper;
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
 * Test class for the SubmissionSignOffResource REST controller.
 *
 * @see SubmissionSignOffResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class SubmissionSignOffResourceIntTest {

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    @Autowired
    private SubmissionSignOffRepository submissionSignOffRepository;

    @Autowired
    private SubmissionSignOffMapper submissionSignOffMapper;

    @Autowired
    private SubmissionSignOffService submissionSignOffService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSubmissionSignOffMockMvc;

    private SubmissionSignOff submissionSignOff;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubmissionSignOffResource submissionSignOffResource = new SubmissionSignOffResource(submissionSignOffService);
        this.restSubmissionSignOffMockMvc = MockMvcBuilders.standaloneSetup(submissionSignOffResource)
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
    public static SubmissionSignOff createEntity(EntityManager em) {
        SubmissionSignOff submissionSignOff = new SubmissionSignOff()
            .status(DEFAULT_STATUS)
            .reason(DEFAULT_REASON);
        // Add required entity
        User signatory = UserResourceIntTest.createEntity(em);
        em.persist(signatory);
        em.flush();
        submissionSignOff.setSignatory(signatory);
        // Add required entity
        CompanyDataBundle companyDataBundle = CompanyDataBundleResourceIntTest.createEntity(em);
        em.persist(companyDataBundle);
        em.flush();
        submissionSignOff.setCompanyDataBundle(companyDataBundle);
        return submissionSignOff;
    }

    @Before
    public void initTest() {
        submissionSignOff = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubmissionSignOff() throws Exception {
        int databaseSizeBeforeCreate = submissionSignOffRepository.findAll().size();

        // Create the SubmissionSignOff
        SubmissionSignOffDTO submissionSignOffDTO = submissionSignOffMapper.toDto(submissionSignOff);
        restSubmissionSignOffMockMvc.perform(post("/api/submission-sign-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(submissionSignOffDTO)))
            .andExpect(status().isCreated());

        // Validate the SubmissionSignOff in the database
        List<SubmissionSignOff> submissionSignOffList = submissionSignOffRepository.findAll();
        assertThat(submissionSignOffList).hasSize(databaseSizeBeforeCreate + 1);
        SubmissionSignOff testSubmissionSignOff = submissionSignOffList.get(submissionSignOffList.size() - 1);
        assertThat(testSubmissionSignOff.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSubmissionSignOff.getReason()).isEqualTo(DEFAULT_REASON);
    }

    @Test
    @Transactional
    public void createSubmissionSignOffWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = submissionSignOffRepository.findAll().size();

        // Create the SubmissionSignOff with an existing ID
        submissionSignOff.setId(1L);
        SubmissionSignOffDTO submissionSignOffDTO = submissionSignOffMapper.toDto(submissionSignOff);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubmissionSignOffMockMvc.perform(post("/api/submission-sign-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(submissionSignOffDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SubmissionSignOff> submissionSignOffList = submissionSignOffRepository.findAll();
        assertThat(submissionSignOffList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = submissionSignOffRepository.findAll().size();
        // set the field null
        submissionSignOff.setStatus(null);

        // Create the SubmissionSignOff, which fails.
        SubmissionSignOffDTO submissionSignOffDTO = submissionSignOffMapper.toDto(submissionSignOff);

        restSubmissionSignOffMockMvc.perform(post("/api/submission-sign-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(submissionSignOffDTO)))
            .andExpect(status().isBadRequest());

        List<SubmissionSignOff> submissionSignOffList = submissionSignOffRepository.findAll();
        assertThat(submissionSignOffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubmissionSignOffs() throws Exception {
        // Initialize the database
        submissionSignOffRepository.saveAndFlush(submissionSignOff);

        // Get all the submissionSignOffList
        restSubmissionSignOffMockMvc.perform(get("/api/submission-sign-offs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(submissionSignOff.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())));
    }

    @Test
    @Transactional
    public void getSubmissionSignOff() throws Exception {
        // Initialize the database
        submissionSignOffRepository.saveAndFlush(submissionSignOff);

        // Get the submissionSignOff
        restSubmissionSignOffMockMvc.perform(get("/api/submission-sign-offs/{id}", submissionSignOff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(submissionSignOff.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubmissionSignOff() throws Exception {
        // Get the submissionSignOff
        restSubmissionSignOffMockMvc.perform(get("/api/submission-sign-offs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubmissionSignOff() throws Exception {
        // Initialize the database
        submissionSignOffRepository.saveAndFlush(submissionSignOff);
        int databaseSizeBeforeUpdate = submissionSignOffRepository.findAll().size();

        // Update the submissionSignOff
        SubmissionSignOff updatedSubmissionSignOff = submissionSignOffRepository.findOne(submissionSignOff.getId());
        updatedSubmissionSignOff
            .status(UPDATED_STATUS)
            .reason(UPDATED_REASON);
        SubmissionSignOffDTO submissionSignOffDTO = submissionSignOffMapper.toDto(updatedSubmissionSignOff);

        restSubmissionSignOffMockMvc.perform(put("/api/submission-sign-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(submissionSignOffDTO)))
            .andExpect(status().isOk());

        // Validate the SubmissionSignOff in the database
        List<SubmissionSignOff> submissionSignOffList = submissionSignOffRepository.findAll();
        assertThat(submissionSignOffList).hasSize(databaseSizeBeforeUpdate);
        SubmissionSignOff testSubmissionSignOff = submissionSignOffList.get(submissionSignOffList.size() - 1);
        assertThat(testSubmissionSignOff.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSubmissionSignOff.getReason()).isEqualTo(UPDATED_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingSubmissionSignOff() throws Exception {
        int databaseSizeBeforeUpdate = submissionSignOffRepository.findAll().size();

        // Create the SubmissionSignOff
        SubmissionSignOffDTO submissionSignOffDTO = submissionSignOffMapper.toDto(submissionSignOff);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSubmissionSignOffMockMvc.perform(put("/api/submission-sign-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(submissionSignOffDTO)))
            .andExpect(status().isCreated());

        // Validate the SubmissionSignOff in the database
        List<SubmissionSignOff> submissionSignOffList = submissionSignOffRepository.findAll();
        assertThat(submissionSignOffList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSubmissionSignOff() throws Exception {
        // Initialize the database
        submissionSignOffRepository.saveAndFlush(submissionSignOff);
        int databaseSizeBeforeDelete = submissionSignOffRepository.findAll().size();

        // Get the submissionSignOff
        restSubmissionSignOffMockMvc.perform(delete("/api/submission-sign-offs/{id}", submissionSignOff.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SubmissionSignOff> submissionSignOffList = submissionSignOffRepository.findAll();
        assertThat(submissionSignOffList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubmissionSignOff.class);
        SubmissionSignOff submissionSignOff1 = new SubmissionSignOff();
        submissionSignOff1.setId(1L);
        SubmissionSignOff submissionSignOff2 = new SubmissionSignOff();
        submissionSignOff2.setId(submissionSignOff1.getId());
        assertThat(submissionSignOff1).isEqualTo(submissionSignOff2);
        submissionSignOff2.setId(2L);
        assertThat(submissionSignOff1).isNotEqualTo(submissionSignOff2);
        submissionSignOff1.setId(null);
        assertThat(submissionSignOff1).isNotEqualTo(submissionSignOff2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubmissionSignOffDTO.class);
        SubmissionSignOffDTO submissionSignOffDTO1 = new SubmissionSignOffDTO();
        submissionSignOffDTO1.setId(1L);
        SubmissionSignOffDTO submissionSignOffDTO2 = new SubmissionSignOffDTO();
        assertThat(submissionSignOffDTO1).isNotEqualTo(submissionSignOffDTO2);
        submissionSignOffDTO2.setId(submissionSignOffDTO1.getId());
        assertThat(submissionSignOffDTO1).isEqualTo(submissionSignOffDTO2);
        submissionSignOffDTO2.setId(2L);
        assertThat(submissionSignOffDTO1).isNotEqualTo(submissionSignOffDTO2);
        submissionSignOffDTO1.setId(null);
        assertThat(submissionSignOffDTO1).isNotEqualTo(submissionSignOffDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(submissionSignOffMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(submissionSignOffMapper.fromId(null)).isNull();
    }
}
