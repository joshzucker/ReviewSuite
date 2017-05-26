package com.review.dashboard.repository;

import com.review.dashboard.domain.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Customer entity.
 */
@SuppressWarnings("unused")
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    //@Query("SELECT t.title FROM Customer t where t.id = :id") 
    Page<Customer> findByUserId(long id,Pageable paginate);
  
    
    
    
    
}
