package uk.gov.ofwat.external.web.rest;

import uk.gov.ofwat.external.CompanySharingExternalApp;

import uk.gov.ofwat.external.domain.CompanyDataInput;
import uk.gov.ofwat.external.domain.CompanyStatus;
import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.domain.CompanyDataBundle;
import uk.gov.ofwat.external.domain.DataInput;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.repository.CompanyDataInputRepository;
import uk.gov.ofwat.external.service.CompanyDataInputService;
import uk.gov.ofwat.external.service.DataInputService;
import uk.gov.ofwat.external.service.dto.CompanyDataInputDTO;
import uk.gov.ofwat.external.service.mapper.CompanyDataInputMapper;
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
 * Test class for the CompanyDataInputResource REST controller.
 *
 * @see CompanyDataInputResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class CompanyDataInputResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CompanyDataInputRepository companyDataInputRepository;

    @Autowired
    private CompanyDataInputMapper companyDataInputMapper;

    @Autowired
    private CompanyDataInputService companyDataInputService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyDataInputMockMvc;

    private CompanyDataInput companyDataInput;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompanyDataInputResource companyDataInputResource = new CompanyDataInputResource(companyDataInputService);
        this.restCompanyDataInputMockMvc = MockMvcBuilders.standaloneSetup(companyDataInputResource)
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
    public static CompanyDataInput createEntity(EntityManager em) {
        CompanyDataInput companyDataInput = new CompanyDataInput()
            .name(DEFAULT_NAME);
        // Add required entity
        CompanyStatus status = CompanyStatusResourceIntTest.createEntity(em);
        em.persist(status);
        em.flush();
        companyDataInput.setStatus(status);
        // Add required entity
        Company company = CompanyResourceIntTest.createEntity(em);
        em.persist(company);
        em.flush();
        companyDataInput.setCompany(company);
        // Add required entity
        CompanyDataBundle companyDataBundle = CompanyDataBundleResourceIntTest.createEntity(em);
        em.persist(companyDataBundle);
        em.flush();
        companyDataInput.setCompanyDataBundle(companyDataBundle);
        // Add required entity
        DataInput dataInput = DataInputResourceIntTest.createEntity(em);
        em.persist(dataInput);
        em.flush();
        companyDataInput.setDataInput(dataInput);
        // Add required entity
        User companyOwner = UserResourceIntTest.createEntity(em);
        em.persist(companyOwner);
        em.flush();
        companyDataInput.setCompanyOwner(companyOwner);
        // Add required entity
        User companyReviewer = UserResourceIntTest.createEntity(em);
        em.persist(companyReviewer);
        em.flush();
        companyDataInput.setCompanyReviewer(companyReviewer);
        return companyDataInput;
    }

    @Before
    public void initTest() {
        companyDataInput = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyDataInput() throws Exception {
        int databaseSizeBeforeCreate = companyDataInputRepository.findAll().size();

        // Create the CompanyDataInput
        CompanyDataInputDTO companyDataInputDTO = companyDataInputMapper.toDto(companyDataInput);
        restCompanyDataInputMockMvc.perform(post("/api/company-data-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDataInputDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyDataInput in the database
        List<CompanyDataInput> companyDataInputList = companyDataInputRepository.findAll();
        assertThat(companyDataInputList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyDataInput testCompanyDataInput = companyDataInputList.get(companyDataInputList.size() - 1);
        assertThat(testCompanyDataInput.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCompanyDataInputWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyDataInputRepository.findAll().size();

        // Create the CompanyDataInput with an existing ID
        companyDataInput.setId(1L);
        CompanyDataInputDTO companyDataInputDTO = companyDataInputMapper.toDto(companyDataInput);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyDataInputMockMvc.perform(post("/api/company-data-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDataInputDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CompanyDataInput> companyDataInputList = companyDataInputRepository.findAll();
        assertThat(companyDataInputList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyDataInputRepository.findAll().size();
        // set the field null
        companyDataInput.setName(null);

        // Create the CompanyDataInput, which fails.
        CompanyDataInputDTO companyDataInputDTO = companyDataInputMapper.toDto(companyDataInput);

        restCompanyDataInputMockMvc.perform(post("/api/company-data-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDataInputDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyDataInput> companyDataInputList = companyDataInputRepository.findAll();
        assertThat(companyDataInputList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanyDataInputs() throws Exception {
        // Initialize the database
        companyDataInputRepository.saveAndFlush(companyDataInput);

        // Get all the companyDataInputList
        restCompanyDataInputMockMvc.perform(get("/api/company-data-inputs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyDataInput.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCompanyDataInput() throws Exception {
        // Initialize the database
        companyDataInputRepository.saveAndFlush(companyDataInput);

        // Get the companyDataInput
        restCompanyDataInputMockMvc.perform(get("/api/company-data-inputs/{id}", companyDataInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyDataInput.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyDataInput() throws Exception {
        // Get the companyDataInput
        restCompanyDataInputMockMvc.perform(get("/api/company-data-inputs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyDataInput() throws Exception {
        // Initialize the database
        companyDataInputRepository.saveAndFlush(companyDataInput);
        int databaseSizeBeforeUpdate = companyDataInputRepository.findAll().size();

        // Update the companyDataInput
        CompanyDataInput updatedCompanyDataInput = companyDataInputRepository.findOne(companyDataInput.getId());
        updatedCompanyDataInput
            .name(UPDATED_NAME);
        CompanyDataInputDTO companyDataInputDTO = companyDataInputMapper.toDto(updatedCompanyDataInput);

        restCompanyDataInputMockMvc.perform(put("/api/company-data-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDataInputDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyDataInput in the database
        List<CompanyDataInput> companyDataInputList = companyDataInputRepository.findAll();
        assertThat(companyDataInputList).hasSize(databaseSizeBeforeUpdate);
        CompanyDataInput testCompanyDataInput = companyDataInputList.get(companyDataInputList.size() - 1);
        assertThat(testCompanyDataInput.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyDataInput() throws Exception {
        int databaseSizeBeforeUpdate = companyDataInputRepository.findAll().size();

        // Create the CompanyDataInput
        CompanyDataInputDTO companyDataInputDTO = companyDataInputMapper.toDto(companyDataInput);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyDataInputMockMvc.perform(put("/api/company-data-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDataInputDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyDataInput in the database
        List<CompanyDataInput> companyDataInputList = companyDataInputRepository.findAll();
        assertThat(companyDataInputList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyDataInput() throws Exception {
        // Initialize the database
        companyDataInputRepository.saveAndFlush(companyDataInput);
        int databaseSizeBeforeDelete = companyDataInputRepository.findAll().size();

        // Get the companyDataInput
        restCompanyDataInputMockMvc.perform(delete("/api/company-data-inputs/{id}", companyDataInput.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyDataInput> companyDataInputList = companyDataInputRepository.findAll();
        assertThat(companyDataInputList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyDataInput.class);
        CompanyDataInput companyDataInput1 = new CompanyDataInput();
        companyDataInput1.setId(1L);
        CompanyDataInput companyDataInput2 = new CompanyDataInput();
        companyDataInput2.setId(companyDataInput1.getId());
        assertThat(companyDataInput1).isEqualTo(companyDataInput2);
        companyDataInput2.setId(2L);
        assertThat(companyDataInput1).isNotEqualTo(companyDataInput2);
        companyDataInput1.setId(null);
        assertThat(companyDataInput1).isNotEqualTo(companyDataInput2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyDataInputDTO.class);
        CompanyDataInputDTO companyDataInputDTO1 = new CompanyDataInputDTO();
        companyDataInputDTO1.setId(1L);
        CompanyDataInputDTO companyDataInputDTO2 = new CompanyDataInputDTO();
        assertThat(companyDataInputDTO1).isNotEqualTo(companyDataInputDTO2);
        companyDataInputDTO2.setId(companyDataInputDTO1.getId());
        assertThat(companyDataInputDTO1).isEqualTo(companyDataInputDTO2);
        companyDataInputDTO2.setId(2L);
        assertThat(companyDataInputDTO1).isNotEqualTo(companyDataInputDTO2);
        companyDataInputDTO1.setId(null);
        assertThat(companyDataInputDTO1).isNotEqualTo(companyDataInputDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(companyDataInputMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(companyDataInputMapper.fromId(null)).isNull();
    }
}
