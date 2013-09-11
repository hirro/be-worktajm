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
package com.arnellconsulting.tps.api;

import com.arnellconsulting.tps.common.TestConstants;
import com.arnellconsulting.tps.config.TestContext;
import com.arnellconsulting.tps.config.WebAppContext;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.model.TimeEntry;
import com.arnellconsulting.tps.security.PersonUserDetails;
import com.arnellconsulting.tps.service.TpsService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class AuthenticationControllerTest {
   private static final String VALID = "valid";
   private static final String INVALID = "invalid";
   private static final String PASSWORD = "password";
   private transient MockMvc mockMvc;
   @Autowired
   private transient TpsService tpsServiceMock;
   @Autowired
   private transient WebApplicationContext webApplicationContext;
   @Autowired
   private transient UserDetailsService userDetailsService;
   @Autowired
   private transient AuthenticationManager authManager;
   private transient PersonUserDetails validUserDetails;
   private transient PersonUserDetails invalidUserDetails;
   
   @Before
   public void setUp() {
      // We have to reset our mock between tests because the mock objects
      // are managed by the Spring container. If we would not reset them,
      // stubbing and verified behavior would "leak" from one test to another.
      Mockito.reset(tpsServiceMock);
      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
      
      // Setup the valid user
      validUserDetails = mock(PersonUserDetails.class);
      when(validUserDetails.getUsername()).thenReturn(VALID);
      when(validUserDetails.getPassword()).thenReturn(PASSWORD);
      
      // Setup the invalid user
      invalidUserDetails = mock(PersonUserDetails.class);
      when(invalidUserDetails.getUsername()).thenReturn(INVALID);
      when(invalidUserDetails.getPassword()).thenReturn(PASSWORD);
   }   

   @Test
   public void testValidAuthentication() throws Exception {
      // Authentication manager must return an authentication object.
      final Authentication authentication = mock(Authentication.class);
      when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
      when(userDetailsService.loadUserByUsername(VALID)).thenReturn(validUserDetails);
      mockMvc.perform(
                  get("/api/authenticate")
                     .param(PASSWORD, PASSWORD)
                     .param("username", VALID))
              .andExpect(status().isOk());
      verifyNoMoreInteractions(tpsServiceMock);
   }

   // @Test
   public void testBadCredentials() throws Exception {
      when(userDetailsService.loadUserByUsername("unknown")).thenReturn(null);
      mockMvc.perform(
                  get("/api/authenticate")
                     .param(PASSWORD, "xxx")
                     .param("username", VALID))
              .andExpect(status().isUnauthorized());
      verify(tpsServiceMock, times(1)).getTimeEntry(1);
      verifyNoMoreInteractions(tpsServiceMock);
   }

//   @Test
   public void testInvalidUser() throws Exception {
      mockMvc.perform(
                  get("/api/authenticate")
                     .param(PASSWORD, "xxx")
                     .param("username", "invalid"))
              .andExpect(status().isUnauthorized());
      verify(tpsServiceMock, times(1)).getTimeEntry(1);
      verifyNoMoreInteractions(tpsServiceMock);
   }
}
