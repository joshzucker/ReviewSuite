package com.review.dashboard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.review.dashboard.domain.Link;

import com.review.dashboard.repository.LinkRepository;
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
 * REST controller for managing Link.
 */
@RestController
@RequestMapping("/api")
public class LinkResource {

    private final Logger log = LoggerFactory.getLogger(LinkResource.class);

    private static final String ENTITY_NAME = "link";
        
    private final LinkRepository linkRepository;

    public LinkResource(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    /**
     * POST  /links : Create a new link.
     *
     * @param link the link to create
     * @return the ResponseEntity with status 201 (Created) and with body the new link, or with status 400 (Bad Request) if the link has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/links")
    @Timed
    public ResponseEntity<Link> createLink(@Valid @RequestBody Link link) throws URISyntaxException {
        log.debug("REST request to save Link : {}", link);
        if (link.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new link cannot already have an ID")).body(null);
        }
        Link result = linkRepository.save(link);
        return ResponseEntity.created(new URI("/api/links/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /links : Updates an existing link.
     *
     * @param link the link to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated link,
     * or with status 400 (Bad Request) if the link is not valid,
     * or with status 500 (Internal Server Error) if the link couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/links")
    @Timed
    public ResponseEntity<Link> updateLink(@Valid @RequestBody Link link) throws URISyntaxException {
        log.debug("REST request to update Link : {}", link);
        if (link.getId() == null) {
            return createLink(link);
        }
        Link result = linkRepository.save(link);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, link.getId().toString()))
            .body(result);
    }

    /**
     * GET  /links : get all the links.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of links in body
     */
    @GetMapping("/links")
    @Timed
    public List<Link> getAllLinks() {
        log.debug("REST request to get all Links");
        List<Link> links = linkRepository.findAll();
        return links;
    }

    /**
     * GET  /links/:id : get the "id" link.
     *
     * @param id the id of the link to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the link, or with status 404 (Not Found)
     */
    @GetMapping("/links/{id}")
    @Timed
    public ResponseEntity<Link> getLink(@PathVariable Long id) {
        log.debug("REST request to get Link : {}", id);
        Link link = linkRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(link));
    }

    /**
     * DELETE  /links/:id : delete the "id" link.
     *
     * @param id the id of the link to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/links/{id}")
    @Timed
    public ResponseEntity<Void> deleteLink(@PathVariable Long id) {
        log.debug("REST request to delete Link : {}", id);
        linkRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
