package uk.gov.ofwat.external.web.rest;

import uk.gov.ofwat.external.CompanySharingExternalApp;

import uk.gov.ofwat.external.domain.DataInput;
import uk.gov.ofwat.external.domain.PublishingStatus;
import uk.gov.ofwat.external.domain.DataBundle;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.repository.DataInputRepository;
import uk.gov.ofwat.external.repository.PublishingStatusRepository;
import uk.gov.ofwat.external.service.DataInputService;
import uk.gov.ofwat.external.service.ExcelReaderService;
import uk.gov.ofwat.external.service.PublishingStateTransformationService;
import uk.gov.ofwat.external.service.dto.DataInputDTO;
import uk.gov.ofwat.external.service.mapper.DataInputMapper;
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
 * Test class for the DataInputResource REST controller.
 *
 * @see DataInputResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class DataInputResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_GUIDANCE = "AAAAAAAAAA";
    private static final String UPDATED_GUIDANCE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DEFAULT_DEADLINE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEFAULT_DEADLINE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_ORDER_INDEX = 1L;
    private static final Long UPDATED_ORDER_INDEX = 2L;

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_FILE_LOCATION = "BBBBBBBBBB";

    @Autowired
    private DataInputRepository dataInputRepository;

    @Autowired
    private DataInputMapper dataInputMapper;

    @Autowired
    ExcelReaderService excelReaderService;

    @Autowired
    private DataInputService dataInputService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private PublishingStatusRepository publishingStatusRepository;

    @Autowired
    private EntityManager em;

    private MockMvc restDataInputMockMvc;

    private DataInput dataInput;

    private PublishingStateTransformationService publishingStateTransformationService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DataInputResource dataInputResource = new DataInputResource(dataInputService, publishingStatusRepository, publishingStateTransformationService, excelReaderService);
        this.restDataInputMockMvc = MockMvcBuilders.standaloneSetup(dataInputResource)
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
    public static DataInput createEntity(EntityManager em) {
        DataInput dataInput = new DataInput()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .guidance(DEFAULT_GUIDANCE)
            .defaultDeadline(DEFAULT_DEFAULT_DEADLINE)
            .orderIndex(DEFAULT_ORDER_INDEX)
            .fileName(DEFAULT_FILE_NAME)
            .fileLocation(DEFAULT_FILE_LOCATION);
        // Add required entity
        PublishingStatus status = PublishingStatusResourceIntTest.createEntity(em);
        em.persist(status);
        em.flush();
        dataInput.setStatus(status);
        // Add required entity
        DataBundle dataBundle = DataBundleResourceIntTest.createEntity(em);
        em.persist(dataBundle);
        em.flush();
        dataInput.setDataBundle(dataBundle);
        // Add required entity
        User owner = UserResourceIntTest.createEntity(em);
        em.persist(owner);
        em.flush();
        dataInput.setOwner(owner);
        // Add required entity
        User reviewer = UserResourceIntTest.createEntity(em);
        em.persist(reviewer);
        em.flush();
        dataInput.setReviewer(reviewer);
        return dataInput;
    }

    @Before
    public void initTest() {
        dataInput = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataInput() throws Exception {
        int databaseSizeBeforeCreate = dataInputRepository.findAll().size();

        // Create the DataInput
        DataInputDTO dataInputDTO = dataInputMapper.toDto(dataInput);
        restDataInputMockMvc.perform(post("/api/data-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataInputDTO)))
            .andExpect(status().isCreated());

        // Validate the DataInput in the database
        List<DataInput> dataInputList = dataInputRepository.findAll();
        assertThat(dataInputList).hasSize(databaseSizeBeforeCreate + 1);
        DataInput testDataInput = dataInputList.get(dataInputList.size() - 1);
        assertThat(testDataInput.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataInput.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDataInput.getGuidance()).isEqualTo(DEFAULT_GUIDANCE);
        assertThat(testDataInput.getDefaultDeadline()).isEqualTo(DEFAULT_DEFAULT_DEADLINE);
        assertThat(testDataInput.getOrderIndex()).isEqualTo(DEFAULT_ORDER_INDEX);
        assertThat(testDataInput.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testDataInput.getFileLocation()).isEqualTo(DEFAULT_FILE_LOCATION);
    }

    @Test
    @Transactional
    public void createDataInputWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataInputRepository.findAll().size();

        // Create the DataInput with an existing ID
        dataInput.setId(1L);
        DataInputDTO dataInputDTO = dataInputMapper.toDto(dataInput);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataInputMockMvc.perform(post("/api/data-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataInputDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DataInput> dataInputList = dataInputRepository.findAll();
        assertThat(dataInputList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataInputRepository.findAll().size();
        // set the field null
        dataInput.setName(null);

        // Create the DataInput, which fails.
        DataInputDTO dataInputDTO = dataInputMapper.toDto(dataInput);

        restDataInputMockMvc.perform(post("/api/data-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataInputDTO)))
            .andExpect(status().isBadRequest());

        List<DataInput> dataInputList = dataInputRepository.findAll();
        assertThat(dataInputList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderIndexIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataInputRepository.findAll().size();
        // set the field null
        dataInput.setOrderIndex(null);

        // Create the DataInput, which fails.
        DataInputDTO dataInputDTO = dataInputMapper.toDto(dataInput);

        restDataInputMockMvc.perform(post("/api/data-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataInputDTO)))
            .andExpect(status().isBadRequest());

        List<DataInput> dataInputList = dataInputRepository.findAll();
        assertThat(dataInputList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataInputRepository.findAll().size();
        // set the field null
        dataInput.setFileName(null);

        // Create the DataInput, which fails.
        DataInputDTO dataInputDTO = dataInputMapper.toDto(dataInput);

        restDataInputMockMvc.perform(post("/api/data-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataInputDTO)))
            .andExpect(status().isBadRequest());

        List<DataInput> dataInputList = dataInputRepository.findAll();
        assertThat(dataInputList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataInputRepository.findAll().size();
        // set the field null
        dataInput.setFileLocation(null);

        // Create the DataInput, which fails.
        DataInputDTO dataInputDTO = dataInputMapper.toDto(dataInput);

        restDataInputMockMvc.perform(post("/api/data-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataInputDTO)))
            .andExpect(status().isBadRequest());

        List<DataInput> dataInputList = dataInputRepository.findAll();
        assertThat(dataInputList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDataInputs() throws Exception {
        // Initialize the database
        dataInputRepository.saveAndFlush(dataInput);

        // Get all the dataInputList
        restDataInputMockMvc.perform(get("/api/data-inputs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataInput.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].guidance").value(hasItem(DEFAULT_GUIDANCE.toString())))
            .andExpect(jsonPath("$.[*].defaultDeadline").value(hasItem(DEFAULT_DEFAULT_DEADLINE.toString())))
            .andExpect(jsonPath("$.[*].orderIndex").value(hasItem(DEFAULT_ORDER_INDEX.intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileLocation").value(hasItem(DEFAULT_FILE_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void getDataInput() throws Exception {
        // Initialize the database
        dataInputRepository.saveAndFlush(dataInput);

        // Get the dataInput
        restDataInputMockMvc.perform(get("/api/data-inputs/{id}", dataInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataInput.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.guidance").value(DEFAULT_GUIDANCE.toString()))
            .andExpect(jsonPath("$.defaultDeadline").value(DEFAULT_DEFAULT_DEADLINE.toString()))
            .andExpect(jsonPath("$.orderIndex").value(DEFAULT_ORDER_INDEX.intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.fileLocation").value(DEFAULT_FILE_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDataInput() throws Exception {
        // Get the dataInput
        restDataInputMockMvc.perform(get("/api/data-inputs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataInput() throws Exception {
        // Initialize the database
        dataInputRepository.saveAndFlush(dataInput);
        int databaseSizeBeforeUpdate = dataInputRepository.findAll().size();

        // Update the dataInput
        DataInput updatedDataInput = dataInputRepository.findOne(dataInput.getId());
        updatedDataInput
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .guidance(UPDATED_GUIDANCE)
            .defaultDeadline(UPDATED_DEFAULT_DEADLINE)
            .orderIndex(UPDATED_ORDER_INDEX)
            .fileName(UPDATED_FILE_NAME)
            .fileLocation(UPDATED_FILE_LOCATION);
        DataInputDTO dataInputDTO = dataInputMapper.toDto(updatedDataInput);

        restDataInputMockMvc.perform(put("/api/data-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataInputDTO)))
            .andExpect(status().isOk());

        // Validate the DataInput in the database
        List<DataInput> dataInputList = dataInputRepository.findAll();
        assertThat(dataInputList).hasSize(databaseSizeBeforeUpdate);
        DataInput testDataInput = dataInputList.get(dataInputList.size() - 1);
        assertThat(testDataInput.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataInput.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDataInput.getGuidance()).isEqualTo(UPDATED_GUIDANCE);
        assertThat(testDataInput.getDefaultDeadline()).isEqualTo(UPDATED_DEFAULT_DEADLINE);
        assertThat(testDataInput.getOrderIndex()).isEqualTo(UPDATED_ORDER_INDEX);
        assertThat(testDataInput.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testDataInput.getFileLocation()).isEqualTo(UPDATED_FILE_LOCATION);
    }

    @Test
    @Transactional
    public void updateNonExistingDataInput() throws Exception {
        int databaseSizeBeforeUpdate = dataInputRepository.findAll().size();

        // Create the DataInput
        DataInputDTO dataInputDTO = dataInputMapper.toDto(dataInput);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDataInputMockMvc.perform(put("/api/data-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataInputDTO)))
            .andExpect(status().isCreated());

        // Validate the DataInput in the database
        List<DataInput> dataInputList = dataInputRepository.findAll();
        assertThat(dataInputList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDataInput() throws Exception {
        // Initialize the database
        dataInputRepository.saveAndFlush(dataInput);
        int databaseSizeBeforeDelete = dataInputRepository.findAll().size();

        // Get the dataInput
        restDataInputMockMvc.perform(delete("/api/data-inputs/{id}", dataInput.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DataInput> dataInputList = dataInputRepository.findAll();
        assertThat(dataInputList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataInput.class);
        DataInput dataInput1 = new DataInput();
        dataInput1.setId(1L);
        DataInput dataInput2 = new DataInput();
        dataInput2.setId(dataInput1.getId());
        assertThat(dataInput1).isEqualTo(dataInput2);
        dataInput2.setId(2L);
        assertThat(dataInput1).isNotEqualTo(dataInput2);
        dataInput1.setId(null);
        assertThat(dataInput1).isNotEqualTo(dataInput2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataInputDTO.class);
        DataInputDTO dataInputDTO1 = new DataInputDTO();
        dataInputDTO1.setId(1L);
        DataInputDTO dataInputDTO2 = new DataInputDTO();
        assertThat(dataInputDTO1).isNotEqualTo(dataInputDTO2);
        dataInputDTO2.setId(dataInputDTO1.getId());
        assertThat(dataInputDTO1).isEqualTo(dataInputDTO2);
        dataInputDTO2.setId(2L);
        assertThat(dataInputDTO1).isNotEqualTo(dataInputDTO2);
        dataInputDTO1.setId(null);
        assertThat(dataInputDTO1).isNotEqualTo(dataInputDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dataInputMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dataInputMapper.fromId(null)).isNull();
    }
}
