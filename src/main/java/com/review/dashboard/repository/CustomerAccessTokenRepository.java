package com.review.dashboard.repository;

import com.review.dashboard.domain.CustomerAccessToken;
import com.review.dashboard.domain.User;

import org.springframework.data.jpa.repository.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

/**
 * Spring Data JPA repository for the CustomerAccessToken entity.
 */
@SuppressWarnings("unused")
public interface CustomerAccessTokenRepository extends JpaRepository<CustomerAccessToken,Long> {
    
    CustomerAccessToken findByToken(String token);

    CustomerAccessToken findByUser(User user);
    
    void deleteById(Long id);

    Stream<CustomerAccessToken> findAllByExpiryDateLessThan(ZonedDateTime now);

    void deleteByExpiryDateLessThan(ZonedDateTime now);
}
