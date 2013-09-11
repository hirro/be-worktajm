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

import java.util.List;

/**
 * Separation between repositories.
 *
 * @author jiar
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
   public List<TimeEntry> getTimeEntriesForPerson(final long userId);
   public TimeEntry getTimeEntry(final long id);
   public void deleteTimeEntry(final long id);
   public void saveTimeEntry(final TimeEntry timeEntry);

   // Corporate
   public boolean findCorporate(String company);
}
