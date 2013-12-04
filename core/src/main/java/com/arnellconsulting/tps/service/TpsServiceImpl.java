/*
 * Copyright 2013 Jim Arnell
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.arnellconsulting.tps.service;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.model.TimeEntry;
import com.arnellconsulting.tps.repository.PersonRepository;
import com.arnellconsulting.tps.repository.ProjectRepository;
import com.arnellconsulting.tps.repository.TimeEntryRepository;

import lombok.extern.slf4j.Slf4j;

import lombok.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.joda.time.DateTime;

/**
 * Implements the TpsService interface.
 *
 * @author hirro
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
   public List<TimeEntry> getTimeEntriesForPerson(final long userId,
                                                  final DateTime from,
                                                  final DateTime to) {
     return timeEntryRepository.findByPersonIdAndStartTimeBetween(userId, from.toDate(), to.toDate());
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
