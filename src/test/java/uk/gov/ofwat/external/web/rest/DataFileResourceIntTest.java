package uk.gov.ofwat.external.web.rest;

import uk.gov.ofwat.external.CompanySharingExternalApp;

import uk.gov.ofwat.external.domain.DataFile;
import uk.gov.ofwat.external.repository.DataFileRepository;
import uk.gov.ofwat.external.service.DataFileService;
import uk.gov.ofwat.external.service.dto.DataFileDTO;
import uk.gov.ofwat.external.service.mapper.DataFileMapper;
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
 * Test class for the DataFileResource REST controller.
 *
 * @see DataFileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class DataFileResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    @Autowired
    private DataFileRepository dataFileRepository;

    @Autowired
    private DataFileMapper dataFileMapper;

    @Autowired
    private DataFileService dataFileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDataFileMockMvc;

    private DataFile dataFile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DataFileResource dataFileResource = new DataFileResource(dataFileService);
        this.restDataFileMockMvc = MockMvcBuilders.standaloneSetup(dataFileResource)
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
    public static DataFile createEntity(EntityManager em) {
        DataFile dataFile = new DataFile()
            .name(DEFAULT_NAME)
            .location(DEFAULT_LOCATION);
        return dataFile;
    }

    @Before
    public void initTest() {
        dataFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataFile() throws Exception {
        int databaseSizeBeforeCreate = dataFileRepository.findAll().size();

        // Create the DataFile
        DataFileDTO dataFileDTO = dataFileMapper.toDto(dataFile);
        restDataFileMockMvc.perform(post("/api/data-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataFileDTO)))
            .andExpect(status().isCreated());

        // Validate the DataFile in the database
        List<DataFile> dataFileList = dataFileRepository.findAll();
        assertThat(dataFileList).hasSize(databaseSizeBeforeCreate + 1);
        DataFile testDataFile = dataFileList.get(dataFileList.size() - 1);
        assertThat(testDataFile.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataFile.getLocation()).isEqualTo(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    public void createDataFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataFileRepository.findAll().size();

        // Create the DataFile with an existing ID
        dataFile.setId(1L);
        DataFileDTO dataFileDTO = dataFileMapper.toDto(dataFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataFileMockMvc.perform(post("/api/data-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DataFile> dataFileList = dataFileRepository.findAll();
        assertThat(dataFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDataFiles() throws Exception {
        // Initialize the database
        dataFileRepository.saveAndFlush(dataFile);

        // Get all the dataFileList
        restDataFileMockMvc.perform(get("/api/data-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void getDataFile() throws Exception {
        // Initialize the database
        dataFileRepository.saveAndFlush(dataFile);

        // Get the dataFile
        restDataFileMockMvc.perform(get("/api/data-files/{id}", dataFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataFile.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDataFile() throws Exception {
        // Get the dataFile
        restDataFileMockMvc.perform(get("/api/data-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataFile() throws Exception {
        // Initialize the database
        dataFileRepository.saveAndFlush(dataFile);
        int databaseSizeBeforeUpdate = dataFileRepository.findAll().size();

        // Update the dataFile
        DataFile updatedDataFile = dataFileRepository.findOne(dataFile.getId());
        updatedDataFile
            .name(UPDATED_NAME)
            .location(UPDATED_LOCATION);
        DataFileDTO dataFileDTO = dataFileMapper.toDto(updatedDataFile);

        restDataFileMockMvc.perform(put("/api/data-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataFileDTO)))
            .andExpect(status().isOk());

        // Validate the DataFile in the database
        List<DataFile> dataFileList = dataFileRepository.findAll();
        assertThat(dataFileList).hasSize(databaseSizeBeforeUpdate);
        DataFile testDataFile = dataFileList.get(dataFileList.size() - 1);
        assertThat(testDataFile.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataFile.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingDataFile() throws Exception {
        int databaseSizeBeforeUpdate = dataFileRepository.findAll().size();

        // Create the DataFile
        DataFileDTO dataFileDTO = dataFileMapper.toDto(dataFile);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDataFileMockMvc.perform(put("/api/data-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataFileDTO)))
            .andExpect(status().isCreated());

        // Validate the DataFile in the database
        List<DataFile> dataFileList = dataFileRepository.findAll();
        assertThat(dataFileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDataFile() throws Exception {
        // Initialize the database
        dataFileRepository.saveAndFlush(dataFile);
        int databaseSizeBeforeDelete = dataFileRepository.findAll().size();

        // Get the dataFile
        restDataFileMockMvc.perform(delete("/api/data-files/{id}", dataFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DataFile> dataFileList = dataFileRepository.findAll();
        assertThat(dataFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataFile.class);
        DataFile dataFile1 = new DataFile();
        dataFile1.setId(1L);
        DataFile dataFile2 = new DataFile();
        dataFile2.setId(dataFile1.getId());
        assertThat(dataFile1).isEqualTo(dataFile2);
        dataFile2.setId(2L);
        assertThat(dataFile1).isNotEqualTo(dataFile2);
        dataFile1.setId(null);
        assertThat(dataFile1).isNotEqualTo(dataFile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataFileDTO.class);
        DataFileDTO dataFileDTO1 = new DataFileDTO();
        dataFileDTO1.setId(1L);
        DataFileDTO dataFileDTO2 = new DataFileDTO();
        assertThat(dataFileDTO1).isNotEqualTo(dataFileDTO2);
        dataFileDTO2.setId(dataFileDTO1.getId());
        assertThat(dataFileDTO1).isEqualTo(dataFileDTO2);
        dataFileDTO2.setId(2L);
        assertThat(dataFileDTO1).isNotEqualTo(dataFileDTO2);
        dataFileDTO1.setId(null);
        assertThat(dataFileDTO1).isNotEqualTo(dataFileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dataFileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dataFileMapper.fromId(null)).isNull();
    }
}
