package uk.gov.ofwat.external.web.rest;

import uk.gov.ofwat.external.CompanySharingExternalApp;

import uk.gov.ofwat.external.domain.DataCollection;
import uk.gov.ofwat.external.repository.DataCollectionRepository;
import uk.gov.ofwat.external.service.DataCollectionService;
import uk.gov.ofwat.external.service.dto.DataCollectionDTO;
import uk.gov.ofwat.external.service.mapper.DataCollectionMapper;
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
 * Test class for the DataCollectionResource REST controller.
 *
 * @see DataCollectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class DataCollectionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DataCollectionRepository dataCollectionRepository;

    @Autowired
    private DataCollectionMapper dataCollectionMapper;

    @Autowired
    private DataCollectionService dataCollectionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDataCollectionMockMvc;

    private DataCollection dataCollection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DataCollectionResource dataCollectionResource = new DataCollectionResource(dataCollectionService, dataCollectionRepository);
        this.restDataCollectionMockMvc = MockMvcBuilders.standaloneSetup(dataCollectionResource)
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
    public static DataCollection createEntity(EntityManager em) {
        DataCollection dataCollection = new DataCollection()
            .name(DEFAULT_NAME);
        return dataCollection;
    }

    @Before
    public void initTest() {
        dataCollection = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataCollection() throws Exception {
        int databaseSizeBeforeCreate = dataCollectionRepository.findAll().size();

        // Create the DataCollection
        DataCollectionDTO dataCollectionDTO = dataCollectionMapper.toDto(dataCollection);
        restDataCollectionMockMvc.perform(post("/api/data-collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCollectionDTO)))
            .andExpect(status().isCreated());

        // Validate the DataCollection in the database
        List<DataCollection> dataCollectionList = dataCollectionRepository.findAll();
        assertThat(dataCollectionList).hasSize(databaseSizeBeforeCreate + 1);
        DataCollection testDataCollection = dataCollectionList.get(dataCollectionList.size() - 1);
        assertThat(testDataCollection.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDataCollectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataCollectionRepository.findAll().size();

        // Create the DataCollection with an existing ID
        dataCollection.setId(1L);
        DataCollectionDTO dataCollectionDTO = dataCollectionMapper.toDto(dataCollection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataCollectionMockMvc.perform(post("/api/data-collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCollectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DataCollection> dataCollectionList = dataCollectionRepository.findAll();
        assertThat(dataCollectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataCollectionRepository.findAll().size();
        // set the field null
        dataCollection.setName(null);

        // Create the DataCollection, which fails.
        DataCollectionDTO dataCollectionDTO = dataCollectionMapper.toDto(dataCollection);

        restDataCollectionMockMvc.perform(post("/api/data-collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCollectionDTO)))
            .andExpect(status().isBadRequest());

        List<DataCollection> dataCollectionList = dataCollectionRepository.findAll();
        assertThat(dataCollectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDataCollections() throws Exception {
        // Initialize the database
        dataCollectionRepository.saveAndFlush(dataCollection);

        // Get all the dataCollectionList
        restDataCollectionMockMvc.perform(get("/api/data-collections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataCollection.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getDataCollection() throws Exception {
        // Initialize the database
        dataCollectionRepository.saveAndFlush(dataCollection);

        // Get the dataCollection
        restDataCollectionMockMvc.perform(get("/api/data-collections/{id}", dataCollection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataCollection.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDataCollection() throws Exception {
        // Get the dataCollection
        restDataCollectionMockMvc.perform(get("/api/data-collections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataCollection() throws Exception {
        // Initialize the database
        dataCollectionRepository.saveAndFlush(dataCollection);
        int databaseSizeBeforeUpdate = dataCollectionRepository.findAll().size();

        // Update the dataCollection
        DataCollection updatedDataCollection = dataCollectionRepository.findOne(dataCollection.getId());
        updatedDataCollection
            .name(UPDATED_NAME);
        DataCollectionDTO dataCollectionDTO = dataCollectionMapper.toDto(updatedDataCollection);

        restDataCollectionMockMvc.perform(put("/api/data-collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCollectionDTO)))
            .andExpect(status().isOk());

        // Validate the DataCollection in the database
        List<DataCollection> dataCollectionList = dataCollectionRepository.findAll();
        assertThat(dataCollectionList).hasSize(databaseSizeBeforeUpdate);
        DataCollection testDataCollection = dataCollectionList.get(dataCollectionList.size() - 1);
        assertThat(testDataCollection.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDataCollection() throws Exception {
        int databaseSizeBeforeUpdate = dataCollectionRepository.findAll().size();

        // Create the DataCollection
        DataCollectionDTO dataCollectionDTO = dataCollectionMapper.toDto(dataCollection);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDataCollectionMockMvc.perform(put("/api/data-collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataCollectionDTO)))
            .andExpect(status().isCreated());

        // Validate the DataCollection in the database
        List<DataCollection> dataCollectionList = dataCollectionRepository.findAll();
        assertThat(dataCollectionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDataCollection() throws Exception {
        // Initialize the database
        dataCollectionRepository.saveAndFlush(dataCollection);
        int databaseSizeBeforeDelete = dataCollectionRepository.findAll().size();

        // Get the dataCollection
        restDataCollectionMockMvc.perform(delete("/api/data-collections/{id}", dataCollection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DataCollection> dataCollectionList = dataCollectionRepository.findAll();
        assertThat(dataCollectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataCollection.class);
        DataCollection dataCollection1 = new DataCollection();
        dataCollection1.setId(1L);
        DataCollection dataCollection2 = new DataCollection();
        dataCollection2.setId(dataCollection1.getId());
        assertThat(dataCollection1).isEqualTo(dataCollection2);
        dataCollection2.setId(2L);
        assertThat(dataCollection1).isNotEqualTo(dataCollection2);
        dataCollection1.setId(null);
        assertThat(dataCollection1).isNotEqualTo(dataCollection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataCollectionDTO.class);
        DataCollectionDTO dataCollectionDTO1 = new DataCollectionDTO();
        dataCollectionDTO1.setId(1L);
        DataCollectionDTO dataCollectionDTO2 = new DataCollectionDTO();
        assertThat(dataCollectionDTO1).isNotEqualTo(dataCollectionDTO2);
        dataCollectionDTO2.setId(dataCollectionDTO1.getId());
        assertThat(dataCollectionDTO1).isEqualTo(dataCollectionDTO2);
        dataCollectionDTO2.setId(2L);
        assertThat(dataCollectionDTO1).isNotEqualTo(dataCollectionDTO2);
        dataCollectionDTO1.setId(null);
        assertThat(dataCollectionDTO1).isNotEqualTo(dataCollectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dataCollectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dataCollectionMapper.fromId(null)).isNull();
    }
}
