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



package com.arnellconsulting.tps.api;

import com.arnellconsulting.tps.common.TestConstants;
import com.arnellconsulting.tps.config.TestContext;
import com.arnellconsulting.tps.config.WebAppContext;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.model.TimeEntry;
import com.arnellconsulting.tps.security.PersonUserDetails;
import com.arnellconsulting.tps.service.TpsService;

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

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.mockito.ArgumentCaptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 *
 * @author hirro
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebAppContext.class })
@WebAppConfiguration
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class ProjectControllerTest {
   private transient MockMvc mockMvc;
   private transient Project projectA;
   private transient List<Project> projects;
   private transient Person person1;
   private transient Person person2;
   
   @Autowired
   private transient TpsService tpsServiceMock;
   @Autowired
   private transient WebApplicationContext webApplicationContext;

   @Autowired
   private transient PersonUserDetails personUserDetails;
   private UsernamePasswordAuthenticationToken principal;
   private UsernamePasswordAuthenticationToken token;
  
   @Before
   public void setUp() {

      // We have to reset our mock between tests because the mock objects
      // are managed by the Spring container. If we would not reset them,
      // stubbing and verified behavior would "leak" from one test to another.
      Mockito.reset(tpsServiceMock);
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
      person1 = spy(TestConstants.createPersonA());
      person2 = spy(TestConstants.createPersonA());
      when(person1.getId()).thenReturn(1L);
      when(person2.getId()).thenReturn(2L);
      when(personUserDetails.getPerson()).thenReturn(person1);
      token = new UsernamePasswordAuthenticationToken(TestConstants.PERSON_A_EMAIL, TestConstants.PERSON_A_PASSWORD);
      principal = spy(token);
      when(principal.getPrincipal()).thenReturn(personUserDetails);
      
      // Projects
      projectA = TestConstants.createProjectA();      
   }

   @Test
   public void testCreate() throws Exception {
      when(tpsServiceMock.getProjectsForPerson(1)).thenReturn(projects);
      
      mockMvc.perform(
         post("/api/project")
         .content(TestConstants.PROJECT_A)
         .contentType(MediaType.APPLICATION_JSON)
         .principal(principal))
      .andExpect(status().isOk());

      final ArgumentCaptor<Project> argument = ArgumentCaptor.forClass(Project.class);
      verify(tpsServiceMock, times(1)).saveProject(argument.capture());
      assertThat(argument.getValue().getDescription(), is(TestConstants.PROJECT_A_DESCRIPTION)); 
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testRead() throws Exception {
      when(tpsServiceMock.getProject(1L)).thenReturn(projectA);
      mockMvc.perform(
         get("/api/project/1")
         .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().string(TestConstants.PROJECT_A));
      
      verify(tpsServiceMock, times(1)).getProject(1L);
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testUpdate() throws Exception {
      mockMvc.perform(
         put("/api/project/1")
         .content(TestConstants.PROJECT_A)
         .contentType(MediaType.APPLICATION_JSON)
      )
      .andExpect(status()
      .isNoContent());

      final ArgumentCaptor<Project> argument = ArgumentCaptor.forClass(Project.class);
      verify(tpsServiceMock, times(1)).saveProject(argument.capture());
      final Project project = argument.getValue();
      assertThat(project.getDescription(), is(TestConstants.PROJECT_A_DESCRIPTION));
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testDelete() throws Exception {
      mockMvc.perform(
         delete("/api/project/1")
         .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status()
      .isNoContent());
      verify(tpsServiceMock, times(1)).deleteProject(1L);
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testList() throws Exception {
      when(tpsServiceMock.getProjectsForPerson(1)).thenReturn(projects);
      mockMvc.perform(
         get("/api/project")
         .accept(MediaType.APPLICATION_JSON)
         .principal(principal)
      )
      .andExpect(status().isOk());

      verify(tpsServiceMock, times(1)).getProjectsForPerson(1);
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testGetBadPath() throws Exception {
      mockMvc.perform(
         get("/api2/project/1")
         .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());

      verifyZeroInteractions(tpsServiceMock);
   }

   @Test
   public void testGetInvalidProject() throws Exception {
      mockMvc.perform(
         get("/api/project/2")
         .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk());

      verify(tpsServiceMock, times(1)).getProject(2L);
      verifyNoMoreInteractions(tpsServiceMock);
   }
}
