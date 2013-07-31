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
import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.service.TpsService;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author jiar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration
public class ProjectControllerTest {
   public static final String PROJECT_1 = "{\"id\":null,\"name\":\"Project A\",\"description\":\"desc\",\"rate\":10.3,\"new\":true}";
   private MockMvc mockMvc;
   private Project projectA;
   List<Project> projects;

   @Autowired
   TpsService tpsServiceMock;

   @Autowired
   private WebApplicationContext webApplicationContext;
   
   @Before
   public void setUp() {
      // We have to reset our mock between tests because the mock objects
      // are managed by the Spring container. If we would not reset them,
      // stubbing and verified behavior would "leak" from one test to another.
      Mockito.reset(tpsServiceMock);
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

      projectA = new Project();
      projectA.setName("Project A");
      projectA.setRate(new BigDecimal("10.3"));
      projectA.setDescription("desc");

      projects = new ArrayList<Project>();
      projects.add(projectA);      
   }

   @Test
   public void testCreate() throws Exception {
      //when(tpsServiceMock.getProject(1)).thenReturn(projectA);
      mockMvc.perform(post("/api/project")
              .content(PROJECT_1)
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
      verify(tpsServiceMock, times(1)).saveProject(projectA);
      verifyNoMoreInteractions(tpsServiceMock);      
   } 
   
   @Test
   public void testRead() throws Exception {
      when(tpsServiceMock.getProject(1)).thenReturn(projectA);
      mockMvc.perform(get("/api/project/1")
              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content().string(PROJECT_1));
      verify(tpsServiceMock, times(1)).getProject(1L);
      verifyNoMoreInteractions(tpsServiceMock);      
   }
   
   @Test
   public void testUpdate() throws Exception {
      //when(tpsServiceMock.saveProject(projectA)).thenReturn(projectA);
      mockMvc.perform(put("/api/project/1")
              .content(PROJECT_1)
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNoContent());
      verify(tpsServiceMock, times(1)).saveProject(projectA);
      verifyNoMoreInteractions(tpsServiceMock);      
   } 
   
   @Test
   public void testDelete() throws Exception {
      when(tpsServiceMock.getProject(1)).thenReturn(projectA);
      mockMvc.perform(delete("/api/project/1")
              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isNoContent());
      verify(tpsServiceMock, times(1)).deleteProject(1L);
      verifyNoMoreInteractions(tpsServiceMock);      
   }     

   @Test
   public void testList() throws Exception {
      when(tpsServiceMock.getProjets()).thenReturn(projects);

      mockMvc.perform(get("/api/project")
              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
      verify(tpsServiceMock, times(1)).getProjets();
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testGetBadPath() throws Exception {
      mockMvc.perform(get("/api2/project/1")
              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
      verifyZeroInteractions(tpsServiceMock);
   }

   @Test
   public void testGetInvalidProject() throws Exception {
      mockMvc.perform(get("/api/project/2")
              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
      verify(tpsServiceMock, times(1)).getProject(2L);
      verifyNoMoreInteractions(tpsServiceMock);      
   }
   
}
