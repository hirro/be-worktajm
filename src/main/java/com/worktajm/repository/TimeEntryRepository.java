package com.worktajm.repository;

import com.worktajm.domain.TimeEntry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TimeEntry entity.
 */
public interface TimeEntryRepository extends JpaRepository<TimeEntry,Long> {

    @Query("select timeEntry from TimeEntry timeEntry where timeEntry.user.login = ?#{principal.username}")
    List<TimeEntry> findByUserIsCurrentUser();

}
