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
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.is;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author hirro
 */
public class TimeEntryTest {
   private static final ObjectMapper objectMapper = new ObjectMapper();
   
   @Test
   public void testAccessors() {
      final Person person = TestConstants.createPersonA();
      final Project project = TestConstants.createProjectA();
      final TimeEntry timeEntry = TestConstants.createTimeEntryA(person, project);
      assertThat(timeEntry.getComment(), is(TestConstants.TIMEENTRY_A_COMMENT));
      assertThat(timeEntry.getEndTime(), is(TestConstants.TIMEENTRY_A_END_TIME));
      assertThat(timeEntry.getStartTime(), is(TestConstants.TIMEENTRY_A_START_TIME));
      assertThat(timeEntry.getProject(), is(project));
      assertThat(timeEntry.getPerson(), is(person));
   }

}
