package com.review.dashboard.web.rest;

import com.review.dashboard.Reviewmanager4App;

import com.review.dashboard.domain.CustomerAccessToken;
import com.review.dashboard.repository.CustomerAccessTokenRepository;
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
 * Test class for the CustomerAccessTokenResource REST controller.
 *
 * @see CustomerAccessTokenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Reviewmanager4App.class)
public class CustomerAccessTokenResourceIntTest {

    private static final ZonedDateTime DEFAULT_EXPIRY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    @Autowired
    private CustomerAccessTokenRepository customerAccessTokenRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerAccessTokenMockMvc;

    private CustomerAccessToken customerAccessToken;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerAccessTokenResource customerAccessTokenResource = new CustomerAccessTokenResource(customerAccessTokenRepository);
        this.restCustomerAccessTokenMockMvc = MockMvcBuilders.standaloneSetup(customerAccessTokenResource)
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
    public static CustomerAccessToken createEntity(EntityManager em) {
        CustomerAccessToken customerAccessToken = new CustomerAccessToken()
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .token(DEFAULT_TOKEN);
        return customerAccessToken;
    }

    @Before
    public void initTest() {
        customerAccessToken = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerAccessToken() throws Exception {
        int databaseSizeBeforeCreate = customerAccessTokenRepository.findAll().size();

        // Create the CustomerAccessToken
        restCustomerAccessTokenMockMvc.perform(post("/api/customer-access-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAccessToken)))
            .andExpect(status().isCreated());

        // Validate the CustomerAccessToken in the database
        List<CustomerAccessToken> customerAccessTokenList = customerAccessTokenRepository.findAll();
        assertThat(customerAccessTokenList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerAccessToken testCustomerAccessToken = customerAccessTokenList.get(customerAccessTokenList.size() - 1);
        assertThat(testCustomerAccessToken.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testCustomerAccessToken.getToken()).isEqualTo(DEFAULT_TOKEN);
    }

    @Test
    @Transactional
    public void createCustomerAccessTokenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerAccessTokenRepository.findAll().size();

        // Create the CustomerAccessToken with an existing ID
        customerAccessToken.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerAccessTokenMockMvc.perform(post("/api/customer-access-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAccessToken)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CustomerAccessToken> customerAccessTokenList = customerAccessTokenRepository.findAll();
        assertThat(customerAccessTokenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerAccessTokenRepository.findAll().size();
        // set the field null
        customerAccessToken.setToken(null);

        // Create the CustomerAccessToken, which fails.

        restCustomerAccessTokenMockMvc.perform(post("/api/customer-access-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAccessToken)))
            .andExpect(status().isBadRequest());

        List<CustomerAccessToken> customerAccessTokenList = customerAccessTokenRepository.findAll();
        assertThat(customerAccessTokenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerAccessTokens() throws Exception {
        // Initialize the database
        customerAccessTokenRepository.saveAndFlush(customerAccessToken);

        // Get all the customerAccessTokenList
        restCustomerAccessTokenMockMvc.perform(get("/api/customer-access-tokens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerAccessToken.getId().intValue())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(sameInstant(DEFAULT_EXPIRY_DATE))))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())));
    }

    @Test
    @Transactional
    public void getCustomerAccessToken() throws Exception {
        // Initialize the database
        customerAccessTokenRepository.saveAndFlush(customerAccessToken);

        // Get the customerAccessToken
        restCustomerAccessTokenMockMvc.perform(get("/api/customer-access-tokens/{id}", customerAccessToken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerAccessToken.getId().intValue()))
            .andExpect(jsonPath("$.expiryDate").value(sameInstant(DEFAULT_EXPIRY_DATE)))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerAccessToken() throws Exception {
        // Get the customerAccessToken
        restCustomerAccessTokenMockMvc.perform(get("/api/customer-access-tokens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerAccessToken() throws Exception {
        // Initialize the database
        customerAccessTokenRepository.saveAndFlush(customerAccessToken);
        int databaseSizeBeforeUpdate = customerAccessTokenRepository.findAll().size();

        // Update the customerAccessToken
        CustomerAccessToken updatedCustomerAccessToken = customerAccessTokenRepository.findOne(customerAccessToken.getId());
        updatedCustomerAccessToken
            .expiryDate(UPDATED_EXPIRY_DATE)
            .token(UPDATED_TOKEN);

        restCustomerAccessTokenMockMvc.perform(put("/api/customer-access-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerAccessToken)))
            .andExpect(status().isOk());

        // Validate the CustomerAccessToken in the database
        List<CustomerAccessToken> customerAccessTokenList = customerAccessTokenRepository.findAll();
        assertThat(customerAccessTokenList).hasSize(databaseSizeBeforeUpdate);
        CustomerAccessToken testCustomerAccessToken = customerAccessTokenList.get(customerAccessTokenList.size() - 1);
        assertThat(testCustomerAccessToken.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testCustomerAccessToken.getToken()).isEqualTo(UPDATED_TOKEN);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerAccessToken() throws Exception {
        int databaseSizeBeforeUpdate = customerAccessTokenRepository.findAll().size();

        // Create the CustomerAccessToken

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerAccessTokenMockMvc.perform(put("/api/customer-access-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerAccessToken)))
            .andExpect(status().isCreated());

        // Validate the CustomerAccessToken in the database
        List<CustomerAccessToken> customerAccessTokenList = customerAccessTokenRepository.findAll();
        assertThat(customerAccessTokenList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerAccessToken() throws Exception {
        // Initialize the database
        customerAccessTokenRepository.saveAndFlush(customerAccessToken);
        int databaseSizeBeforeDelete = customerAccessTokenRepository.findAll().size();

        // Get the customerAccessToken
        restCustomerAccessTokenMockMvc.perform(delete("/api/customer-access-tokens/{id}", customerAccessToken.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerAccessToken> customerAccessTokenList = customerAccessTokenRepository.findAll();
        assertThat(customerAccessTokenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerAccessToken.class);
    }
}
