package com.review.dashboard.web.rest;

import com.review.dashboard.Reviewmanager4App;
import com.review.dashboard.domain.Customer;
import com.review.dashboard.repository.CustomerRepository;
import com.review.dashboard.service.CustomerService;
import com.review.dashboard.service.MailService;
import com.review.dashboard.service.UserService;
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
 * Test class for the CustomerResource REST controller.
 *
 * @see CustomerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Reviewmanager4App.class)
public class CustomerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_SENT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_SENT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_RECEIVED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_RECEIVED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_REVIEW_ID = 1L;
    private static final Long UPDATED_REVIEW_ID = 2L;

    private static final Boolean DEFAULT_IS_REVIEW_1_EMAIL_CLICKED = false;
    private static final Boolean UPDATED_IS_REVIEW_1_EMAIL_CLICKED = true;

    private static final Boolean DEFAULT_IS_REVIEW_2_EMAIL_CLICKED = false;
    private static final Boolean UPDATED_IS_REVIEW_2_EMAIL_CLICKED = true;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerMockMvc;

    private Customer customer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomerResource customerResource = new CustomerResource(customerService,userService,mailService);
        this.restCustomerMockMvc = MockMvcBuilders.standaloneSetup(customerResource)
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
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .dateSent(DEFAULT_DATE_SENT)
            .dateReceived(DEFAULT_DATE_RECEIVED)
            .reviewId(DEFAULT_REVIEW_ID)
            .isReview1EmailClicked(DEFAULT_IS_REVIEW_1_EMAIL_CLICKED)
            .isReview2EmailClicked(DEFAULT_IS_REVIEW_2_EMAIL_CLICKED);
        return customer;
    }

    @Before
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomer.getDateSent()).isEqualTo(DEFAULT_DATE_SENT);
        assertThat(testCustomer.getDateReceived()).isEqualTo(DEFAULT_DATE_RECEIVED);
        assertThat(testCustomer.getReviewId()).isEqualTo(DEFAULT_REVIEW_ID);
        assertThat(testCustomer.isIsReview1EmailClicked()).isEqualTo(DEFAULT_IS_REVIEW_1_EMAIL_CLICKED);
        assertThat(testCustomer.isIsReview2EmailClicked()).isEqualTo(DEFAULT_IS_REVIEW_2_EMAIL_CLICKED);
    }

    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer with an existing ID
        customer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setName(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setEmail(null);

        // Create the Customer, which fails.

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].dateSent").value(hasItem(sameInstant(DEFAULT_DATE_SENT))))
            .andExpect(jsonPath("$.[*].dateReceived").value(hasItem(sameInstant(DEFAULT_DATE_RECEIVED))))
            .andExpect(jsonPath("$.[*].reviewId").value(hasItem(DEFAULT_REVIEW_ID.intValue())))
            .andExpect(jsonPath("$.[*].isReview1EmailClicked").value(hasItem(DEFAULT_IS_REVIEW_1_EMAIL_CLICKED.booleanValue())))
            .andExpect(jsonPath("$.[*].isReview2EmailClicked").value(hasItem(DEFAULT_IS_REVIEW_2_EMAIL_CLICKED.booleanValue())));
    }

    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.dateSent").value(sameInstant(DEFAULT_DATE_SENT)))
            .andExpect(jsonPath("$.dateReceived").value(sameInstant(DEFAULT_DATE_RECEIVED)))
            .andExpect(jsonPath("$.reviewId").value(DEFAULT_REVIEW_ID.intValue()))
            .andExpect(jsonPath("$.isReview1EmailClicked").value(DEFAULT_IS_REVIEW_1_EMAIL_CLICKED.booleanValue()))
            .andExpect(jsonPath("$.isReview2EmailClicked").value(DEFAULT_IS_REVIEW_2_EMAIL_CLICKED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findOne(customer.getId());
        updatedCustomer
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .dateSent(UPDATED_DATE_SENT)
            .dateReceived(UPDATED_DATE_RECEIVED)
            .reviewId(UPDATED_REVIEW_ID)
            .isReview1EmailClicked(UPDATED_IS_REVIEW_1_EMAIL_CLICKED)
            .isReview2EmailClicked(UPDATED_IS_REVIEW_2_EMAIL_CLICKED);

        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomer)))
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomer.getDateSent()).isEqualTo(UPDATED_DATE_SENT);
        assertThat(testCustomer.getDateReceived()).isEqualTo(UPDATED_DATE_RECEIVED);
        assertThat(testCustomer.getReviewId()).isEqualTo(UPDATED_REVIEW_ID);
        assertThat(testCustomer.isIsReview1EmailClicked()).isEqualTo(UPDATED_IS_REVIEW_1_EMAIL_CLICKED);
        assertThat(testCustomer.isIsReview2EmailClicked()).isEqualTo(UPDATED_IS_REVIEW_2_EMAIL_CLICKED);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Create the Customer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerService.save(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Get the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
    }
}
