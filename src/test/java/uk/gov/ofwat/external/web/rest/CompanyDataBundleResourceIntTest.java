package uk.gov.ofwat.external.web.rest;

import uk.gov.ofwat.external.CompanySharingExternalApp;

import uk.gov.ofwat.external.domain.CompanyDataBundle;
import uk.gov.ofwat.external.domain.CompanyStatus;
import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.domain.CompanyDataCollection;
import uk.gov.ofwat.external.domain.DataBundle;
import uk.gov.ofwat.external.repository.CompanyDataBundleRepository;
import uk.gov.ofwat.external.service.CompanyDataBundleService;
import uk.gov.ofwat.external.service.dto.CompanyDataBundleDTO;
import uk.gov.ofwat.external.service.mapper.CompanyDataBundleMapper;
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
 * Test class for the CompanyDataBundleResource REST controller.
 *
 * @see CompanyDataBundleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class CompanyDataBundleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COMPANY_DEADLINE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMPANY_DEADLINE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CompanyDataBundleRepository companyDataBundleRepository;

    @Autowired
    private CompanyDataBundleMapper companyDataBundleMapper;

    @Autowired
    private CompanyDataBundleService companyDataBundleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyDataBundleMockMvc;

    private CompanyDataBundle companyDataBundle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompanyDataBundleResource companyDataBundleResource = new CompanyDataBundleResource(companyDataBundleService);
        this.restCompanyDataBundleMockMvc = MockMvcBuilders.standaloneSetup(companyDataBundleResource)
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
    public static CompanyDataBundle createEntity(EntityManager em) {
        CompanyDataBundle companyDataBundle = new CompanyDataBundle()
            .name(DEFAULT_NAME)
            .companyDeadline(DEFAULT_COMPANY_DEADLINE);
        // Add required entity
        CompanyStatus status = CompanyStatusResourceIntTest.createEntity(em);
        em.persist(status);
        em.flush();
        companyDataBundle.setStatus(status);
        // Add required entity
        Company company = CompanyResourceIntTest.createEntity(em);
        em.persist(company);
        em.flush();
        companyDataBundle.setCompany(company);
        // Add required entity
        CompanyDataCollection companyDataCollection = CompanyDataCollectionResourceIntTest.createEntity(em);
        em.persist(companyDataCollection);
        em.flush();
        companyDataBundle.setCompanyDataCollection(companyDataCollection);
        // Add required entity
        DataBundle dataBundle = DataBundleResourceIntTest.createEntity(em);
        em.persist(dataBundle);
        em.flush();
        companyDataBundle.setDataBundle(dataBundle);
        return companyDataBundle;
    }

    @Before
    public void initTest() {
        companyDataBundle = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyDataBundle() throws Exception {
        int databaseSizeBeforeCreate = companyDataBundleRepository.findAll().size();

        // Create the CompanyDataBundle
        CompanyDataBundleDTO companyDataBundleDTO = companyDataBundleMapper.toDto(companyDataBundle);
        restCompanyDataBundleMockMvc.perform(post("/api/company-data-bundles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDataBundleDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyDataBundle in the database
        List<CompanyDataBundle> companyDataBundleList = companyDataBundleRepository.findAll();
        assertThat(companyDataBundleList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyDataBundle testCompanyDataBundle = companyDataBundleList.get(companyDataBundleList.size() - 1);
        assertThat(testCompanyDataBundle.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyDataBundle.getCompanyDeadline()).isEqualTo(DEFAULT_COMPANY_DEADLINE);
    }

    @Test
    @Transactional
    public void createCompanyDataBundleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyDataBundleRepository.findAll().size();

        // Create the CompanyDataBundle with an existing ID
        companyDataBundle.setId(1L);
        CompanyDataBundleDTO companyDataBundleDTO = companyDataBundleMapper.toDto(companyDataBundle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyDataBundleMockMvc.perform(post("/api/company-data-bundles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDataBundleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CompanyDataBundle> companyDataBundleList = companyDataBundleRepository.findAll();
        assertThat(companyDataBundleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyDataBundleRepository.findAll().size();
        // set the field null
        companyDataBundle.setName(null);

        // Create the CompanyDataBundle, which fails.
        CompanyDataBundleDTO companyDataBundleDTO = companyDataBundleMapper.toDto(companyDataBundle);

        restCompanyDataBundleMockMvc.perform(post("/api/company-data-bundles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDataBundleDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyDataBundle> companyDataBundleList = companyDataBundleRepository.findAll();
        assertThat(companyDataBundleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanyDataBundles() throws Exception {
        // Initialize the database
        companyDataBundleRepository.saveAndFlush(companyDataBundle);

        // Get all the companyDataBundleList
        restCompanyDataBundleMockMvc.perform(get("/api/company-data-bundles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyDataBundle.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].companyDeadline").value(hasItem(DEFAULT_COMPANY_DEADLINE.toString())));
    }

    @Test
    @Transactional
    public void getCompanyDataBundle() throws Exception {
        // Initialize the database
        companyDataBundleRepository.saveAndFlush(companyDataBundle);

        // Get the companyDataBundle
        restCompanyDataBundleMockMvc.perform(get("/api/company-data-bundles/{id}", companyDataBundle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyDataBundle.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.companyDeadline").value(DEFAULT_COMPANY_DEADLINE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyDataBundle() throws Exception {
        // Get the companyDataBundle
        restCompanyDataBundleMockMvc.perform(get("/api/company-data-bundles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyDataBundle() throws Exception {
        // Initialize the database
        companyDataBundleRepository.saveAndFlush(companyDataBundle);
        int databaseSizeBeforeUpdate = companyDataBundleRepository.findAll().size();

        // Update the companyDataBundle
        CompanyDataBundle updatedCompanyDataBundle = companyDataBundleRepository.findOne(companyDataBundle.getId());
        updatedCompanyDataBundle
            .name(UPDATED_NAME)
            .companyDeadline(UPDATED_COMPANY_DEADLINE);
        CompanyDataBundleDTO companyDataBundleDTO = companyDataBundleMapper.toDto(updatedCompanyDataBundle);

        restCompanyDataBundleMockMvc.perform(put("/api/company-data-bundles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDataBundleDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyDataBundle in the database
        List<CompanyDataBundle> companyDataBundleList = companyDataBundleRepository.findAll();
        assertThat(companyDataBundleList).hasSize(databaseSizeBeforeUpdate);
        CompanyDataBundle testCompanyDataBundle = companyDataBundleList.get(companyDataBundleList.size() - 1);
        assertThat(testCompanyDataBundle.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyDataBundle.getCompanyDeadline()).isEqualTo(UPDATED_COMPANY_DEADLINE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyDataBundle() throws Exception {
        int databaseSizeBeforeUpdate = companyDataBundleRepository.findAll().size();

        // Create the CompanyDataBundle
        CompanyDataBundleDTO companyDataBundleDTO = companyDataBundleMapper.toDto(companyDataBundle);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyDataBundleMockMvc.perform(put("/api/company-data-bundles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDataBundleDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyDataBundle in the database
        List<CompanyDataBundle> companyDataBundleList = companyDataBundleRepository.findAll();
        assertThat(companyDataBundleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyDataBundle() throws Exception {
        // Initialize the database
        companyDataBundleRepository.saveAndFlush(companyDataBundle);
        int databaseSizeBeforeDelete = companyDataBundleRepository.findAll().size();

        // Get the companyDataBundle
        restCompanyDataBundleMockMvc.perform(delete("/api/company-data-bundles/{id}", companyDataBundle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyDataBundle> companyDataBundleList = companyDataBundleRepository.findAll();
        assertThat(companyDataBundleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyDataBundle.class);
        CompanyDataBundle companyDataBundle1 = new CompanyDataBundle();
        companyDataBundle1.setId(1L);
        CompanyDataBundle companyDataBundle2 = new CompanyDataBundle();
        companyDataBundle2.setId(companyDataBundle1.getId());
        assertThat(companyDataBundle1).isEqualTo(companyDataBundle2);
        companyDataBundle2.setId(2L);
        assertThat(companyDataBundle1).isNotEqualTo(companyDataBundle2);
        companyDataBundle1.setId(null);
        assertThat(companyDataBundle1).isNotEqualTo(companyDataBundle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyDataBundleDTO.class);
        CompanyDataBundleDTO companyDataBundleDTO1 = new CompanyDataBundleDTO();
        companyDataBundleDTO1.setId(1L);
        CompanyDataBundleDTO companyDataBundleDTO2 = new CompanyDataBundleDTO();
        assertThat(companyDataBundleDTO1).isNotEqualTo(companyDataBundleDTO2);
        companyDataBundleDTO2.setId(companyDataBundleDTO1.getId());
        assertThat(companyDataBundleDTO1).isEqualTo(companyDataBundleDTO2);
        companyDataBundleDTO2.setId(2L);
        assertThat(companyDataBundleDTO1).isNotEqualTo(companyDataBundleDTO2);
        companyDataBundleDTO1.setId(null);
        assertThat(companyDataBundleDTO1).isNotEqualTo(companyDataBundleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(companyDataBundleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(companyDataBundleMapper.fromId(null)).isNull();
    }
}
