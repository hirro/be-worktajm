package com.worktajm.service;

import com.worktajm.domain.TimeEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing TimeEntry.
 */
public interface TimeEntryService {

    /**
     * Save a timeEntry.
     * @return the persisted entity
     */
    public TimeEntry save(TimeEntry timeEntry);

    /**
     *  get all the timeEntrys.
     *  @return the list of entities
     */
    public Page<TimeEntry> findAll(Pageable pageable);

    /**
     *  get the "id" timeEntry.
     *  @return the entity
     */
    public TimeEntry findOne(Long id);

    /**
     *  delete the "id" timeEntry.
     */
    public void delete(Long id);

    /**
     * search for the timeEntry corresponding
     * to the query.
     */
    public List<TimeEntry> search(String query);
}
