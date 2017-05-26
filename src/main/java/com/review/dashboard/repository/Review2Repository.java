package com.review.dashboard.repository;

import com.review.dashboard.domain.Review2;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Review2 entity.
 */
@SuppressWarnings("unused")
public interface Review2Repository extends JpaRepository<Review2,Long> {

}
