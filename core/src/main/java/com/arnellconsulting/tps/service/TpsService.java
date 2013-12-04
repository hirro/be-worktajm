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

import java.util.List;
import org.joda.time.DateTime;

/**
 * Separation between repositories.
 *
 * @author hirro
 */
@SuppressWarnings({ "PMD.UnusedModifier", "PMD.ShortVariable" })
public interface TpsService {

   // Project
   public List<Project> getProjets();
   public Project getProject(final long id);
   public void deleteProject(final long id);
   public void saveProject(final Project project);

   // Persons
   public Person getPerson(final long id);
   public List<Person> getPersons();
   public void deletePerson(final long id);
   public void savePerson(Person person);
   Person findPersonByEmail(final String email);

   // TimeEntry
   public List<TimeEntry> getTimeEntriesForPerson(final long userId, final DateTime from, final DateTime to);
   public TimeEntry getTimeEntry(final long id);
   public void deleteTimeEntry(final long id);
   public void saveTimeEntry(final TimeEntry timeEntry);

}
