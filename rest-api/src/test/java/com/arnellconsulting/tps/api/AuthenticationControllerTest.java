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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
   private static final String PARAM_PASSWORD = "password";
   public static final String PARAM_USERNAME = "username";
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
      final PersonUserDetails validUserDetails = mock(PersonUserDetails.class);
      final Authentication authentication = mock(Authentication.class);
      when(validUserDetails.getUsername()).thenReturn(VALID);
      when(validUserDetails.getPassword()).thenReturn(VALID);
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
