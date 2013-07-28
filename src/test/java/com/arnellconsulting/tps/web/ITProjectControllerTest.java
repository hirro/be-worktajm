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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jiar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration
public class ITProjectControllerTest {
   public static final String PROJECT_1 = "[{\"id\":1,\"description\":\"Lorem ipsum\",\"title\":\"Foo\"},{\"id\":2,\"description\":\"Lorem ipsum\",\"title\":\"Bar\"}]";
   private MockMvc mockMvc;

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
   }

   @Test
   public void testGet() throws Exception {
      Project projectA = new Project();

      projectA.setName("Project A");
      when(tpsServiceMock.getProject(1)).thenReturn(projectA);
      mockMvc.perform(get("/api/project/1")
              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
              //.andExpect(content().string(PROJECT_1));
   }

   @Test
   public void testList() throws Exception {
      // Setup mock
      Project projectA = new Project();
      projectA.setName("Project A");
      List<Project> projects = new ArrayList<Project>();
      projects.add(projectA);
      when(tpsServiceMock.getProjets()).thenReturn(projects);

      mockMvc.perform(get("/api/project")
              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
   }
}
