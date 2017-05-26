package com.review.dashboard.repository;

import com.review.dashboard.domain.Review1;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Review1 entity.
 */
@SuppressWarnings("unused")
public interface Review1Repository extends JpaRepository<Review1,Long> {

}
