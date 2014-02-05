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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.arnellconsulting.tps.common.TestConstants;
import com.arnellconsulting.tps.config.TestContext;
import com.arnellconsulting.tps.config.WebAppContext;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.security.PersonUserDetails;
import com.arnellconsulting.tps.service.TpsService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 *
 * @author hirro
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebAppContext.class })
@WebAppConfiguration
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class PersonControllerTest {
   private transient MockMvc mockMvc;
   @Autowired
   private transient TpsService tpsServiceMock;
   @Autowired
   private transient WebApplicationContext webApplicationContext;
   private transient Person person;
   private transient List<Person> persons;

   @Autowired
   private transient PersonUserDetails personUserDetails;
   private UsernamePasswordAuthenticationToken principal;
   
   @Before
   public void prepare() {

      // We have to reset our mock between tests because the mock objects
      // are managed by the Spring container. If we would not reset them,
      // stubbing and verified behavior would "leak" from one test to another.
      Mockito.reset(tpsServiceMock);
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
      person = TestConstants.createPersonA();
      persons = new ArrayList<Person>();
      persons.add(person);

      when(personUserDetails.getPerson()).thenReturn(person);
      UsernamePasswordAuthenticationToken token;
      token = new UsernamePasswordAuthenticationToken(
         TestConstants.PERSON_A_EMAIL, 
         TestConstants.PERSON_A_PASSWORD);
      principal = spy(token);
      when(principal.getPrincipal()).thenReturn(personUserDetails);
      
   }

   @Test
   public void testCreate() throws Exception {
      mockMvc.perform(
      post("/person").content(TestConstants.PERSON_A_CREATE).contentType(MediaType.APPLICATION_JSON)).andExpect(
      status().isOk());
      final ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
      verify(tpsServiceMock, times(1)).savePerson(argument.capture());
      assertThat(argument.getValue().getEmail(), is(TestConstants.PERSON_A_EMAIL));
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testDelete() throws Exception {
      mockMvc.perform(delete("/person/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
      verify(tpsServiceMock, times(1)).deletePerson(1L);
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testGet() throws Exception {
      mockMvc.perform(
         get("/person")
            .accept(MediaType.APPLICATION_JSON)
            .principal(principal)
      ).andExpect(status().isOk());
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testRead() throws Exception {
      when(tpsServiceMock.getPerson(1)).thenReturn(person);
      mockMvc.perform(
         get("/person/1")
            .accept(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(content().contentType(TestConstants.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("email", is(person.getEmail())));              
      verify(tpsServiceMock, times(1)).getPerson(1L);
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testUpdate() throws Exception {
      mockMvc.perform(put("/person/1").content(TestConstants.PERSON_A).contentType(MediaType.APPLICATION_JSON)).andExpect(
      status().isNoContent());
      final ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
      verify(tpsServiceMock, times(1)).savePerson(argument.capture());
      final Person a = argument.getValue();
      assertThat(a.getEmail(), is(TestConstants.PERSON_A_EMAIL));
      verifyNoMoreInteractions(tpsServiceMock);
   }
}
