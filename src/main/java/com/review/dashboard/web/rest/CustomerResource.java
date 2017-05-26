package com.review.dashboard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.review.dashboard.domain.Authority;
import com.review.dashboard.domain.Customer;
import com.review.dashboard.domain.User;
import com.review.dashboard.service.CustomerService;
import com.review.dashboard.service.MailService;
import com.review.dashboard.service.UserService;
import com.review.dashboard.service.util.EmailStatus;
import com.review.dashboard.service.util.RandomUtil;
import com.review.dashboard.web.rest.util.HeaderUtil;
import com.review.dashboard.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Customer.
 */
@RestController
@RequestMapping("/api")
public class CustomerResource {

    private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    private static final String ENTITY_NAME = "customer";
        
    private final CustomerService customerService;
    private final UserService userService;
    private final MailService mailService;

    public CustomerResource(CustomerService customerService,UserService userService,MailService mailService) {
        this.userService = userService;
        this.customerService = customerService;
        this.mailService = mailService;
    }

    /**
     * POST  /customers : Create a new customer.
     *
     * @param customer the customer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customer, or with status 400 (Bad Request) if the customer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customers")
    @Timed
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) throws URISyntaxException {
        log.debug("REST request to save Customer : {}", customer);
        if (customer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customer cannot already have an ID")).body(null);
        }
        
        User user = userService.getUserWithAuthorities();
        String token= RandomUtil.generateCustTokenKey();
        
        // Customer result=customer;
        customer.setDateSent(ZonedDateTime.now());
        customer.setUser(user);
        
        Customer result = customerService.save(customer);
        EmailStatus emailStatus = mailService.sendCustomerTemplateMail(user,result,token);
        userService.createCustomerAccessTokenForUser(user,customer,token);  
        
        return ResponseEntity.created(new URI("/api/customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customers : Updates an existing customer.
     *
     * @param customer the customer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customer,
     * or with status 400 (Bad Request) if the customer is not valid,
     * or with status 500 (Internal Server Error) if the customer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customers")
    @Timed
    public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer customer) throws URISyntaxException {
        log.debug("REST request to update Customer : {}", customer);
        if (customer.getId() == null) {
            return createCustomer(customer);
        }
        Customer result = customerService.save(customer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customers : get all the customers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of customers in body
     */
    @GetMapping("/customers")
    @Timed
    public ResponseEntity<List<Customer>> getAllCustomers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Customers");
        Page<Customer> page;
        
        User user = userService.getUserWithAuthorities();
        Authority authority = new Authority();
        authority.setName("ROLE_ADMIN");
        if(user.getAuthorities().contains(authority)){
             page = customerService.findAll(pageable);  
        }
        else{
            page = customerService.findByUserId(user.getId(),pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customers/:id : get the "id" customer.
     *
     * @param id the id of the customer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customer, or with status 404 (Not Found)
     */
    @GetMapping("/customers/{id}")
    @Timed
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        log.debug("REST request to get Customer : {}", id);
        Customer customer = customerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customer));
    }

    /**
     * DELETE  /customers/:id : delete the "id" customer.
     *
     * @param id the id of the customer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customers/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        log.debug("REST request to delete Customer : {}", id);
        customerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
