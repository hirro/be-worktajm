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

import com.arnellconsulting.tps.config.TestContext;
import com.arnellconsulting.tps.config.WebAppContext;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.security.PersonUserDetails;
import com.arnellconsulting.tps.service.TpsService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author hirro
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebAppContext.class })
@WebAppConfiguration
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class AuthenticationControllerTest {
   private static final String VALID = "valid";
   private static final String INVALID = "invalid";
   private static final String PARAM_PASSWORD = "password";
   private static final String PARAM_USERNAME = "username";
   private transient MockMvc mockMvc;
   @Autowired
   private transient TpsService tpsServiceMock;
   @Autowired
   private transient WebApplicationContext webApplicationContext;
   @Autowired
   private transient UserDetailsService userDetailsService;
   @Autowired
   private transient AuthenticationManager authenticationManager;

   @Before
   public void setUp() {

      // We have to reset our mock between tests because the mock objects
      // are managed by the Spring container. If we would not reset them,
      // stubbing and verified behavior would "leak" from one test to another.
      Mockito.reset(tpsServiceMock);
      Mockito.reset(userDetailsService);
      Mockito.reset(authenticationManager);
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
      
   }

   @Test
   public void testValidAuthentication() throws Exception {
      final Person validPerson = mock(Person.class);
      when(validPerson.getId()).thenReturn(1l);
      final PersonUserDetails validUserDetails = mock(PersonUserDetails.class);
      final Authentication authentication = mock(Authentication.class);
      when(validUserDetails.getUsername()).thenReturn(VALID);
      when(validUserDetails.getPassword()).thenReturn(VALID);
      when(validUserDetails.getPerson()).thenReturn(validPerson);
      when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
      when(userDetailsService.loadUserByUsername(VALID)).thenReturn(validUserDetails);

      mockMvc.perform(get("/api/authenticate")
              .param(PARAM_PASSWORD, VALID)
              .param(PARAM_USERNAME, VALID))
            .andExpect(status().isOk());
      verifyNoMoreInteractions(tpsServiceMock);
   }

   @Test
   public void testBadCredentials() throws Exception {
      final Authentication authentication = mock(Authentication.class);
      final PersonUserDetails invalidUserDetails = mock(PersonUserDetails.class);
      when(invalidUserDetails.getUsername()).thenReturn(INVALID);
      when(invalidUserDetails.getPassword()).thenReturn(INVALID);
      when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);      
      when(userDetailsService.loadUserByUsername(INVALID)).thenThrow(new UsernameNotFoundException("itest"));

      mockMvc.perform(get("/api/authenticate")
              .param(PARAM_PASSWORD, INVALID)
              .param(PARAM_USERNAME, INVALID))
            .andExpect(status().isInternalServerError());
      verifyNoMoreInteractions(tpsServiceMock);
   }

}
