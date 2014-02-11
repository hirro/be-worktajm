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

import com.arnellconsulting.tps.model.Customer;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.model.TimeEntry;
import com.arnellconsulting.tps.repository.PersonRepository;
import com.arnellconsulting.tps.repository.ProjectRepository;
import com.arnellconsulting.tps.repository.TimeEntryRepository;
import com.arnellconsulting.tps.repository.CompanyRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Implements the TpsService interface.
 *
 * @author hirro
 */
@Service
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ShortVariable" })
public class TpsServiceImpl implements TpsService {
   private static final Logger LOG = LoggerFactory.getLogger(TpsServiceImpl.class);

   private final transient PersonRepository personRepository;
   private final transient ProjectRepository projectRepository;
   private final transient TimeEntryRepository timeEntryRepository;
   private final transient CompanyRepository customerRepository;

   @Autowired
   public TpsServiceImpl(final PersonRepository personRepository,
                         final ProjectRepository projectRepository,
                         final TimeEntryRepository timeEntryRepository,
                         final CompanyRepository companyRepository) {
      LOG.debug("ctor");
      this.personRepository = personRepository;
      this.projectRepository = projectRepository;
      this.timeEntryRepository = timeEntryRepository;
      this.customerRepository = companyRepository;
   }

   // ~ Project
   @Override
   public List<Project> getProjectsForPerson(final long id) {
      return projectRepository.findByPersonId(id);
   }

   @Override
   public Project getProject(final long id) {
      LOG.debug("getProjectById");
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
      // First get the current person's password
      Person current = personRepository.findOne(person.getId());
      if (current != null) {
         person.setPassword(current.getPassword());      
      }
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
   public List<Customer> getCustomersForPerson(long id) {
      return customerRepository.findByPersonId(id);
   }

   @Override
   public Customer getCustomer(long id) {
      return customerRepository.findOne(id);
   }

   @Override
   public void deleteCustomer(long id) {
      customerRepository.delete(id);
   }

   @Override
   public void saveCustomer(Customer customer) {
      customerRepository.save(customer);
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
