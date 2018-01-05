package uk.gov.ofwat.external.web.rest;

import uk.gov.ofwat.external.CompanySharingExternalApp;

import uk.gov.ofwat.external.domain.ReviewSignOff;
import uk.gov.ofwat.external.domain.User;
import uk.gov.ofwat.external.domain.CompanyDataInput;
import uk.gov.ofwat.external.repository.ReviewSignOffRepository;
import uk.gov.ofwat.external.service.ReviewSignOffService;
import uk.gov.ofwat.external.service.dto.ReviewSignOffDTO;
import uk.gov.ofwat.external.service.mapper.ReviewSignOffMapper;
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
 * Test class for the ReviewSignOffResource REST controller.
 *
 * @see ReviewSignOffResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CompanySharingExternalApp.class)
public class ReviewSignOffResourceIntTest {

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    @Autowired
    private ReviewSignOffRepository reviewSignOffRepository;

    @Autowired
    private ReviewSignOffMapper reviewSignOffMapper;

    @Autowired
    private ReviewSignOffService reviewSignOffService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReviewSignOffMockMvc;

    private ReviewSignOff reviewSignOff;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReviewSignOffResource reviewSignOffResource = new ReviewSignOffResource(reviewSignOffService);
        this.restReviewSignOffMockMvc = MockMvcBuilders.standaloneSetup(reviewSignOffResource)
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
    public static ReviewSignOff createEntity(EntityManager em) {
        ReviewSignOff reviewSignOff = new ReviewSignOff()
            .status(DEFAULT_STATUS)
            .reason(DEFAULT_REASON);
        // Add required entity
        User signatory = UserResourceIntTest.createEntity(em);
        em.persist(signatory);
        em.flush();
        reviewSignOff.setSignatory(signatory);
        // Add required entity
        CompanyDataInput companyDataInput = CompanyDataInputResourceIntTest.createEntity(em);
        em.persist(companyDataInput);
        em.flush();
        reviewSignOff.setCompanyDataInput(companyDataInput);
        return reviewSignOff;
    }

    @Before
    public void initTest() {
        reviewSignOff = createEntity(em);
    }

