package uk.gov.ofwat.external.web.rest;

import uk.gov.ofwat.external.CompanySharingExternalApp;

import uk.gov.ofwat.external.domain.InputType;
import uk.gov.ofwat.external.repository.InputTypeRepository;
import uk.gov.ofwat.external.service.InputTypeService;
import uk.gov.ofwat.external.service.dto.InputTypeDTO;
import uk.gov.ofwat.external.service.mapper.InputTypeMapper;
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
 * Test class for the InputTypeResource REST controller.
 *
 * @see InputTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class InputTypeResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private InputTypeRepository inputTypeRepository;

    @Autowired
    private InputTypeMapper inputTypeMapper;

    @Autowired
    private InputTypeService inputTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInputTypeMockMvc;

    private InputType inputType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InputTypeResource inputTypeResource = new InputTypeResource(inputTypeService);
        this.restInputTypeMockMvc = MockMvcBuilders.standaloneSetup(inputTypeResource)
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
    public static InputType createEntity(EntityManager em) {
        InputType inputType = new InputType()
            .type(DEFAULT_TYPE);
        return inputType;
    }

    @Before
    public void initTest() {
        inputType = createEntity(em);
    }

    @Test
    @Transactional
    public void createInputType() throws Exception {
        int databaseSizeBeforeCreate = inputTypeRepository.findAll().size();

        // Create the InputType
        InputTypeDTO inputTypeDTO = inputTypeMapper.toDto(inputType);
        restInputTypeMockMvc.perform(post("/api/input-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inputTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the InputType in the database
        List<InputType> inputTypeList = inputTypeRepository.findAll();
        assertThat(inputTypeList).hasSize(databaseSizeBeforeCreate + 1);
        InputType testInputType = inputTypeList.get(inputTypeList.size() - 1);
        assertThat(testInputType.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createInputTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inputTypeRepository.findAll().size();

        // Create the InputType with an existing ID
        inputType.setId(1L);
        InputTypeDTO inputTypeDTO = inputTypeMapper.toDto(inputType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInputTypeMockMvc.perform(post("/api/input-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inputTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<InputType> inputTypeList = inputTypeRepository.findAll();
        assertThat(inputTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInputTypes() throws Exception {
        // Initialize the database
        inputTypeRepository.saveAndFlush(inputType);

        // Get all the inputTypeList
        restInputTypeMockMvc.perform(get("/api/input-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inputType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getInputType() throws Exception {
        // Initialize the database
        inputTypeRepository.saveAndFlush(inputType);

        // Get the inputType
        restInputTypeMockMvc.perform(get("/api/input-types/{id}", inputType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inputType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInputType() throws Exception {
        // Get the inputType
        restInputTypeMockMvc.perform(get("/api/input-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInputType() throws Exception {
        // Initialize the database
        inputTypeRepository.saveAndFlush(inputType);
        int databaseSizeBeforeUpdate = inputTypeRepository.findAll().size();

        // Update the inputType
        InputType updatedInputType = inputTypeRepository.findOne(inputType.getId());
        updatedInputType
            .type(UPDATED_TYPE);
        InputTypeDTO inputTypeDTO = inputTypeMapper.toDto(updatedInputType);

        restInputTypeMockMvc.perform(put("/api/input-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inputTypeDTO)))
            .andExpect(status().isOk());

        // Validate the InputType in the database
        List<InputType> inputTypeList = inputTypeRepository.findAll();
        assertThat(inputTypeList).hasSize(databaseSizeBeforeUpdate);
        InputType testInputType = inputTypeList.get(inputTypeList.size() - 1);
        assertThat(testInputType.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingInputType() throws Exception {
        int databaseSizeBeforeUpdate = inputTypeRepository.findAll().size();

        // Create the InputType
        InputTypeDTO inputTypeDTO = inputTypeMapper.toDto(inputType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInputTypeMockMvc.perform(put("/api/input-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inputTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the InputType in the database
        List<InputType> inputTypeList = inputTypeRepository.findAll();
        assertThat(inputTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInputType() throws Exception {
        // Initialize the database
        inputTypeRepository.saveAndFlush(inputType);
        int databaseSizeBeforeDelete = inputTypeRepository.findAll().size();

        // Get the inputType
        restInputTypeMockMvc.perform(delete("/api/input-types/{id}", inputType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<InputType> inputTypeList = inputTypeRepository.findAll();
        assertThat(inputTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InputType.class);
        InputType inputType1 = new InputType();
        inputType1.setId(1L);
        InputType inputType2 = new InputType();
        inputType2.setId(inputType1.getId());
        assertThat(inputType1).isEqualTo(inputType2);
        inputType2.setId(2L);
        assertThat(inputType1).isNotEqualTo(inputType2);
        inputType1.setId(null);
        assertThat(inputType1).isNotEqualTo(inputType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InputTypeDTO.class);
        InputTypeDTO inputTypeDTO1 = new InputTypeDTO();
        inputTypeDTO1.setId(1L);
        InputTypeDTO inputTypeDTO2 = new InputTypeDTO();
        assertThat(inputTypeDTO1).isNotEqualTo(inputTypeDTO2);
        inputTypeDTO2.setId(inputTypeDTO1.getId());
        assertThat(inputTypeDTO1).isEqualTo(inputTypeDTO2);
        inputTypeDTO2.setId(2L);
        assertThat(inputTypeDTO1).isNotEqualTo(inputTypeDTO2);
        inputTypeDTO1.setId(null);
        assertThat(inputTypeDTO1).isNotEqualTo(inputTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(inputTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(inputTypeMapper.fromId(null)).isNull();
    }
}
