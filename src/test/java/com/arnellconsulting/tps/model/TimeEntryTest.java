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



package com.arnellconsulting.tps.model;

import org.codehaus.jackson.map.ObjectMapper;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

import static org.junit.Assert.*;

import java.io.IOException;

import java.util.Date;

/**
 *
 * @author jiar
 */
public class TimeEntryTest {
   private static final Date START_TIME = new Date();
   private static ObjectMapper objectMapper;

   public TimeEntryTest() {}

   //~--- methods -------------------------------------------------------------

   @After
   public void tearDown() {}

   @AfterClass
   public static void tearDownClass() {}

   @Test
   public void testDeserialization() throws IOException {
      final Person person = TestConstants.createPersonA();
      final Project project = TestConstants.createProjectA();
      final TimeEntry timeEntry = TestConstants.createTimeEntryA(person, project);
      final String actorsAsJson = objectMapper.writeValueAsString(timeEntry);

      assertThat(actorsAsJson, is(TestConstants.TIMEENTRY_A));
   }

   //~--- set methods ---------------------------------------------------------

   @BeforeClass
   public static void setUpClass() {
      objectMapper = new ObjectMapper();
   }
}
