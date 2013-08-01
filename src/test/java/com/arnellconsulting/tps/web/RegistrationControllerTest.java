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
import com.arnellconsulting.tps.service.TpsService;
import static com.arnellconsulting.tps.web.TimeEntryControllerTest.TIMEENTRY_1;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author jiar
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebAppContext.class })
@WebAppConfiguration
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class RegistrationControllerTest {
   private static final String EMAIL = "a@example.com";
   private static final String FIRST_NAME = "Jim";
   private static final String LAST_NAME = "Arnell";
   private static final String PASSWORD = "password";
   private static final String COMPANY = "company";
   public static final String CREATE_PATH = "/registration/create?email=%s&password=%s&company=%s";
   public static final String CHECK_EMAIL_PATH = "/registration/checkEmail.do?email=%s";

   private transient MockMvc mockMvc;
   @Autowired
   private transient TpsService tpsServiceMock;
   @Autowired
   private transient WebApplicationContext webApplicationContext;

   private transient Person person;

   @Before
   public void setUp() {
      Mockito.reset(tpsServiceMock);
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
      person = new Person();
      person.setFirstName(FIRST_NAME);
      person.setLastName(LAST_NAME);
      person.setEmail(EMAIL);
      person.setEmailVerified(Boolean.TRUE);
   }

   @Test
   public void testCreate() throws Exception {
      when(tpsServiceMock.findPersonByEmail(EMAIL)).thenReturn(person);
      final String path = String.format(CREATE_PATH, EMAIL, PASSWORD, COMPANY);
      mockMvc.perform(get(path).accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testIndex() throws Exception {
      mockMvc.perform(get("/registration/").accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testRegister() throws Exception {
      mockMvc.perform(get("/registration/register").accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testIsEmailUniqueFound() throws Exception {
      when(tpsServiceMock.findPersonByEmail(EMAIL)).thenReturn(person);
      final String path = String.format(CHECK_EMAIL_PATH, EMAIL);
      mockMvc.perform(get(path).accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content().string("false"));
      verify(tpsServiceMock, times(1)).findPersonByEmail(EMAIL);
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testIsEmailUniqueNotFound() throws Exception {
      when(tpsServiceMock.findPersonByEmail(EMAIL)).thenReturn(null);
      final String path = String.format(CHECK_EMAIL_PATH, EMAIL);
      mockMvc.perform(get(path).accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content().string("true"));
      verify(tpsServiceMock, times(1)).findPersonByEmail(EMAIL);
      verifyNoMoreInteractions(tpsServiceMock);
   }
}
