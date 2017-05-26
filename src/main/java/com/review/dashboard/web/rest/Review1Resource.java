package com.review.dashboard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.review.dashboard.domain.Review1;

import com.review.dashboard.repository.Review1Repository;
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
 * REST controller for managing Review1.
 */
@RestController
@RequestMapping("/api")
public class Review1Resource {

    private final Logger log = LoggerFactory.getLogger(Review1Resource.class);

    private static final String ENTITY_NAME = "review1";
        
    private final Review1Repository review1Repository;

    public Review1Resource(Review1Repository review1Repository) {
        this.review1Repository = review1Repository;
    }

    /**
     * POST  /review-1-s : Create a new review1.
     *
     * @param review1 the review1 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new review1, or with status 400 (Bad Request) if the review1 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/review-1-s")
    @Timed
    public ResponseEntity<Review1> createReview1(@Valid @RequestBody Review1 review1) throws URISyntaxException {
        log.debug("REST request to save Review1 : {}", review1);
        if (review1.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new review1 cannot already have an ID")).body(null);
        }
        Review1 result = review1Repository.save(review1);
        return ResponseEntity.created(new URI("/api/review-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /review-1-s : Updates an existing review1.
     *
     * @param review1 the review1 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated review1,
     * or with status 400 (Bad Request) if the review1 is not valid,
     * or with status 500 (Internal Server Error) if the review1 couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/review-1-s")
    @Timed
    public ResponseEntity<Review1> updateReview1(@Valid @RequestBody Review1 review1) throws URISyntaxException {
        log.debug("REST request to update Review1 : {}", review1);
        if (review1.getId() == null) {
            return createReview1(review1);
        }
        Review1 result = review1Repository.save(review1);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, review1.getId().toString()))
            .body(result);
    }

    /**
     * GET  /review-1-s : get all the review1S.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of review1S in body
     */
    @GetMapping("/review-1-s")
    @Timed
    public List<Review1> getAllReview1S() {
        log.debug("REST request to get all Review1S");
        List<Review1> review1S = review1Repository.findAll();
        return review1S;
    }

    /**
     * GET  /review-1-s/:id : get the "id" review1.
     *
     * @param id the id of the review1 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the review1, or with status 404 (Not Found)
     */
    @GetMapping("/review-1-s/{id}")
    @Timed
    public ResponseEntity<Review1> getReview1(@PathVariable Long id) {
        log.debug("REST request to get Review1 : {}", id);
        Review1 review1 = review1Repository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(review1));
    }

    /**
     * DELETE  /review-1-s/:id : delete the "id" review1.
     *
     * @param id the id of the review1 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/review-1-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteReview1(@PathVariable Long id) {
        log.debug("REST request to delete Review1 : {}", id);
        review1Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
