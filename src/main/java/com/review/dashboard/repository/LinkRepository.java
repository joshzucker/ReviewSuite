package com.review.dashboard.repository;

import com.review.dashboard.domain.Link;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Link entity.
 */
@SuppressWarnings("unused")
public interface LinkRepository extends JpaRepository<Link,Long> {

}
