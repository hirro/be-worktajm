package com.worktajm.service.impl;

import com.worktajm.service.TimeEntryService;
import com.worktajm.domain.TimeEntry;
import com.worktajm.repository.TimeEntryRepository;
import com.worktajm.repository.search.TimeEntrySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TimeEntry.
 */
@Service
@Transactional
public class TimeEntryServiceImpl implements TimeEntryService{

    private final Logger log = LoggerFactory.getLogger(TimeEntryServiceImpl.class);
    
    @Inject
    private TimeEntryRepository timeEntryRepository;
    
    @Inject
    private TimeEntrySearchRepository timeEntrySearchRepository;
    
    /**
     * Save a timeEntry.
     * @return the persisted entity
     */
    public TimeEntry save(TimeEntry timeEntry) {
        log.debug("Request to save TimeEntry : {}", timeEntry);
        TimeEntry result = timeEntryRepository.save(timeEntry);
        timeEntrySearchRepository.save(result);
        return result;
    }

    /**
     *  get all the timeEntrys.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<TimeEntry> findAll(Pageable pageable) {
        log.debug("Request to get all TimeEntrys");
        Page<TimeEntry> result = timeEntryRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one timeEntry by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TimeEntry findOne(Long id) {
        log.debug("Request to get TimeEntry : {}", id);
        TimeEntry timeEntry = timeEntryRepository.findOne(id);
        return timeEntry;
    }

    /**
     *  delete the  timeEntry by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete TimeEntry : {}", id);
        timeEntryRepository.delete(id);
        timeEntrySearchRepository.delete(id);
    }

    /**
     * search for the timeEntry corresponding
     * to the query.
     */
    @Transactional(readOnly = true) 
    public List<TimeEntry> search(String query) {
        
        log.debug("REST request to search TimeEntrys for query {}", query);
        return StreamSupport
            .stream(timeEntrySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
