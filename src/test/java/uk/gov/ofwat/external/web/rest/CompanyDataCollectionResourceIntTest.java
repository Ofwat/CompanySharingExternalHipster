package uk.gov.ofwat.external.web.rest;

import uk.gov.ofwat.external.CompanySharingExternalApp;

import uk.gov.ofwat.external.domain.CompanyDataCollection;
import uk.gov.ofwat.external.domain.CompanyStatus;
import uk.gov.ofwat.external.domain.Company;
import uk.gov.ofwat.external.domain.DataCollection;
import uk.gov.ofwat.external.repository.CompanyDataCollectionRepository;
import uk.gov.ofwat.external.service.CompanyDataCollectionService;
import uk.gov.ofwat.external.service.dto.CompanyDataCollectionDTO;
import uk.gov.ofwat.external.service.mapper.CompanyDataCollectionMapper;
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
 * Test class for the CompanyDataCollectionResource REST controller.
 *
 * @see CompanyDataCollectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class CompanyDataCollectionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CompanyDataCollectionRepository companyDataCollectionRepository;

    @Autowired
    private CompanyDataCollectionMapper companyDataCollectionMapper;

    @Autowired
    private CompanyDataCollectionService companyDataCollectionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyDataCollectionMockMvc;

    private CompanyDataCollection companyDataCollection;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompanyDataCollectionResource companyDataCollectionResource = new CompanyDataCollectionResource(companyDataCollectionService);
        this.restCompanyDataCollectionMockMvc = MockMvcBuilders.standaloneSetup(companyDataCollectionResource)
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
    public static CompanyDataCollection createEntity(EntityManager em) {
        CompanyDataCollection companyDataCollection = new CompanyDataCollection()
            .name(DEFAULT_NAME);
        // Add required entity
        CompanyStatus status = CompanyStatusResourceIntTest.createEntity(em);
        em.persist(status);
        em.flush();
        companyDataCollection.setStatus(status);
        // Add required entity
        Company company = CompanyResourceIntTest.createEntity(em);
        em.persist(company);
        em.flush();
        companyDataCollection.setCompany(company);
        // Add required entity
        DataCollection dataCollection = DataCollectionResourceIntTest.createEntity(em);
        em.persist(dataCollection);
        em.flush();
        companyDataCollection.setDataCollection(dataCollection);
        return companyDataCollection;
    }

    @Before
    public void initTest() {
        companyDataCollection = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyDataCollection() throws Exception {
        int databaseSizeBeforeCreate = companyDataCollectionRepository.findAll().size();

        // Create the CompanyDataCollection
        CompanyDataCollectionDTO companyDataCollectionDTO = companyDataCollectionMapper.toDto(companyDataCollection);
        restCompanyDataCollectionMockMvc.perform(post("/api/company-data-collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDataCollectionDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyDataCollection in the database
        List<CompanyDataCollection> companyDataCollectionList = companyDataCollectionRepository.findAll();
        assertThat(companyDataCollectionList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyDataCollection testCompanyDataCollection = companyDataCollectionList.get(companyDataCollectionList.size() - 1);
        assertThat(testCompanyDataCollection.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCompanyDataCollectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyDataCollectionRepository.findAll().size();

        // Create the CompanyDataCollection with an existing ID
        companyDataCollection.setId(1L);
        CompanyDataCollectionDTO companyDataCollectionDTO = companyDataCollectionMapper.toDto(companyDataCollection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyDataCollectionMockMvc.perform(post("/api/company-data-collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDataCollectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CompanyDataCollection> companyDataCollectionList = companyDataCollectionRepository.findAll();
        assertThat(companyDataCollectionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyDataCollectionRepository.findAll().size();
        // set the field null
        companyDataCollection.setName(null);

        // Create the CompanyDataCollection, which fails.
        CompanyDataCollectionDTO companyDataCollectionDTO = companyDataCollectionMapper.toDto(companyDataCollection);

        restCompanyDataCollectionMockMvc.perform(post("/api/company-data-collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDataCollectionDTO)))
            .andExpect(status().isBadRequest());

        List<CompanyDataCollection> companyDataCollectionList = companyDataCollectionRepository.findAll();
        assertThat(companyDataCollectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanyDataCollections() throws Exception {
        // Initialize the database
        companyDataCollectionRepository.saveAndFlush(companyDataCollection);

        // Get all the companyDataCollectionList
        restCompanyDataCollectionMockMvc.perform(get("/api/company-data-collections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyDataCollection.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCompanyDataCollection() throws Exception {
        // Initialize the database
        companyDataCollectionRepository.saveAndFlush(companyDataCollection);

        // Get the companyDataCollection
        restCompanyDataCollectionMockMvc.perform(get("/api/company-data-collections/{id}", companyDataCollection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyDataCollection.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyDataCollection() throws Exception {
        // Get the companyDataCollection
        restCompanyDataCollectionMockMvc.perform(get("/api/company-data-collections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyDataCollection() throws Exception {
        // Initialize the database
        companyDataCollectionRepository.saveAndFlush(companyDataCollection);
        int databaseSizeBeforeUpdate = companyDataCollectionRepository.findAll().size();

        // Update the companyDataCollection
        CompanyDataCollection updatedCompanyDataCollection = companyDataCollectionRepository.findOne(companyDataCollection.getId());
        updatedCompanyDataCollection
            .name(UPDATED_NAME);
        CompanyDataCollectionDTO companyDataCollectionDTO = companyDataCollectionMapper.toDto(updatedCompanyDataCollection);

        restCompanyDataCollectionMockMvc.perform(put("/api/company-data-collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDataCollectionDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyDataCollection in the database
        List<CompanyDataCollection> companyDataCollectionList = companyDataCollectionRepository.findAll();
        assertThat(companyDataCollectionList).hasSize(databaseSizeBeforeUpdate);
        CompanyDataCollection testCompanyDataCollection = companyDataCollectionList.get(companyDataCollectionList.size() - 1);
        assertThat(testCompanyDataCollection.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyDataCollection() throws Exception {
        int databaseSizeBeforeUpdate = companyDataCollectionRepository.findAll().size();

        // Create the CompanyDataCollection
        CompanyDataCollectionDTO companyDataCollectionDTO = companyDataCollectionMapper.toDto(companyDataCollection);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyDataCollectionMockMvc.perform(put("/api/company-data-collections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyDataCollectionDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyDataCollection in the database
        List<CompanyDataCollection> companyDataCollectionList = companyDataCollectionRepository.findAll();
        assertThat(companyDataCollectionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyDataCollection() throws Exception {
        // Initialize the database
        companyDataCollectionRepository.saveAndFlush(companyDataCollection);
        int databaseSizeBeforeDelete = companyDataCollectionRepository.findAll().size();

        // Get the companyDataCollection
        restCompanyDataCollectionMockMvc.perform(delete("/api/company-data-collections/{id}", companyDataCollection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyDataCollection> companyDataCollectionList = companyDataCollectionRepository.findAll();
        assertThat(companyDataCollectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyDataCollection.class);
        CompanyDataCollection companyDataCollection1 = new CompanyDataCollection();
        companyDataCollection1.setId(1L);
        CompanyDataCollection companyDataCollection2 = new CompanyDataCollection();
        companyDataCollection2.setId(companyDataCollection1.getId());
        assertThat(companyDataCollection1).isEqualTo(companyDataCollection2);
        companyDataCollection2.setId(2L);
        assertThat(companyDataCollection1).isNotEqualTo(companyDataCollection2);
        companyDataCollection1.setId(null);
        assertThat(companyDataCollection1).isNotEqualTo(companyDataCollection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyDataCollectionDTO.class);
        CompanyDataCollectionDTO companyDataCollectionDTO1 = new CompanyDataCollectionDTO();
        companyDataCollectionDTO1.setId(1L);
        CompanyDataCollectionDTO companyDataCollectionDTO2 = new CompanyDataCollectionDTO();
        assertThat(companyDataCollectionDTO1).isNotEqualTo(companyDataCollectionDTO2);
        companyDataCollectionDTO2.setId(companyDataCollectionDTO1.getId());
        assertThat(companyDataCollectionDTO1).isEqualTo(companyDataCollectionDTO2);
        companyDataCollectionDTO2.setId(2L);
        assertThat(companyDataCollectionDTO1).isNotEqualTo(companyDataCollectionDTO2);
        companyDataCollectionDTO1.setId(null);
        assertThat(companyDataCollectionDTO1).isNotEqualTo(companyDataCollectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(companyDataCollectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(companyDataCollectionMapper.fromId(null)).isNull();
    }
}
