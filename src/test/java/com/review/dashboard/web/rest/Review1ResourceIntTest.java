package com.review.dashboard.web.rest;

import com.review.dashboard.Reviewmanager4App;

import com.review.dashboard.domain.Review1;
import com.review.dashboard.repository.Review1Repository;
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
 * Test class for the Review1Resource REST controller.
 *
 * @see Review1Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Reviewmanager4App.class)
public class Review1ResourceIntTest {

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    private static final ZonedDateTime DEFAULT_DATE_WRITTEN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_WRITTEN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_SELECTED_LINK = "AAAAAAAAAA";
    private static final String UPDATED_SELECTED_LINK = "BBBBBBBBBB";

    @Autowired
    private Review1Repository review1Repository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReview1MockMvc;

    private Review1 review1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Review1Resource review1Resource = new Review1Resource(review1Repository);
        this.restReview1MockMvc = MockMvcBuilders.standaloneSetup(review1Resource)
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
    public static Review1 createEntity(EntityManager em) {
        Review1 review1 = new Review1()
            .rating(DEFAULT_RATING)
            .dateWritten(DEFAULT_DATE_WRITTEN)
            .token(DEFAULT_TOKEN)
            .selectedLink(DEFAULT_SELECTED_LINK);
        return review1;
    }

    @Before
    public void initTest() {
        review1 = createEntity(em);
    }

    @Test
    @Transactional
    public void createReview1() throws Exception {
        int databaseSizeBeforeCreate = review1Repository.findAll().size();

        // Create the Review1
        restReview1MockMvc.perform(post("/api/review-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(review1)))
            .andExpect(status().isCreated());

        // Validate the Review1 in the database
        List<Review1> review1List = review1Repository.findAll();
        assertThat(review1List).hasSize(databaseSizeBeforeCreate + 1);
        Review1 testReview1 = review1List.get(review1List.size() - 1);
        assertThat(testReview1.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testReview1.getDateWritten()).isEqualTo(DEFAULT_DATE_WRITTEN);
        assertThat(testReview1.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testReview1.getSelectedLink()).isEqualTo(DEFAULT_SELECTED_LINK);
    }

    @Test
    @Transactional
    public void createReview1WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = review1Repository.findAll().size();

        // Create the Review1 with an existing ID
        review1.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReview1MockMvc.perform(post("/api/review-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(review1)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Review1> review1List = review1Repository.findAll();
        assertThat(review1List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = review1Repository.findAll().size();
        // set the field null
        review1.setToken(null);

        // Create the Review1, which fails.

        restReview1MockMvc.perform(post("/api/review-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(review1)))
            .andExpect(status().isBadRequest());

        List<Review1> review1List = review1Repository.findAll();
        assertThat(review1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSelectedLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = review1Repository.findAll().size();
        // set the field null
        review1.setSelectedLink(null);

        // Create the Review1, which fails.

        restReview1MockMvc.perform(post("/api/review-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(review1)))
            .andExpect(status().isBadRequest());

        List<Review1> review1List = review1Repository.findAll();
        assertThat(review1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReview1S() throws Exception {
        // Initialize the database
        review1Repository.saveAndFlush(review1);

        // Get all the review1List
        restReview1MockMvc.perform(get("/api/review-1-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(review1.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].dateWritten").value(hasItem(sameInstant(DEFAULT_DATE_WRITTEN))))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].selectedLink").value(hasItem(DEFAULT_SELECTED_LINK.toString())));
    }

    @Test
    @Transactional
    public void getReview1() throws Exception {
        // Initialize the database
        review1Repository.saveAndFlush(review1);

        // Get the review1
        restReview1MockMvc.perform(get("/api/review-1-s/{id}", review1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(review1.getId().intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.dateWritten").value(sameInstant(DEFAULT_DATE_WRITTEN)))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()))
            .andExpect(jsonPath("$.selectedLink").value(DEFAULT_SELECTED_LINK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReview1() throws Exception {
        // Get the review1
        restReview1MockMvc.perform(get("/api/review-1-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReview1() throws Exception {
        // Initialize the database
        review1Repository.saveAndFlush(review1);
        int databaseSizeBeforeUpdate = review1Repository.findAll().size();

        // Update the review1
        Review1 updatedReview1 = review1Repository.findOne(review1.getId());
        updatedReview1
            .rating(UPDATED_RATING)
            .dateWritten(UPDATED_DATE_WRITTEN)
            .token(UPDATED_TOKEN)
            .selectedLink(UPDATED_SELECTED_LINK);

        restReview1MockMvc.perform(put("/api/review-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReview1)))
            .andExpect(status().isOk());

        // Validate the Review1 in the database
        List<Review1> review1List = review1Repository.findAll();
        assertThat(review1List).hasSize(databaseSizeBeforeUpdate);
        Review1 testReview1 = review1List.get(review1List.size() - 1);
        assertThat(testReview1.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testReview1.getDateWritten()).isEqualTo(UPDATED_DATE_WRITTEN);
        assertThat(testReview1.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testReview1.getSelectedLink()).isEqualTo(UPDATED_SELECTED_LINK);
    }

    @Test
    @Transactional
    public void updateNonExistingReview1() throws Exception {
        int databaseSizeBeforeUpdate = review1Repository.findAll().size();

        // Create the Review1

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReview1MockMvc.perform(put("/api/review-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(review1)))
            .andExpect(status().isCreated());

        // Validate the Review1 in the database
        List<Review1> review1List = review1Repository.findAll();
        assertThat(review1List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReview1() throws Exception {
        // Initialize the database
        review1Repository.saveAndFlush(review1);
        int databaseSizeBeforeDelete = review1Repository.findAll().size();

        // Get the review1
        restReview1MockMvc.perform(delete("/api/review-1-s/{id}", review1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Review1> review1List = review1Repository.findAll();
        assertThat(review1List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Review1.class);
    }
}
