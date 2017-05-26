package com.review.dashboard.service;

import com.review.dashboard.domain.Customer;
import com.review.dashboard.domain.CustomerAccessToken;
import com.review.dashboard.repository.CustomerAccessTokenRepository;
import com.review.dashboard.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Service Implementation for managing Customer.
 */

@Service
@Transactional
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);
    
    private final CustomerRepository customerRepository;
    

    @Autowired
    private CustomerAccessTokenRepository customerAccessTokenRepository;


    public CustomerService(CustomerRepository customerRepository,CustomerAccessTokenRepository customerAccessTokenRepository) {
        this.customerRepository = customerRepository;
        this.customerAccessTokenRepository = customerAccessTokenRepository;
    }

    /**
     * Save a customer.
     *
     * @param customer the entity to save
     * @return the persisted entity
     */
    public Customer save(Customer customer) {
        log.debug("Request to save Customer : {}", customer);
        Customer result = customerRepository.save(customer);
        return result;
    }

    /**
     *  Get all the customers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Customer> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        Page<Customer> result = customerRepository.findAll(pageable);
        return result;
    }
    
    
    /**
     *  Get all the customers with current user.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Customer> findByUserId(long id, Pageable pageable) {
        log.debug("Request to get all Customers");
        Page<Customer> result = customerRepository.findByUserId(id,pageable);
        return result;
    }
    
  
    /**
     *  Get one customer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Customer findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        Customer customer = customerRepository.findOne(id);
        return customer;
    }

    /**
     *  Delete the  customer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.delete(id);
    }
    
    public CustomerAccessToken getCustomerAccessToken(String token) {
        final CustomerAccessToken custToken = customerAccessTokenRepository.findByToken(token);
        if ((custToken == null)) {
            return null;
        }
        return custToken;
       
    }
    public String validateCustomerAccessToken(String token) {
        final CustomerAccessToken custToken = customerAccessTokenRepository.findByToken(token);
        if ((custToken == null)) {
            return "invalidToken";
        }

        
        if ((zonedDateTimeDifference(custToken.getExpiryDate(),ZonedDateTime.now(),ChronoUnit.SECONDS)) <= 0) {
            customerAccessTokenRepository.deleteById(custToken.getId());
            return "expired";
        }
        
        return null;

      /*  final User user = custToken.getUser();
        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_BUSINESS")));
        SecurityContextHolder.getContext()
            .setAuthentication(auth);
        return null;
        */
    }
    
    static long zonedDateTimeDifference(ZonedDateTime d1, ZonedDateTime d2, ChronoUnit unit){
        return unit.between(d1, d2);
    }

}