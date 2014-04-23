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
package com.arnellconsulting.tps.common;


import com.arnellconsulting.tps.model.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;
import org.joda.time.LocalDateTime;

/**
 *
 * @author hirro
 */
public final class TestConstants {
   public static final Boolean ACCOUNT_NON_EXPIRED = Boolean.TRUE;
   public static final Boolean ACCOUNT_NON_LOCKED = Boolean.TRUE;
   public static final Boolean CREDENTIALS_NON_EXPIRED = Boolean.TRUE;
   public static final Boolean ENABLED = Boolean.TRUE;
   private static final Boolean PERSON_A_EMAIL_VERIFIED = true;
   public static final PersonStatus PERSON_A_STATUS = PersonStatus.NORMAL;
   public static final String CHECK_EMAIL_PATH = "/registration/isEmailAvailable?email=%s";
   public static final String COMPANY_A = "company A";

   // Person A
   public static final String PERSON_A_EMAIL = "a@example.com";
   public static final String PERSON_A_FIRST_NAME = "A";
   public static final String PERSON_A_LAST_NAME = "Ason";
   public static final String PERSON_A_PASSWORD = "PasswordA";
   public static final String PERSON_A_COMPANY = "User A company";
   public static final String PERSON_A_AUTHORITY = "Person A Auth";
   public static final String PERSON_A_CREATE = "{\"emailVerified\":true,\"firstName\":\"A\",\"lastName\":\"Ason\",\"email\":\"a@example.com\",\"new\":true}";
   public static final String PERSON_A = "{\"id\":null,\"emailVerified\":true,\"firstName\":\"A\",\"lastName\":\"Ason\",\"email\":\"a@example.com\",\"new\":true}";
   public static final String PERSON_A_READ = "{\"id\":null,\"emailVerified\":true,\"activeTimeEntry\":null,\"firstName\":\"A\",\"lastName\":\"Ason\",\"email\":\"a@example.com\",\"password\":\"PasswordA\",\"new\":true}";
   public static final String PERSON_A_REGISTER = "{\"firstName\":\"A\",\"lastName\":\"Ason\",\"email\":\"a@example.com\",\"password\":\"password\"}";

   // Project A
   public static final String PROJECT_A = "{\"id\":null,\"name\":\"Project A\",\"description\":\"Project A description\",\"rate\":10.3,\"new\":true}";
   private static final BigDecimal PROJECT_A_RATE = new BigDecimal("10.3");
   public static final String PROJECT_A_DESCRIPTION = "Project A description";
   private static final String PROJECT_A_NAME = "Project A";

   // Time Entry A
   private static final LocalDateTime TIMEENTRY_A_END_TIME = new LocalDateTime(1262300400000L);
   private static final LocalDateTime TIMEENTRY_A_START_TIME = new LocalDateTime(1274392800000L);
   public static final String TIMEENTRY_A_CREATE = "{\"startTime\":1274392800000,\"endTime\":1262300400000,\"comment\":\"Time Entry A comment\",\"new\":true}";
   public static final String TIMEENTRY_A_COMMENT = "Time Entry A comment";
   public static final String TIMEENTRY_A_UPDATE = "{\"id\":1,\"startTime\":1274392800000,\"endTime\":1262300400000,\"comment\":\"Time Entry A comment\",\"new\":true}";

   // Address A
   private static final String ADDRESS_A_CITY = "Punxsutawney";
   private static final String ADDRESS_A_LINE1 = "102 West Mahoning Street";
   private static final String ADDRESS_A_LINE2 = "";
   private static final String ADDRESS_A_COUNTRY = "US";
   private static final String ADDRESS_A_ZIP = "15767";
   private static final String ADDRESS_A_STATE = "PA";

   // Customer A
   private static final String CUSTOMER_A_REFERENCE_PERSON = "Phil";
   private static final String CUSTOMER_A_NAME = "Phil and Co";
   public static final String CUSTOMER_A_JSON_UPDATE = "{\"id\":1,\"name\":\"Phil and Co\",\"billingAddress\":{\"line1\":\"102 West Mahoning Street\",\"line2\":\"\",\"city\":\"Punxsutawney\",\"state\":\"PA\",\"country\":\"US\",\"zip\":\"15767\"},\"referencePerson\":\"Phil\"}";
   public static final String CUSTOMER_A_JSON_CREATE = "{\"name\":\"Phil and Co\",\"billingAddress\":{\"line1\":\"102 West Mahoning Street\",\"line2\":\"\",\"city\":\"Punxsutawney\",\"state\":\"PA\",\"country\":\"US\",\"zip\":\"15767\"},\"referencePerson\":\"Phil\",\"new\":true}";
   public static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";

   private TestConstants() {
   }

   public static Person createPersonA() {
      final Person person = new Person();
      person.setEmail(PERSON_A_EMAIL);
      person.setFirstName(PERSON_A_FIRST_NAME);
      person.setLastName(PERSON_A_LAST_NAME);
      person.setEmailVerified(PERSON_A_EMAIL_VERIFIED);
      person.setPassword(PERSON_A_PASSWORD);
      person.setProjects(null);
      return person;
   }

   public static Project createProjectA() {
      final Project project = new Project();
      project.setName(PROJECT_A_NAME);
      project.setDescription(PROJECT_A_DESCRIPTION);
      project.setRate(PROJECT_A_RATE);
      return project;
   }

   public static TimeEntry createTimeEntryA(final Person person, final Project project) {
      final TimeEntry timeEntryA = new TimeEntry();
      timeEntryA.setEndTime(TIMEENTRY_A_END_TIME);
      timeEntryA.setStartTime(TIMEENTRY_A_START_TIME);
      timeEntryA.setComment(TIMEENTRY_A_COMMENT);
      timeEntryA.setPerson(person);
      timeEntryA.setProject(project);
      return timeEntryA;
   }

   private static Address createAddressA() {
      final Address address = new Address();
      address.setCity(ADDRESS_A_CITY);
      address.setCountry(ADDRESS_A_COUNTRY);
      address.setLine1(ADDRESS_A_LINE1);
      address.setLine2(ADDRESS_A_LINE2);
      address.setState(ADDRESS_A_STATE);
      address.setZip(ADDRESS_A_ZIP);
      return address;
   }

   public static Customer createCustomerA() {
      Customer customer = new Customer(1L);
      customer.getId();
      customer.setName(CUSTOMER_A_NAME);
      customer.setReferencePerson(CUSTOMER_A_REFERENCE_PERSON);
      customer.setBillingAddress(createAddressA());
      Collection<Project> projects = new Vector<Project>();
      projects.add(createProjectA());
      customer.setProjects(projects);
      return customer;
   }
}
