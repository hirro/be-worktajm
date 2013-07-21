/*
 * Time Reporing Server.
 *
 * Copyright 2013 Arnell Consulting AB
 */
package com.arnellconsulting.tps.service;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.model.TimeEntry;
import com.arnellconsulting.tps.repository.PersonRepository;
import com.arnellconsulting.tps.repository.ProjectRepository;
import com.arnellconsulting.tps.repository.TimeEntryRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * Implements the TpsService interface.
 *
 * @author jiar
 */
@Slf4j
@Service
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class TpsServiceImpl implements TpsService {
   private final transient @NonNull PersonRepository personRepository;
   private final transient @NonNull ProjectRepository projectRepository;
   private final transient @NonNull TimeEntryRepository timeEntryRepository;

   //~--- methods -------------------------------------------------------------

   @Override
   public Person create(final Person person) {
      try {
         log.debug("Create person");
         this.personRepository.save(person);
      } catch (DataAccessException e) {
         log.debug("Failed to create entry", e);
      }
      return person;
   }

   @Override
   public Person delete(final int id) {
      log.debug("Delete person");
      throw new UnsupportedOperationException("Not supported yet.");    // To change body of generated methods, choose Tools | Templates.
   }

   @Override
   public Person findPersonByEmail(final String email) {
     log.debug("Find person by email");
      return this.personRepository.findByEmail(email);
   }

   @Override
   public Person update(final Person person) {
      log.debug("Update person");
      this.personRepository.save(person);

      return person;
   }

   @Override
   public List<Project> getProjets() {
      return projectRepository.findAll();
   }

   @Override
   public Project getProjectById(Long id) {
      return projectRepository.findOne(id);
   }

   @Override
   public List<TimeEntry> getTimeEntries() {
      return timeEntryRepository.findAll();
   }
}
