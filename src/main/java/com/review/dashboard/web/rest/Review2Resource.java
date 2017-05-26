package com.review.dashboard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.review.dashboard.domain.Review2;

import com.review.dashboard.repository.Review2Repository;
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
 * REST controller for managing Review2.
 */
@RestController
@RequestMapping("/api")
public class Review2Resource {

    private final Logger log = LoggerFactory.getLogger(Review2Resource.class);

    private static final String ENTITY_NAME = "review2";
        
    private final Review2Repository review2Repository;

    public Review2Resource(Review2Repository review2Repository) {
        this.review2Repository = review2Repository;
    }

    /**
     * POST  /review-2-s : Create a new review2.
     *
     * @param review2 the review2 to create
     * @return the ResponseEntity with status 201 (Created) and with body the new review2, or with status 400 (Bad Request) if the review2 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/review-2-s")
    @Timed
    public ResponseEntity<Review2> createReview2(@Valid @RequestBody Review2 review2) throws URISyntaxException {
        log.debug("REST request to save Review2 : {}", review2);
        if (review2.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new review2 cannot already have an ID")).body(null);
        }
        Review2 result = review2Repository.save(review2);
        return ResponseEntity.created(new URI("/api/review-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /review-2-s : Updates an existing review2.
     *
     * @param review2 the review2 to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated review2,
     * or with status 400 (Bad Request) if the review2 is not valid,
     * or with status 500 (Internal Server Error) if the review2 couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/review-2-s")
    @Timed
    public ResponseEntity<Review2> updateReview2(@Valid @RequestBody Review2 review2) throws URISyntaxException {
        log.debug("REST request to update Review2 : {}", review2);
        if (review2.getId() == null) {
            return createReview2(review2);
        }
        Review2 result = review2Repository.save(review2);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, review2.getId().toString()))
            .body(result);
    }

    /**
     * GET  /review-2-s : get all the review2S.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of review2S in body
     */
    @GetMapping("/review-2-s")
    @Timed
    public List<Review2> getAllReview2S() {
        log.debug("REST request to get all Review2S");
        List<Review2> review2S = review2Repository.findAll();
        return review2S;
    }

    /**
     * GET  /review-2-s/:id : get the "id" review2.
     *
     * @param id the id of the review2 to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the review2, or with status 404 (Not Found)
     */
    @GetMapping("/review-2-s/{id}")
    @Timed
    public ResponseEntity<Review2> getReview2(@PathVariable Long id) {
        log.debug("REST request to get Review2 : {}", id);
        Review2 review2 = review2Repository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(review2));
    }

    /**
     * DELETE  /review-2-s/:id : delete the "id" review2.
     *
     * @param id the id of the review2 to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/review-2-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteReview2(@PathVariable Long id) {
        log.debug("REST request to delete Review2 : {}", id);
        review2Repository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
