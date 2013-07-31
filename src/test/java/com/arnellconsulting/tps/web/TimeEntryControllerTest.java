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
package com.arnellconsulting.tps.web;

import com.arnellconsulting.tps.config.TestContext;
import com.arnellconsulting.tps.config.WebAppContext;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.model.TimeEntry;
import com.arnellconsulting.tps.service.TpsService;
import static com.arnellconsulting.tps.web.ProjectControllerTest.PROJECT_1;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author jiar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebAppContext.class })
@WebAppConfiguration
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class TimeEntryControllerTest {
   public static final String TIMEENTRY_1 =
      "{\"id\":null,\"startTime\":1274392800000,\"endTime\":1262300400000,\"comment\":\"Comment\",\"project\":{\"id\":null,\"name\":\"Project Name\",\"description\":\"Project descri\",\"rate\":0,\"new\":true},\"new\":true}";
   private transient MockMvc mockMvc;
   private transient TimeEntry timeEntryA;
   private transient Person person;
   private transient Project project;
   private transient List<TimeEntry> timeEntries;
   @Autowired
   private transient TpsService tpsServiceMock;
   @Autowired
   private transient WebApplicationContext webApplicationContext;
   private static final DateTime END_TIME = new DateTime("2010-01-01");
   private static final DateTime START_TIME = new DateTime("2010-05-21");
   private static final String COMMENT = "Comment";
   private static final String FIRST_NAME = "First";
   private static final String LAST_NAME = "Last";
   private static final String EMAIL = "user@example.com";
   private static final String PROJECT_NAME = "Project Name";
   private static final String  PROJECT_DESCRIPTION = "Project descri";

   @Before
   public void setUp() {

      // We have to reset our mock between tests because the mock objects
      // are managed by the Spring container. If we would not reset them,
      // stubbing and verified behavior would "leak" from one test to another.
      Mockito.reset(tpsServiceMock);
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

      person = new Person();
      person.setFirstName(FIRST_NAME);
      person.setLastName(LAST_NAME);
      person.setEmail(EMAIL);
      person.setEmailVerified(Boolean.TRUE);
      
      project = new Project();
      project.setName(PROJECT_NAME);
      project.setDescription(PROJECT_DESCRIPTION);
      project.setRate(BigDecimal.ZERO);
      
      timeEntryA = new TimeEntry();
      timeEntryA.setEndTime(END_TIME.toDate());
      timeEntryA.setStartTime(START_TIME.toDate());
      timeEntryA.setComment(COMMENT);
      timeEntryA.setPerson(person);
      timeEntryA.setProject(project);
      
      timeEntries = new ArrayList<TimeEntry>();
      timeEntries.add(timeEntryA);
   }

   @Test
   public void testList() throws Exception {
      when(tpsServiceMock.getTimeEntries()).thenReturn(timeEntries);
      mockMvc.perform(get("/api/timeEntry").accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
      verify(tpsServiceMock, times(1)).getTimeEntries();
      verifyNoMoreInteractions(tpsServiceMock);      
   }

   @Test
   public void testCreate() throws Exception {
      mockMvc.perform(post("/api/timeEntry").content(TIMEENTRY_1).contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
      verify(tpsServiceMock, times(1)).saveTimeEntry(timeEntryA);
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testRead() throws Exception {
      when(tpsServiceMock.getTimeEntry(1)).thenReturn(timeEntryA);
      mockMvc.perform(get("/api/timeEntry/1").accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content().string(TIMEENTRY_1));
      verify(tpsServiceMock, times(1)).getTimeEntry(1L);
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testUpdate() throws Exception {
      mockMvc.perform(put("/api/timeEntry/1").content(PROJECT_1).contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNoContent());
      verify(tpsServiceMock, times(1)).saveTimeEntry(timeEntryA);
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testDelete() throws Exception {
      mockMvc.perform(delete("/api/timeEntry/1").accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isNoContent());
      verify(tpsServiceMock, times(1)).deleteTimeEntry(1L);
      verifyNoMoreInteractions(tpsServiceMock);
   }
}