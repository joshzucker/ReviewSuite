package com.review.dashboard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.review.dashboard.domain.CustomerAccessToken;

import com.review.dashboard.repository.CustomerAccessTokenRepository;
import com.review.dashboard.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CustomerAccessToken.
 */
@RestController
@RequestMapping("/api")
public class CustomerAccessTokenResource {

    private final Logger log = LoggerFactory.getLogger(CustomerAccessTokenResource.class);

    private static final String ENTITY_NAME = "customerAccessToken";
        
    private final CustomerAccessTokenRepository customerAccessTokenRepository;

    public CustomerAccessTokenResource(CustomerAccessTokenRepository customerAccessTokenRepository) {
        this.customerAccessTokenRepository = customerAccessTokenRepository;
    }

    /**
     * POST  /customer-access-tokens : Create a new customerAccessToken.
     *
     * @param customerAccessToken the customerAccessToken to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerAccessToken, or with status 400 (Bad Request) if the customerAccessToken has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-access-tokens")
    @Timed
    public ResponseEntity<CustomerAccessToken> createCustomerAccessToken(@Valid @RequestBody CustomerAccessToken customerAccessToken) throws URISyntaxException {
        log.debug("REST request to save CustomerAccessToken : {}", customerAccessToken);
        if (customerAccessToken.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerAccessToken cannot already have an ID")).body(null);
        }
        CustomerAccessToken result = customerAccessTokenRepository.save(customerAccessToken);
        return ResponseEntity.created(new URI("/api/customer-access-tokens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-access-tokens : Updates an existing customerAccessToken.
     *
     * @param customerAccessToken the customerAccessToken to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerAccessToken,
     * or with status 400 (Bad Request) if the customerAccessToken is not valid,
     * or with status 500 (Internal Server Error) if the customerAccessToken couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-access-tokens")
    @Timed
    public ResponseEntity<CustomerAccessToken> updateCustomerAccessToken(@Valid @RequestBody CustomerAccessToken customerAccessToken) throws URISyntaxException {
        log.debug("REST request to update CustomerAccessToken : {}", customerAccessToken);
        if (customerAccessToken.getId() == null) {
            return createCustomerAccessToken(customerAccessToken);
        }
        CustomerAccessToken result = customerAccessTokenRepository.save(customerAccessToken);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerAccessToken.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-access-tokens : get all the customerAccessTokens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of customerAccessTokens in body
     */
    @GetMapping("/customer-access-tokens")
    @Timed
    public List<CustomerAccessToken> getAllCustomerAccessTokens() {
        log.debug("REST request to get all CustomerAccessTokens");
        List<CustomerAccessToken> customerAccessTokens = customerAccessTokenRepository.findAll();
        return customerAccessTokens;
    }

    /**
     * GET  /customer-access-tokens/:id : get the "id" customerAccessToken.
     *
     * @param id the id of the customerAccessToken to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerAccessToken, or with status 404 (Not Found)
     */
    @GetMapping("/customer-access-tokens/{id}")
    @Timed
    public ResponseEntity<CustomerAccessToken> getCustomerAccessToken(@PathVariable Long id) {
        log.debug("REST request to get CustomerAccessToken : {}", id);
        CustomerAccessToken customerAccessToken = customerAccessTokenRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerAccessToken));
    }

    /**
     * DELETE  /customer-access-tokens/:id : delete the "id" customerAccessToken.
     *
     * @param id the id of the customerAccessToken to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-access-tokens/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerAccessToken(@PathVariable Long id) {
        log.debug("REST request to delete CustomerAccessToken : {}", id);
        customerAccessTokenRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