    @Test
    @Transactional
    public void createReviewSignOff() throws Exception {
        int databaseSizeBeforeCreate = reviewSignOffRepository.findAll().size();

        // Create the ReviewSignOff
        ReviewSignOffDTO reviewSignOffDTO = reviewSignOffMapper.toDto(reviewSignOff);
        restReviewSignOffMockMvc.perform(post("/api/review-sign-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewSignOffDTO)))
            .andExpect(status().isCreated());

        // Validate the ReviewSignOff in the database
        List<ReviewSignOff> reviewSignOffList = reviewSignOffRepository.findAll();
        assertThat(reviewSignOffList).hasSize(databaseSizeBeforeCreate + 1);
        ReviewSignOff testReviewSignOff = reviewSignOffList.get(reviewSignOffList.size() - 1);
        assertThat(testReviewSignOff.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testReviewSignOff.getReason()).isEqualTo(DEFAULT_REASON);
    }

    @Test
    @Transactional
    public void createReviewSignOffWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reviewSignOffRepository.findAll().size();

        // Create the ReviewSignOff with an existing ID
        reviewSignOff.setId(1L);
        ReviewSignOffDTO reviewSignOffDTO = reviewSignOffMapper.toDto(reviewSignOff);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewSignOffMockMvc.perform(post("/api/review-sign-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewSignOffDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ReviewSignOff> reviewSignOffList = reviewSignOffRepository.findAll();
        assertThat(reviewSignOffList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = reviewSignOffRepository.findAll().size();
        // set the field null
        reviewSignOff.setStatus(null);

        // Create the ReviewSignOff, which fails.
        ReviewSignOffDTO reviewSignOffDTO = reviewSignOffMapper.toDto(reviewSignOff);

        restReviewSignOffMockMvc.perform(post("/api/review-sign-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewSignOffDTO)))
            .andExpect(status().isBadRequest());

        List<ReviewSignOff> reviewSignOffList = reviewSignOffRepository.findAll();
        assertThat(reviewSignOffList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReviewSignOffs() throws Exception {
        // Initialize the database
        reviewSignOffRepository.saveAndFlush(reviewSignOff);

        // Get all the reviewSignOffList
        restReviewSignOffMockMvc.perform(get("/api/review-sign-offs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reviewSignOff.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())));
    }

    @Test
    @Transactional
    public void getReviewSignOff() throws Exception {
        // Initialize the database
        reviewSignOffRepository.saveAndFlush(reviewSignOff);

        // Get the reviewSignOff
        restReviewSignOffMockMvc.perform(get("/api/review-sign-offs/{id}", reviewSignOff.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reviewSignOff.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReviewSignOff() throws Exception {
        // Get the reviewSignOff
        restReviewSignOffMockMvc.perform(get("/api/review-sign-offs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReviewSignOff() throws Exception {
        // Initialize the database
        reviewSignOffRepository.saveAndFlush(reviewSignOff);
        int databaseSizeBeforeUpdate = reviewSignOffRepository.findAll().size();

        // Update the reviewSignOff
        ReviewSignOff updatedReviewSignOff = reviewSignOffRepository.findOne(reviewSignOff.getId());
        updatedReviewSignOff
            .status(UPDATED_STATUS)
            .reason(UPDATED_REASON);
        ReviewSignOffDTO reviewSignOffDTO = reviewSignOffMapper.toDto(updatedReviewSignOff);

        restReviewSignOffMockMvc.perform(put("/api/review-sign-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewSignOffDTO)))
            .andExpect(status().isOk());

        // Validate the ReviewSignOff in the database
        List<ReviewSignOff> reviewSignOffList = reviewSignOffRepository.findAll();
        assertThat(reviewSignOffList).hasSize(databaseSizeBeforeUpdate);
        ReviewSignOff testReviewSignOff = reviewSignOffList.get(reviewSignOffList.size() - 1);
        assertThat(testReviewSignOff.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testReviewSignOff.getReason()).isEqualTo(UPDATED_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingReviewSignOff() throws Exception {
        int databaseSizeBeforeUpdate = reviewSignOffRepository.findAll().size();

        // Create the ReviewSignOff
        ReviewSignOffDTO reviewSignOffDTO = reviewSignOffMapper.toDto(reviewSignOff);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReviewSignOffMockMvc.perform(put("/api/review-sign-offs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewSignOffDTO)))
            .andExpect(status().isCreated());

        // Validate the ReviewSignOff in the database
        List<ReviewSignOff> reviewSignOffList = reviewSignOffRepository.findAll();
        assertThat(reviewSignOffList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReviewSignOff() throws Exception {
        // Initialize the database
        reviewSignOffRepository.saveAndFlush(reviewSignOff);
        int databaseSizeBeforeDelete = reviewSignOffRepository.findAll().size();

        // Get the reviewSignOff
        restReviewSignOffMockMvc.perform(delete("/api/review-sign-offs/{id}", reviewSignOff.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReviewSignOff> reviewSignOffList = reviewSignOffRepository.findAll();
        assertThat(reviewSignOffList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewSignOff.class);
        ReviewSignOff reviewSignOff1 = new ReviewSignOff();
        reviewSignOff1.setId(1L);
        ReviewSignOff reviewSignOff2 = new ReviewSignOff();
        reviewSignOff2.setId(reviewSignOff1.getId());
        assertThat(reviewSignOff1).isEqualTo(reviewSignOff2);
        reviewSignOff2.setId(2L);
        assertThat(reviewSignOff1).isNotEqualTo(reviewSignOff2);
        reviewSignOff1.setId(null);
        assertThat(reviewSignOff1).isNotEqualTo(reviewSignOff2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewSignOffDTO.class);
        ReviewSignOffDTO reviewSignOffDTO1 = new ReviewSignOffDTO();
        reviewSignOffDTO1.setId(1L);
        ReviewSignOffDTO reviewSignOffDTO2 = new ReviewSignOffDTO();
        assertThat(reviewSignOffDTO1).isNotEqualTo(reviewSignOffDTO2);
        reviewSignOffDTO2.setId(reviewSignOffDTO1.getId());
        assertThat(reviewSignOffDTO1).isEqualTo(reviewSignOffDTO2);
        reviewSignOffDTO2.setId(2L);
        assertThat(reviewSignOffDTO1).isNotEqualTo(reviewSignOffDTO2);
        reviewSignOffDTO1.setId(null);
        assertThat(reviewSignOffDTO1).isNotEqualTo(reviewSignOffDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reviewSignOffMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reviewSignOffMapper.fromId(null)).isNull();
    }
}
