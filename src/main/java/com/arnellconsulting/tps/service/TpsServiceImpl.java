/*
 * Copyright 2013 Arnell Consulting AB.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package com.arnellconsulting.tps.service;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.model.TimeEntry;
import com.arnellconsulting.tps.repository.PersonRepository;
import com.arnellconsulting.tps.repository.ProjectRepository;
import com.arnellconsulting.tps.repository.TimeEntryRepository;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import lombok.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implements the TpsService interface.
 *
 * @author jiar
 */
@Slf4j
@Service
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ShortVariable" })
public class TpsServiceImpl implements TpsService {
   private final transient @NonNull
   PersonRepository personRepository;
   private final transient @NonNull
   ProjectRepository projectRepository;
   private final transient @NonNull
   TimeEntryRepository timeEntryRepository;

   @Autowired
   public TpsServiceImpl(final PersonRepository personRepository,
                         final ProjectRepository projectRepository,
                         final TimeEntryRepository timeEntryRepository) {
      log.debug("ctor");
      this.personRepository = personRepository;
      this.projectRepository = projectRepository;
      this.timeEntryRepository = timeEntryRepository;
   }

   // ~ Project
   @Override
   public List<Project> getProjets() {
      return projectRepository.findAll();
   }

   @Override
   public Project getProject(final long id) {
      log.debug("getProjectById");
      return projectRepository.findOne(id);
   }

   @Override
   public void deleteProject(final long id) {
      projectRepository.delete(id);
   }

   @Override
   public void saveProject(final Project project) {
      projectRepository.save(project);
   }

   // ~ Person
   @Override
   public List<Person> getPersons() {
      return personRepository.findAll();
   }

   @Override
   public Person getPerson(final long id) {
      return personRepository.findOne(id);
   }

   @Override
   public void deletePerson(final long id) {
      personRepository.delete(id);
   }

   @Override
   public void savePerson(final Person person) {
      personRepository.save(person);
   }

   @Override
   public Person findPersonByEmail(final String email) {
      return personRepository.findByEmail(email);
   }

   // ~ TimeEntry
   @Override
   public List<TimeEntry> getTimeEntries() {
      return timeEntryRepository.findAll();
   }

   @Override
   public TimeEntry getTimeEntry(final long id) {
      return timeEntryRepository.findOne(id);
   }

   @Override
   public void deleteTimeEntry(final long id) {
      timeEntryRepository.delete(id);
   }

   @Override
   public void saveTimeEntry(final TimeEntry person) {
      timeEntryRepository.save(person);
   }

}
