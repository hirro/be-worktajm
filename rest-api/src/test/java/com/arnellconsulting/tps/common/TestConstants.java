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
package com.arnellconsulting.tps.common;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.model.TimeEntry;
import java.math.BigDecimal;
import org.joda.time.DateTime;

/**
 *
 * @author jiar
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
   public static final String CHECK_EMAIL_PATH = "/api/registration/checkEmail.do?email=%s";
   public static final String COMPANY_A = "company A";
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
