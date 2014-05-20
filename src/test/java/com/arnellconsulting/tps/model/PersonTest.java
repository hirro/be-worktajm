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


package com.arnellconsulting.tps.model;

import com.arnellconsulting.tps.domain.TimeEntry;
import com.arnellconsulting.tps.domain.Project;
import com.arnellconsulting.tps.domain.Person;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.is;

/**
 *
 * @author hirro
 */
public class PersonTest {

   @Test
   public void testGetters() {
     final Person person = TestConstants.createPersonA();
     final Project project = TestConstants.createProjectA();
     final TimeEntry timeEntry = TestConstants.createTimeEntryA(person, project);
     person.setActiveTimeEntry(timeEntry);
     assertThat(person.getFirstName(), is(TestConstants.PERSON_A_FIRST_NAME));
     assertThat(person.getLastName(), is(TestConstants.PERSON_A_LAST_NAME));
     assertThat(person.getEmail(), is(TestConstants.PERSON_A_EMAIL));
     assertThat(person.getPassword(), is(TestConstants.PERSON_A_PASSWORD));
     assertThat(person.getEmailVerified(), is(TestConstants.PERSON_A_EMAIL_VERIFIED));
     assertThat(person.getActiveTimeEntry(), is(timeEntry));
     assertNull(person.getTimeEntries());
     assertNull(person.getProjects());
  }
}
