package com.review.dashboard.web.rest;

import com.review.dashboard.Reviewmanager4App;

import com.review.dashboard.domain.Review2;
import com.review.dashboard.repository.Review2Repository;
import com.review.dashboard.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.review.dashboard.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the Review2Resource REST controller.
 *
 * @see Review2Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Reviewmanager4App.class)
public class Review2ResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_WRITTEN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_WRITTEN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_EXPERIECE = "AAAAAAAAAA";
    private static final String UPDATED_CUST_EXPERIECE = "BBBBBBBBBB";

    @Autowired
    private Review2Repository review2Repository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReview2MockMvc;

    private Review2 review2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Review2Resource review2Resource = new Review2Resource(review2Repository);
        this.restReview2MockMvc = MockMvcBuilders.standaloneSetup(review2Resource)
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
    public static Review2 createEntity(EntityManager em) {
        Review2 review2 = new Review2()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .dateWritten(DEFAULT_DATE_WRITTEN)
            .token(DEFAULT_TOKEN)
            .custExperiece(DEFAULT_CUST_EXPERIECE);
        return review2;
    }

    @Before
    public void initTest() {
        review2 = createEntity(em);
    }

    @Test
    @Transactional
    public void createReview2() throws Exception {
        int databaseSizeBeforeCreate = review2Repository.findAll().size();

        // Create the Review2
        restReview2MockMvc.perform(post("/api/review-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(review2)))
            .andExpect(status().isCreated());

        // Validate the Review2 in the database
        List<Review2> review2List = review2Repository.findAll();
        assertThat(review2List).hasSize(databaseSizeBeforeCreate + 1);
        Review2 testReview2 = review2List.get(review2List.size() - 1);
        assertThat(testReview2.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testReview2.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testReview2.getDateWritten()).isEqualTo(DEFAULT_DATE_WRITTEN);
        assertThat(testReview2.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testReview2.getCustExperiece()).isEqualTo(DEFAULT_CUST_EXPERIECE);
    }

    @Test
    @Transactional
    public void createReview2WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = review2Repository.findAll().size();

        // Create the Review2 with an existing ID
        review2.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReview2MockMvc.perform(post("/api/review-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(review2)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Review2> review2List = review2Repository.findAll();
        assertThat(review2List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = review2Repository.findAll().size();
        // set the field null
        review2.setFirstName(null);

        // Create the Review2, which fails.

        restReview2MockMvc.perform(post("/api/review-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(review2)))
            .andExpect(status().isBadRequest());

        List<Review2> review2List = review2Repository.findAll();
        assertThat(review2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = review2Repository.findAll().size();
        // set the field null
        review2.setLastName(null);

        // Create the Review2, which fails.

        restReview2MockMvc.perform(post("/api/review-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(review2)))
            .andExpect(status().isBadRequest());

        List<Review2> review2List = review2Repository.findAll();
        assertThat(review2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = review2Repository.findAll().size();
        // set the field null
        review2.setToken(null);

        // Create the Review2, which fails.

        restReview2MockMvc.perform(post("/api/review-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(review2)))
            .andExpect(status().isBadRequest());

        List<Review2> review2List = review2Repository.findAll();
        assertThat(review2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustExperieceIsRequired() throws Exception {
        int databaseSizeBeforeTest = review2Repository.findAll().size();
        // set the field null
        review2.setCustExperiece(null);

        // Create the Review2, which fails.

        restReview2MockMvc.perform(post("/api/review-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(review2)))
            .andExpect(status().isBadRequest());

        List<Review2> review2List = review2Repository.findAll();
        assertThat(review2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReview2S() throws Exception {
        // Initialize the database
        review2Repository.saveAndFlush(review2);

        // Get all the review2List
        restReview2MockMvc.perform(get("/api/review-2-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(review2.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].dateWritten").value(hasItem(sameInstant(DEFAULT_DATE_WRITTEN))))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].custExperiece").value(hasItem(DEFAULT_CUST_EXPERIECE.toString())));
    }

    @Test
    @Transactional
    public void getReview2() throws Exception {
        // Initialize the database
        review2Repository.saveAndFlush(review2);

        // Get the review2
        restReview2MockMvc.perform(get("/api/review-2-s/{id}", review2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(review2.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.dateWritten").value(sameInstant(DEFAULT_DATE_WRITTEN)))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()))
            .andExpect(jsonPath("$.custExperiece").value(DEFAULT_CUST_EXPERIECE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReview2() throws Exception {
        // Get the review2
        restReview2MockMvc.perform(get("/api/review-2-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReview2() throws Exception {
        // Initialize the database
        review2Repository.saveAndFlush(review2);
        int databaseSizeBeforeUpdate = review2Repository.findAll().size();

        // Update the review2
        Review2 updatedReview2 = review2Repository.findOne(review2.getId());
        updatedReview2
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dateWritten(UPDATED_DATE_WRITTEN)
            .token(UPDATED_TOKEN)
            .custExperiece(UPDATED_CUST_EXPERIECE);

        restReview2MockMvc.perform(put("/api/review-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReview2)))
            .andExpect(status().isOk());

        // Validate the Review2 in the database
        List<Review2> review2List = review2Repository.findAll();
        assertThat(review2List).hasSize(databaseSizeBeforeUpdate);
        Review2 testReview2 = review2List.get(review2List.size() - 1);
        assertThat(testReview2.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testReview2.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testReview2.getDateWritten()).isEqualTo(UPDATED_DATE_WRITTEN);
        assertThat(testReview2.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testReview2.getCustExperiece()).isEqualTo(UPDATED_CUST_EXPERIECE);
    }

    @Test
    @Transactional
    public void updateNonExistingReview2() throws Exception {
        int databaseSizeBeforeUpdate = review2Repository.findAll().size();

        // Create the Review2

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReview2MockMvc.perform(put("/api/review-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(review2)))
            .andExpect(status().isCreated());

        // Validate the Review2 in the database
        List<Review2> review2List = review2Repository.findAll();
        assertThat(review2List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReview2() throws Exception {
        // Initialize the database
        review2Repository.saveAndFlush(review2);
        int databaseSizeBeforeDelete = review2Repository.findAll().size();

        // Get the review2
        restReview2MockMvc.perform(delete("/api/review-2-s/{id}", review2.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Review2> review2List = review2Repository.findAll();
        assertThat(review2List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Review2.class);
    }
}
