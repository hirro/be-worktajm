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

import com.arnellconsulting.tps.common.PersonStatus;
import java.math.BigDecimal;
import org.joda.time.DateTime;

/**
 *
 * @author hirro
 */
public final class TestConstants {
   public static final BigDecimal PROJECTA_A_RATE = new BigDecimal("10.3");
   public static final Boolean ACCOUNT_NON_EXPIRED = Boolean.TRUE;
   public static final Boolean ACCOUNT_NON_LOCKED = Boolean.TRUE;
   public static final Boolean CREDENTIALS_NON_EXPIRED = Boolean.TRUE;
   public static final Boolean ENABLED = Boolean.TRUE;
   public static final Boolean PERSON_A_EMAIL_VERIFIED = true;
   public static final DateTime TIMEENTRY_A_END_TIME = new DateTime("2010-01-01");
   public static final DateTime TIMEENTRY_A_START_TIME = new DateTime("2010-05-21");
   public static final PersonStatus PERSON_A_STATUS = PersonStatus.NORMAL;
   public static final String CHECK_EMAIL_PATH = "/registration/checkEmail.do?email=%s";
   public static final String COMPANY_A = "company A";
   public static final String CREATE_PATH = "/registration/create?email=%s&password=%s&company=%s";
   public static final String PERSON_A_EMAIL = "a@example.com";
   public static final String PERSON_A_FIRST_NAME = "A";
   public static final String PERSON_A_LAST_NAME = "Ason";
   public static final String PERSON_A_PASSWORD = "PasswordA";
   public static final String PERSON_A_AUTHORITY = "Person A Auth";
   public static final String PROJECT_A = "{\"id\":null,\"name\":\"Project A\",\"description\":\"Project A description\",\"rate\":10.3,\"new\":true}";
   public static final String PROJECT_A_DESCRIPTION = "Project A description";
   public static final String PROJECT_A_NAME = "Project A";
   public static final String TIMEENTRY_A_READ = "{\"id\":null,\"startTime\":1274392800000,\"endTime\":1262300400000,\"comment\":\"Time Entry A comment\",\"project\":{\"id\":null,\"name\":\"Project A\",\"description\":\"Project A description\",\"rate\":10.3,\"new\":true},\"new\":true}";
   public static final String TIMEENTRY_A_CREATE = "{\"startTime\":1274392800000,\"endTime\":1262300400000,\"comment\":\"Time Entry A comment\",\"new\":true}";
   public static final String TIMEENTRY_A_COMMENT = "Time Entry A comment";
   public static final String PERSON_A_CREATE = "{\"emailVerified\":true,\"firstName\":\"A\",\"lastName\":\"Ason\",\"email\":\"a@example.com\",\"new\":true}";
   public static final String PERSON_A = "{\"id\":null,\"emailVerified\":true,\"firstName\":\"A\",\"lastName\":\"Ason\",\"email\":\"a@example.com\",\"new\":true}";
   public static final String PERSON_A_READ = "{\"id\":null,\"emailVerified\":true,\"activeTimeEntry\":null,\"firstName\":\"A\",\"lastName\":\"Ason\",\"email\":\"a@example.com\",\"new\":true}";

   private TestConstants() {
   }

   public static Person createPersonA() {
      final Person person = new Person();
      person.setEmail(PERSON_A_EMAIL);
      person.setFirstName(PERSON_A_FIRST_NAME);
      person.setLastName(PERSON_A_LAST_NAME);
      person.setEmailVerified(PERSON_A_EMAIL_VERIFIED);
      return person;
   }

   public static Project createProjectA() {
      final Project project = new Project();
      project.setName(PROJECT_A_NAME);
      project.setDescription(PROJECT_A_DESCRIPTION);
      project.setRate(PROJECTA_A_RATE);
      return project;
   }

   public static TimeEntry createTimeEntryA(final Person person, final Project project) {
      final TimeEntry timeEntryA = new TimeEntry();
      timeEntryA.setEndTime(TIMEENTRY_A_END_TIME.toDate());
      timeEntryA.setStartTime(TIMEENTRY_A_START_TIME.toDate());
      timeEntryA.setComment(TIMEENTRY_A_COMMENT);
      timeEntryA.setPerson(person);
      timeEntryA.setProject(project);
      return timeEntryA;
   }
}
