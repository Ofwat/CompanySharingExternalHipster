package uk.gov.ofwat.external.web.rest;

import uk.gov.ofwat.external.CompanySharingExternalApp;

import uk.gov.ofwat.external.domain.DataBundle;
import uk.gov.ofwat.external.domain.PublishingStatus;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.domain.DataCollection;
import uk.gov.ofwat.external.repository.DataBundleRepository;
import uk.gov.ofwat.external.service.DataBundleService;
import uk.gov.ofwat.external.service.dto.DataBundleDTO;
import uk.gov.ofwat.external.service.mapper.DataBundleMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DataBundleResource REST controller.
 *
 * @see DataBundleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class DataBundleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_GUIDANCE = "AAAAAAAAAA";
    private static final String UPDATED_GUIDANCE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DEFAULT_DEADLINE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEFAULT_DEADLINE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DataBundleRepository dataBundleRepository;

    @Autowired
    private DataBundleMapper dataBundleMapper;

    @Autowired
    private DataBundleService dataBundleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDataBundleMockMvc;

    private DataBundle dataBundle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DataBundleResource dataBundleResource = new DataBundleResource(dataBundleService);
        this.restDataBundleMockMvc = MockMvcBuilders.standaloneSetup(dataBundleResource)
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
    public static DataBundle createEntity(EntityManager em) {
        DataBundle dataBundle = new DataBundle()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .guidance(DEFAULT_GUIDANCE)
            .defaultDeadline(DEFAULT_DEFAULT_DEADLINE);
        // Add required entity
        PublishingStatus status = PublishingStatusResourceIntTest.createEntity(em);
        em.persist(status);
        em.flush();
        dataBundle.setStatus(status);
        // Add required entity
        User owner = UserResourceIntTest.createEntity(em);
        em.persist(owner);
        em.flush();
        dataBundle.setOwner(owner);
        // Add required entity
        User reviewer = UserResourceIntTest.createEntity(em);
        em.persist(reviewer);
        em.flush();
        dataBundle.setReviewer(reviewer);
        // Add required entity
        DataCollection dataCollection = DataCollectionResourceIntTest.createEntity(em);
        em.persist(dataCollection);
        em.flush();
        dataBundle.setDataCollection(dataCollection);
        return dataBundle;
    }

    @Before
    public void initTest() {
        dataBundle = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataBundle() throws Exception {
        int databaseSizeBeforeCreate = dataBundleRepository.findAll().size();

        // Create the DataBundle
        DataBundleDTO dataBundleDTO = dataBundleMapper.toDto(dataBundle);
        restDataBundleMockMvc.perform(post("/api/data-bundles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataBundleDTO)))
            .andExpect(status().isCreated());

        // Validate the DataBundle in the database
        List<DataBundle> dataBundleList = dataBundleRepository.findAll();
        assertThat(dataBundleList).hasSize(databaseSizeBeforeCreate + 1);
        DataBundle testDataBundle = dataBundleList.get(dataBundleList.size() - 1);
        assertThat(testDataBundle.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataBundle.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDataBundle.getGuidance()).isEqualTo(DEFAULT_GUIDANCE);
        assertThat(testDataBundle.getDefaultDeadline()).isEqualTo(DEFAULT_DEFAULT_DEADLINE);
    }

    @Test
    @Transactional
    public void createDataBundleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataBundleRepository.findAll().size();

        // Create the DataBundle with an existing ID
        dataBundle.setId(1L);
        DataBundleDTO dataBundleDTO = dataBundleMapper.toDto(dataBundle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataBundleMockMvc.perform(post("/api/data-bundles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataBundleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DataBundle> dataBundleList = dataBundleRepository.findAll();
        assertThat(dataBundleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataBundleRepository.findAll().size();
        // set the field null
        dataBundle.setName(null);

        // Create the DataBundle, which fails.
        DataBundleDTO dataBundleDTO = dataBundleMapper.toDto(dataBundle);

        restDataBundleMockMvc.perform(post("/api/data-bundles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataBundleDTO)))
            .andExpect(status().isBadRequest());

        List<DataBundle> dataBundleList = dataBundleRepository.findAll();
        assertThat(dataBundleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDataBundles() throws Exception {
        // Initialize the database
        dataBundleRepository.saveAndFlush(dataBundle);

        // Get all the dataBundleList
        restDataBundleMockMvc.perform(get("/api/data-bundles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataBundle.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].guidance").value(hasItem(DEFAULT_GUIDANCE.toString())))
            .andExpect(jsonPath("$.[*].defaultDeadline").value(hasItem(DEFAULT_DEFAULT_DEADLINE.toString())));
    }

    @Test
    @Transactional
    public void getDataBundle() throws Exception {
        // Initialize the database
        dataBundleRepository.saveAndFlush(dataBundle);

        // Get the dataBundle
        restDataBundleMockMvc.perform(get("/api/data-bundles/{id}", dataBundle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataBundle.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.guidance").value(DEFAULT_GUIDANCE.toString()))
            .andExpect(jsonPath("$.defaultDeadline").value(DEFAULT_DEFAULT_DEADLINE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDataBundle() throws Exception {
        // Get the dataBundle
        restDataBundleMockMvc.perform(get("/api/data-bundles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataBundle() throws Exception {
        // Initialize the database
        dataBundleRepository.saveAndFlush(dataBundle);
        int databaseSizeBeforeUpdate = dataBundleRepository.findAll().size();

        // Update the dataBundle
        DataBundle updatedDataBundle = dataBundleRepository.findOne(dataBundle.getId());
        updatedDataBundle
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .guidance(UPDATED_GUIDANCE)
            .defaultDeadline(UPDATED_DEFAULT_DEADLINE);
        DataBundleDTO dataBundleDTO = dataBundleMapper.toDto(updatedDataBundle);

        restDataBundleMockMvc.perform(put("/api/data-bundles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataBundleDTO)))
            .andExpect(status().isOk());

        // Validate the DataBundle in the database
        List<DataBundle> dataBundleList = dataBundleRepository.findAll();
        assertThat(dataBundleList).hasSize(databaseSizeBeforeUpdate);
        DataBundle testDataBundle = dataBundleList.get(dataBundleList.size() - 1);
        assertThat(testDataBundle.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataBundle.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDataBundle.getGuidance()).isEqualTo(UPDATED_GUIDANCE);
        assertThat(testDataBundle.getDefaultDeadline()).isEqualTo(UPDATED_DEFAULT_DEADLINE);
    }

    @Test
    @Transactional
    public void updateNonExistingDataBundle() throws Exception {
        int databaseSizeBeforeUpdate = dataBundleRepository.findAll().size();

        // Create the DataBundle
        DataBundleDTO dataBundleDTO = dataBundleMapper.toDto(dataBundle);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDataBundleMockMvc.perform(put("/api/data-bundles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataBundleDTO)))
            .andExpect(status().isCreated());

        // Validate the DataBundle in the database
        List<DataBundle> dataBundleList = dataBundleRepository.findAll();
        assertThat(dataBundleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDataBundle() throws Exception {
        // Initialize the database
        dataBundleRepository.saveAndFlush(dataBundle);
        int databaseSizeBeforeDelete = dataBundleRepository.findAll().size();

        // Get the dataBundle
        restDataBundleMockMvc.perform(delete("/api/data-bundles/{id}", dataBundle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DataBundle> dataBundleList = dataBundleRepository.findAll();
        assertThat(dataBundleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataBundle.class);
        DataBundle dataBundle1 = new DataBundle();
        dataBundle1.setId(1L);
        DataBundle dataBundle2 = new DataBundle();
        dataBundle2.setId(dataBundle1.getId());
        assertThat(dataBundle1).isEqualTo(dataBundle2);
        dataBundle2.setId(2L);
        assertThat(dataBundle1).isNotEqualTo(dataBundle2);
        dataBundle1.setId(null);
        assertThat(dataBundle1).isNotEqualTo(dataBundle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataBundleDTO.class);
        DataBundleDTO dataBundleDTO1 = new DataBundleDTO();
        dataBundleDTO1.setId(1L);
        DataBundleDTO dataBundleDTO2 = new DataBundleDTO();
        assertThat(dataBundleDTO1).isNotEqualTo(dataBundleDTO2);
        dataBundleDTO2.setId(dataBundleDTO1.getId());
        assertThat(dataBundleDTO1).isEqualTo(dataBundleDTO2);
        dataBundleDTO2.setId(2L);
        assertThat(dataBundleDTO1).isNotEqualTo(dataBundleDTO2);
        dataBundleDTO1.setId(null);
        assertThat(dataBundleDTO1).isNotEqualTo(dataBundleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dataBundleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dataBundleMapper.fromId(null)).isNull();
    }
}
