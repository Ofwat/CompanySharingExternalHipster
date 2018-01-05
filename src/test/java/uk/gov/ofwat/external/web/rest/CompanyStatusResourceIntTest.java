package uk.gov.ofwat.external.web.rest;

import uk.gov.ofwat.external.CompanySharingExternalApp;

import uk.gov.ofwat.external.domain.CompanyStatus;
import uk.gov.ofwat.external.repository.CompanyStatusRepository;
import uk.gov.ofwat.external.service.CompanyStatusService;
import uk.gov.ofwat.external.service.dto.CompanyStatusDTO;
import uk.gov.ofwat.external.service.mapper.CompanyStatusMapper;
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
 * Test class for the CompanyStatusResource REST controller.
 *
 * @see CompanyStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class CompanyStatusResourceIntTest {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private CompanyStatusRepository companyStatusRepository;

    @Autowired
    private CompanyStatusMapper companyStatusMapper;

    @Autowired
    private CompanyStatusService companyStatusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyStatusMockMvc;

    private CompanyStatus companyStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CompanyStatusResource companyStatusResource = new CompanyStatusResource(companyStatusService);
        this.restCompanyStatusMockMvc = MockMvcBuilders.standaloneSetup(companyStatusResource)
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
    public static CompanyStatus createEntity(EntityManager em) {
        CompanyStatus companyStatus = new CompanyStatus()
            .status(DEFAULT_STATUS);
        return companyStatus;
    }

    @Before
    public void initTest() {
        companyStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyStatus() throws Exception {
        int databaseSizeBeforeCreate = companyStatusRepository.findAll().size();

        // Create the CompanyStatus
        CompanyStatusDTO companyStatusDTO = companyStatusMapper.toDto(companyStatus);
        restCompanyStatusMockMvc.perform(post("/api/company-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyStatus in the database
        List<CompanyStatus> companyStatusList = companyStatusRepository.findAll();
        assertThat(companyStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyStatus testCompanyStatus = companyStatusList.get(companyStatusList.size() - 1);
        assertThat(testCompanyStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createCompanyStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyStatusRepository.findAll().size();

        // Create the CompanyStatus with an existing ID
        companyStatus.setId(1L);
        CompanyStatusDTO companyStatusDTO = companyStatusMapper.toDto(companyStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyStatusMockMvc.perform(post("/api/company-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CompanyStatus> companyStatusList = companyStatusRepository.findAll();
        assertThat(companyStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyStatuses() throws Exception {
        // Initialize the database
        companyStatusRepository.saveAndFlush(companyStatus);

        // Get all the companyStatusList
        restCompanyStatusMockMvc.perform(get("/api/company-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getCompanyStatus() throws Exception {
        // Initialize the database
        companyStatusRepository.saveAndFlush(companyStatus);

        // Get the companyStatus
        restCompanyStatusMockMvc.perform(get("/api/company-statuses/{id}", companyStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyStatus.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyStatus() throws Exception {
        // Get the companyStatus
        restCompanyStatusMockMvc.perform(get("/api/company-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyStatus() throws Exception {
        // Initialize the database
        companyStatusRepository.saveAndFlush(companyStatus);
        int databaseSizeBeforeUpdate = companyStatusRepository.findAll().size();

        // Update the companyStatus
        CompanyStatus updatedCompanyStatus = companyStatusRepository.findOne(companyStatus.getId());
        updatedCompanyStatus
            .status(UPDATED_STATUS);
        CompanyStatusDTO companyStatusDTO = companyStatusMapper.toDto(updatedCompanyStatus);

        restCompanyStatusMockMvc.perform(put("/api/company-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyStatusDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyStatus in the database
        List<CompanyStatus> companyStatusList = companyStatusRepository.findAll();
        assertThat(companyStatusList).hasSize(databaseSizeBeforeUpdate);
        CompanyStatus testCompanyStatus = companyStatusList.get(companyStatusList.size() - 1);
        assertThat(testCompanyStatus.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyStatus() throws Exception {
        int databaseSizeBeforeUpdate = companyStatusRepository.findAll().size();

        // Create the CompanyStatus
        CompanyStatusDTO companyStatusDTO = companyStatusMapper.toDto(companyStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyStatusMockMvc.perform(put("/api/company-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyStatus in the database
        List<CompanyStatus> companyStatusList = companyStatusRepository.findAll();
        assertThat(companyStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyStatus() throws Exception {
        // Initialize the database
        companyStatusRepository.saveAndFlush(companyStatus);
        int databaseSizeBeforeDelete = companyStatusRepository.findAll().size();

        // Get the companyStatus
        restCompanyStatusMockMvc.perform(delete("/api/company-statuses/{id}", companyStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CompanyStatus> companyStatusList = companyStatusRepository.findAll();
        assertThat(companyStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyStatus.class);
        CompanyStatus companyStatus1 = new CompanyStatus();
        companyStatus1.setId(1L);
        CompanyStatus companyStatus2 = new CompanyStatus();
        companyStatus2.setId(companyStatus1.getId());
        assertThat(companyStatus1).isEqualTo(companyStatus2);
        companyStatus2.setId(2L);
        assertThat(companyStatus1).isNotEqualTo(companyStatus2);
        companyStatus1.setId(null);
        assertThat(companyStatus1).isNotEqualTo(companyStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyStatusDTO.class);
        CompanyStatusDTO companyStatusDTO1 = new CompanyStatusDTO();
        companyStatusDTO1.setId(1L);
        CompanyStatusDTO companyStatusDTO2 = new CompanyStatusDTO();
        assertThat(companyStatusDTO1).isNotEqualTo(companyStatusDTO2);
        companyStatusDTO2.setId(companyStatusDTO1.getId());
        assertThat(companyStatusDTO1).isEqualTo(companyStatusDTO2);
        companyStatusDTO2.setId(2L);
        assertThat(companyStatusDTO1).isNotEqualTo(companyStatusDTO2);
        companyStatusDTO1.setId(null);
        assertThat(companyStatusDTO1).isNotEqualTo(companyStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(companyStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(companyStatusMapper.fromId(null)).isNull();
    }
}
