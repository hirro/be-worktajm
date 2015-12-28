package com.worktajm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.worktajm.domain.TimeEntry;
import com.worktajm.service.TimeEntryService;
import com.worktajm.web.rest.util.HeaderUtil;
import com.worktajm.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TimeEntry.
 */
@RestController
@RequestMapping("/api")
public class TimeEntryResource {

    private final Logger log = LoggerFactory.getLogger(TimeEntryResource.class);
        
    @Inject
    private TimeEntryService timeEntryService;
    
    /**
     * POST  /timeEntrys -> Create a new timeEntry.
     */
    @RequestMapping(value = "/timeEntrys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeEntry> createTimeEntry(@RequestBody TimeEntry timeEntry) throws URISyntaxException {
        log.debug("REST request to save TimeEntry : {}", timeEntry);
        if (timeEntry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("timeEntry", "idexists", "A new timeEntry cannot already have an ID")).body(null);
        }
        TimeEntry result = timeEntryService.save(timeEntry);
        return ResponseEntity.created(new URI("/api/timeEntrys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("timeEntry", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /timeEntrys -> Updates an existing timeEntry.
     */
    @RequestMapping(value = "/timeEntrys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeEntry> updateTimeEntry(@RequestBody TimeEntry timeEntry) throws URISyntaxException {
        log.debug("REST request to update TimeEntry : {}", timeEntry);
        if (timeEntry.getId() == null) {
            return createTimeEntry(timeEntry);
        }
        TimeEntry result = timeEntryService.save(timeEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("timeEntry", timeEntry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /timeEntrys -> get all the timeEntrys.
     */
    @RequestMapping(value = "/timeEntrys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TimeEntry>> getAllTimeEntrys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TimeEntrys");
        Page<TimeEntry> page = timeEntryService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/timeEntrys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /timeEntrys/:id -> get the "id" timeEntry.
     */
    @RequestMapping(value = "/timeEntrys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeEntry> getTimeEntry(@PathVariable Long id) {
        log.debug("REST request to get TimeEntry : {}", id);
        TimeEntry timeEntry = timeEntryService.findOne(id);
        return Optional.ofNullable(timeEntry)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /timeEntrys/:id -> delete the "id" timeEntry.
     */
    @RequestMapping(value = "/timeEntrys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTimeEntry(@PathVariable Long id) {
        log.debug("REST request to delete TimeEntry : {}", id);
        timeEntryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("timeEntry", id.toString())).build();
    }

    /**
     * SEARCH  /_search/timeEntrys/:query -> search for the timeEntry corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/timeEntrys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TimeEntry> searchTimeEntrys(@PathVariable String query) {
        log.debug("Request to search TimeEntrys for query {}", query);
        return timeEntryService.search(query);
    }
}
