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


import java.util.List;
import org.joda.time.DateTime;

/**
 * Separation between repositories.
 *
 * @author hirro
 */
@SuppressWarnings({ "PMD.UnusedModifier", "PMD.ShortVariable" })
public interface TpsService {

   /**
    * Retrieve all the projects the current user is authorized for.
    * @return list of projects
    */
   public List<Project> getProjectsForPerson(final long id);
   
   /**
    * Retrieve the project with the specified project id.
    * @param id project id
    * @return project
    */
   public Project getProject(final long id);
   
   /**
    * Delete or disable project with the specified id.
    * Projects with time entries may not be deleted.
    * @param id project id.
    */
   public void deleteProject(final long id);
   
   /**
    * Save the project.
    * @param project 
    */
   public void saveProject(final Project project);

   /**
    * Get the person with the provided id.
    * Certain information may be restricted to person only.
    * @param id
    * @return person
    */
   public Person getPerson(final long id);
   
   /**
    * Get all persons?
    * @return a list of persons belonging to the same company as the logged in user.
    */
   public List<Person> getPersons();
   
   /**
    * Deletes a person with provided id.
    * May only be performed by administrators.
    * @param id 
    */
   public void deletePerson(final long id);
   
   /**
    * Save the person.
    * @param person 
    */
   public void savePerson(Person person);
   
   /**
    * Find person by email address.
    * @param email
    * @return person
    */
   Person findPersonByEmail(final String email);

   /**
    * Gets the time entry with the specified id.
    * @param id time entry id
    * @return TimeEntry
    */
   public TimeEntry getTimeEntry(final long id);
   
   /**
    * Deletes the time entry with the specified id
    * @param id time entry id
    */
   public void deleteTimeEntry(final long id);
   
   /**
    * Save the time entry.
    * @param timeEntry 
    */
   public void saveTimeEntry(final TimeEntry timeEntry);

   /**
    * Gets all the time entries for the given person.
    * @param userId user id.
    * @param from the date from which time entries should be retrieved.
    * @param to the date to which time entries should be retrieved.
    * @return list of time entries.
    */
   public List<TimeEntry> getTimeEntriesForPerson(final long userId, final DateTime from, final DateTime to);

   /**
    * Retrieve all the projects the current user is authorized for.
    * @return list of projects
    */
   public List<Customer> getCustomersForPerson(final long id);

   /**
    * Retrieve the customer with the specified customer id.
    *
    * @param id customer id
    * @return customer
    */
   public Customer getCustomer(final long id);

   /**
    * Delete or disable customer with the specified id.
    * Customers with time entries may not be deleted.
    * @param id project id.
    */
   public void deleteCustomer(final long id);

   /**
    * Save the customer.
    * @param customer
    */
   public void saveCustomer(final Customer customer);
}
