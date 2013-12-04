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

package com.arnellconsulting.tps.security;

import com.arnellconsulting.tps.repository.PersonRepository;
import javax.servlet.FilterChain;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import static org.junit.Assert.*;

import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author jiar
 */
public class AuthenticationTokenProcessingFilterTest {
   AuthenticationManager authenticationManager;
   UserDetailsService userDetailsService;
   AuthenticationTokenProcessingFilter filter;
   HttpServletRequest request;
   HttpServletResponse response;
   FilterChain chain;
   
   @Test(expected=RuntimeException.class)
   public void testDoFilterWithInvalidParams() throws Exception {
      authenticationManager = Mockito.mock(AuthenticationManager.class);
      userDetailsService = Mockito.mock(UserDetailsService.class);
      filter = new AuthenticationTokenProcessingFilter(authenticationManager, userDetailsService);
      filter.doFilter(null, null, null);
   }
 
   @Test
   public void testDoFilter() throws Exception {
      authenticationManager = Mockito.mock(AuthenticationManager.class);
      userDetailsService = Mockito.mock(UserDetailsService.class);
      request = Mockito.mock(HttpServletRequest.class);
      response = Mockito.mock(HttpServletResponse.class);
      when(request.getHeader("Auth-Token")).thenReturn("jim@arnellconsulting.com:234234:234234");
      chain = Mockito.mock(FilterChain.class);
      filter = new AuthenticationTokenProcessingFilter(authenticationManager, userDetailsService);
      filter.doFilter(request, response, chain);
   }
   
}
