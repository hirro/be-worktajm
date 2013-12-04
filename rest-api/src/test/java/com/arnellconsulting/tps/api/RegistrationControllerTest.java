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

import static org.junit.Assert.fail;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test
 * @author hirro
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

   //~--- methods -------------------------------------------------------------

   @Test
   public void testCreate() throws Exception {
      when(tpsServiceMock.findPersonByEmail(TestConstants.PERSON_A_EMAIL)).thenReturn(null);
      mockMvc.perform(get("/api/registration")
              .param("password", TestConstants.PERSON_A_PASSWORD)
              .param("email", TestConstants.PERSON_A_EMAIL)
              .param("company", TestConstants.COMPANY_A)
              .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
      verify(tpsServiceMock, times(1)).findPersonByEmail(TestConstants.PERSON_A_EMAIL);
      verify(tpsServiceMock, times(1)).savePerson(any(Person.class));
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testCreateDuplicate() {
      when(tpsServiceMock.findPersonByEmail(TestConstants.PERSON_A_EMAIL)).thenReturn(person);

      try {
         mockMvc.perform(
         get("/api/registration")
                 .param("password", TestConstants.PERSON_A_PASSWORD)
                 .param("email", TestConstants.PERSON_A_EMAIL)
                 .param("company", TestConstants.COMPANY_A)
                 .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
      } catch (Exception ex) {
         fail("Should not throw here? Controller exception is not visible");
      }

      verify(tpsServiceMock, times(1)).findPersonByEmail(TestConstants.PERSON_A_EMAIL);
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testIndex() throws Exception {
      mockMvc.perform(get("/api/registration").accept(MediaType.APPLICATION_JSON)).andExpect(
      status().isBadRequest());
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testIsEmailUniqueFound() throws Exception {
      when(tpsServiceMock.findPersonByEmail(TestConstants.PERSON_A_EMAIL)).thenReturn(person);

      final String path = String.format(TestConstants.CHECK_EMAIL_PATH, TestConstants.PERSON_A_EMAIL);

      mockMvc.perform(get(path).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(
      content().string("false"));
      verify(tpsServiceMock, times(1)).findPersonByEmail(TestConstants.PERSON_A_EMAIL);
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testIsEmailUniqueNotFound() throws Exception {
      when(tpsServiceMock.findPersonByEmail(TestConstants.PERSON_A_EMAIL)).thenReturn(null);

      final String path = String.format(TestConstants.CHECK_EMAIL_PATH, TestConstants.PERSON_A_EMAIL);

      mockMvc.perform(get(path).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(
      content().string("true"));
      verify(tpsServiceMock, times(1)).findPersonByEmail(TestConstants.PERSON_A_EMAIL);
      verifyNoMoreInteractions(tpsServiceMock);
   }

   //~--- set methods ---------------------------------------------------------

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
}
