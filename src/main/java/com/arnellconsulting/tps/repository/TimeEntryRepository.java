/*
 * Time Reporing Server.
 *
 * Copyright 2013 Arnell Consulting AB
 */

package com.arnellconsulting.tps.repository;

import com.arnellconsulting.tps.model.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jiar
 */
public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {   
}
