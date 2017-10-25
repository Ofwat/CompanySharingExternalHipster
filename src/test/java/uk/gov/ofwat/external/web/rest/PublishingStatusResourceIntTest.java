package uk.gov.ofwat.external.web.rest;

import uk.gov.ofwat.external.CompanySharingExternalApp;

import uk.gov.ofwat.external.domain.PublishingStatus;
import uk.gov.ofwat.external.repository.PublishingStatusRepository;
import uk.gov.ofwat.external.service.PublishingStatusService;
import uk.gov.ofwat.external.service.dto.PublishingStatusDTO;
import uk.gov.ofwat.external.service.mapper.PublishingStatusMapper;
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
 * Test class for the PublishingStatusResource REST controller.
 *
 * @see PublishingStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class PublishingStatusResourceIntTest {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private PublishingStatusRepository publishingStatusRepository;

    @Autowired
    private PublishingStatusMapper publishingStatusMapper;

    @Autowired
    private PublishingStatusService publishingStatusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPublishingStatusMockMvc;

    private PublishingStatus publishingStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PublishingStatusResource publishingStatusResource = new PublishingStatusResource(publishingStatusService);
        this.restPublishingStatusMockMvc = MockMvcBuilders.standaloneSetup(publishingStatusResource)
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
    public static PublishingStatus createEntity(EntityManager em) {
        PublishingStatus publishingStatus = new PublishingStatus()
            .status(DEFAULT_STATUS);
        return publishingStatus;
    }

    @Before
    public void initTest() {
        publishingStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublishingStatus() throws Exception {
        int databaseSizeBeforeCreate = publishingStatusRepository.findAll().size();

        // Create the PublishingStatus
        PublishingStatusDTO publishingStatusDTO = publishingStatusMapper.toDto(publishingStatus);
        restPublishingStatusMockMvc.perform(post("/api/publishing-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publishingStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the PublishingStatus in the database
        List<PublishingStatus> publishingStatusList = publishingStatusRepository.findAll();
        assertThat(publishingStatusList).hasSize(databaseSizeBeforeCreate + 1);
        PublishingStatus testPublishingStatus = publishingStatusList.get(publishingStatusList.size() - 1);
        assertThat(testPublishingStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPublishingStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publishingStatusRepository.findAll().size();

        // Create the PublishingStatus with an existing ID
        publishingStatus.setId(1L);
        PublishingStatusDTO publishingStatusDTO = publishingStatusMapper.toDto(publishingStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublishingStatusMockMvc.perform(post("/api/publishing-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publishingStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PublishingStatus> publishingStatusList = publishingStatusRepository.findAll();
        assertThat(publishingStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPublishingStatuses() throws Exception {
        // Initialize the database
        publishingStatusRepository.saveAndFlush(publishingStatus);

        // Get all the publishingStatusList
        restPublishingStatusMockMvc.perform(get("/api/publishing-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publishingStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getPublishingStatus() throws Exception {
        // Initialize the database
        publishingStatusRepository.saveAndFlush(publishingStatus);

        // Get the publishingStatus
        restPublishingStatusMockMvc.perform(get("/api/publishing-statuses/{id}", publishingStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(publishingStatus.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPublishingStatus() throws Exception {
        // Get the publishingStatus
        restPublishingStatusMockMvc.perform(get("/api/publishing-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublishingStatus() throws Exception {
        // Initialize the database
        publishingStatusRepository.saveAndFlush(publishingStatus);
        int databaseSizeBeforeUpdate = publishingStatusRepository.findAll().size();

        // Update the publishingStatus
        PublishingStatus updatedPublishingStatus = publishingStatusRepository.findOne(publishingStatus.getId());
        updatedPublishingStatus
            .status(UPDATED_STATUS);
        PublishingStatusDTO publishingStatusDTO = publishingStatusMapper.toDto(updatedPublishingStatus);

        restPublishingStatusMockMvc.perform(put("/api/publishing-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publishingStatusDTO)))
            .andExpect(status().isOk());

        // Validate the PublishingStatus in the database
        List<PublishingStatus> publishingStatusList = publishingStatusRepository.findAll();
        assertThat(publishingStatusList).hasSize(databaseSizeBeforeUpdate);
        PublishingStatus testPublishingStatus = publishingStatusList.get(publishingStatusList.size() - 1);
        assertThat(testPublishingStatus.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPublishingStatus() throws Exception {
        int databaseSizeBeforeUpdate = publishingStatusRepository.findAll().size();

        // Create the PublishingStatus
        PublishingStatusDTO publishingStatusDTO = publishingStatusMapper.toDto(publishingStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPublishingStatusMockMvc.perform(put("/api/publishing-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publishingStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the PublishingStatus in the database
        List<PublishingStatus> publishingStatusList = publishingStatusRepository.findAll();
        assertThat(publishingStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePublishingStatus() throws Exception {
        // Initialize the database
        publishingStatusRepository.saveAndFlush(publishingStatus);
        int databaseSizeBeforeDelete = publishingStatusRepository.findAll().size();

        // Get the publishingStatus
        restPublishingStatusMockMvc.perform(delete("/api/publishing-statuses/{id}", publishingStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PublishingStatus> publishingStatusList = publishingStatusRepository.findAll();
        assertThat(publishingStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublishingStatus.class);
        PublishingStatus publishingStatus1 = new PublishingStatus();
        publishingStatus1.setId(1L);
        PublishingStatus publishingStatus2 = new PublishingStatus();
        publishingStatus2.setId(publishingStatus1.getId());
        assertThat(publishingStatus1).isEqualTo(publishingStatus2);
        publishingStatus2.setId(2L);
        assertThat(publishingStatus1).isNotEqualTo(publishingStatus2);
        publishingStatus1.setId(null);
        assertThat(publishingStatus1).isNotEqualTo(publishingStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublishingStatusDTO.class);
        PublishingStatusDTO publishingStatusDTO1 = new PublishingStatusDTO();
        publishingStatusDTO1.setId(1L);
        PublishingStatusDTO publishingStatusDTO2 = new PublishingStatusDTO();
        assertThat(publishingStatusDTO1).isNotEqualTo(publishingStatusDTO2);
        publishingStatusDTO2.setId(publishingStatusDTO1.getId());
        assertThat(publishingStatusDTO1).isEqualTo(publishingStatusDTO2);
        publishingStatusDTO2.setId(2L);
        assertThat(publishingStatusDTO1).isNotEqualTo(publishingStatusDTO2);
        publishingStatusDTO1.setId(null);
        assertThat(publishingStatusDTO1).isNotEqualTo(publishingStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(publishingStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(publishingStatusMapper.fromId(null)).isNull();
    }
}
