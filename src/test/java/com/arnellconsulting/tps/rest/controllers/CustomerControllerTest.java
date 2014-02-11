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



package com.arnellconsulting.tps.rest.controllers;

import com.arnellconsulting.tps.common.TestConstants;
import com.arnellconsulting.tps.config.TestContext;
import com.arnellconsulting.tps.config.WebAppContext;
import com.arnellconsulting.tps.model.Customer;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author hirro
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebAppContext.class })
@WebAppConfiguration
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class CustomerControllerTest {
   private transient MockMvc mockMvc;
   private transient Customer customerA;
   private transient List<Customer> customers;

    @Autowired
   private transient TpsService tpsServiceMock;
   @Autowired
   private transient WebApplicationContext webApplicationContext;

   @Autowired
   private transient PersonUserDetails personUserDetails;
   private UsernamePasswordAuthenticationToken principal;

    @Before
   public void setUp() {

      // We have to reset our mock between tests because the mock objects
      // are managed by the Spring container. If we would not reset them,
      // stubbing and verified behavior would "leak" from one test to another.
      Mockito.reset(tpsServiceMock);
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Person person1 = spy(TestConstants.createPersonA());
        Person person2 = spy(TestConstants.createPersonA());
      when(person1.getId()).thenReturn(1L);
      when(person2.getId()).thenReturn(2L);
      when(personUserDetails.getPerson()).thenReturn(person1);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(TestConstants.PERSON_A_EMAIL, TestConstants.PERSON_A_PASSWORD);
      principal = spy(token);
      when(principal.getPrincipal()).thenReturn(personUserDetails);

      // Customers
      customerA = TestConstants.createCustomerA();
   }

   @Test
   public void testCreate() throws Exception {
      when(tpsServiceMock.getCustomersForPerson(1)).thenReturn(customers);

      mockMvc.perform(
         post("/customer")
            .content(TestConstants.CUSTOMER_A_JSON_CREATE)
            .contentType(MediaType.APPLICATION_JSON)
            .principal(principal))
         .andExpect(status().isOk());

      final ArgumentCaptor<Customer> argument = ArgumentCaptor.forClass(Customer.class);
      verify(tpsServiceMock, times(1)).saveCustomer(argument.capture());
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testRead() throws Exception {
      when(tpsServiceMock.getCustomer(1L)).thenReturn(customerA);
      mockMvc.perform(
         get("/customer/1")
            .accept(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(content().contentType(TestConstants.APPLICATION_JSON_UTF8))
         .andExpect(jsonPath("name", is(customerA.getName())));              

      verify(tpsServiceMock, times(1)).getCustomer(1L);
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testUpdate() throws Exception {
      mockMvc.perform(
         put("/customer/1")
            .content(TestConstants.CUSTOMER_A_JSON_UPDATE)
            .contentType(MediaType.APPLICATION_JSON)
            .principal(principal))
         .andExpect(status().isNoContent());

      final ArgumentCaptor<Customer> argument = ArgumentCaptor.forClass(Customer.class);
      verify(tpsServiceMock, times(1)).saveCustomer(argument.capture());
      final Customer customer = argument.getValue();
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testDelete() throws Exception {
      mockMvc.perform(
         delete("/customer/1")
            .accept(MediaType.APPLICATION_JSON))
         .andExpect(status().isNoContent());
      verify(tpsServiceMock, times(1)).deleteCustomer(1L);
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testList() throws Exception {
      when(tpsServiceMock.getCustomersForPerson(1)).thenReturn(customers);
      mockMvc.perform(
         get("/customer")
            .accept(MediaType.APPLICATION_JSON)
            .principal(principal))
         .andExpect(status().isOk());

      verify(tpsServiceMock, times(1)).getCustomersForPerson(1);
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testGetBadPath() throws Exception {
      mockMvc.perform(
         get("/api2/customer/1")
            .accept(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk());

      verifyZeroInteractions(tpsServiceMock);
   }

   @Test
   public void testGetInvalidCustomer() throws Exception {
      mockMvc.perform(
         get("/customer/2")
            .accept(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk());

      verify(tpsServiceMock, times(1)).getCustomer(2L);
      verifyNoMoreInteractions(tpsServiceMock);
   }
}
