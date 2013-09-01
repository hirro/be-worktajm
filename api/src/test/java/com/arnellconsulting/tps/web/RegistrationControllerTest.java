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

import com.arnellconsulting.tps.common.TestConstants;
import com.arnellconsulting.tps.config.TestContext;
import com.arnellconsulting.tps.config.WebAppContext;
import com.arnellconsulting.tps.exception.EmailNotUniqueException;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.service.TpsService;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
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
      person.setFirstName(TestConstants.PERSON_A_FIRST_NAME);
      person.setLastName(TestConstants.PERSON_A_LAST_NAME);
      person.setEmail(TestConstants.PERSON_A_EMAIL);
      person.setEmailVerified(Boolean.TRUE);
   }

   @Test
   public void testCreate() throws Exception {
      when(tpsServiceMock.findPersonByEmail(TestConstants.PERSON_A_EMAIL)).thenReturn(null);
      final String path = String.format(TestConstants.CREATE_PATH, TestConstants.PERSON_A_EMAIL, TestConstants.PERSON_A_PASSWORD, TestConstants.COMPANY_A);
      mockMvc.perform(get(path).accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isMovedTemporarily());
      verify(tpsServiceMock, times(1)).findPersonByEmail(TestConstants.PERSON_A_EMAIL);
      verify(tpsServiceMock, times(1)).savePerson(any(Person.class));
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testCreateDuplicate() {
      when(tpsServiceMock.findPersonByEmail(TestConstants.PERSON_A_EMAIL)).thenReturn(person);
      final String path = String.format(TestConstants.CREATE_PATH, TestConstants.PERSON_A_EMAIL, TestConstants.PERSON_A_PASSWORD, TestConstants.COMPANY_A);
      try {
         mockMvc.perform(get(path).accept(MediaType.APPLICATION_JSON))
                 .andExpect(status().isInternalServerError());
      } catch (Exception ex) {
         fail("Should not throw here? Controller exception is not visible");
      }
      verify(tpsServiceMock, times(1)).findPersonByEmail(TestConstants.PERSON_A_EMAIL);
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
      when(tpsServiceMock.findPersonByEmail(TestConstants.PERSON_A_EMAIL)).thenReturn(person);
      final String path = String.format(TestConstants.CHECK_EMAIL_PATH, TestConstants.PERSON_A_EMAIL);
      mockMvc.perform(get(path).accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content().string("false"));
      verify(tpsServiceMock, times(1)).findPersonByEmail(TestConstants.PERSON_A_EMAIL);
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testIsEmailUniqueNotFound() throws Exception {
      when(tpsServiceMock.findPersonByEmail(TestConstants.PERSON_A_EMAIL)).thenReturn(null);
      final String path = String.format(TestConstants.CHECK_EMAIL_PATH, TestConstants.PERSON_A_EMAIL);
      mockMvc.perform(get(path).accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content().string("true"));
      verify(tpsServiceMock, times(1)).findPersonByEmail(TestConstants.PERSON_A_EMAIL);
      verifyNoMoreInteractions(tpsServiceMock);
   }
}
