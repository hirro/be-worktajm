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

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

   //
   // @Autowired
   // private TokenUtils tokenUtils;
   private final transient AuthenticationManager authManager;
   private final transient UserDetailsService userService;

   public AuthenticationTokenProcessingFilter(final AuthenticationManager authManager,
           final UserDetailsService userService) {
      super();
      this.authManager = authManager;
      this.userService = userService;
   }

   @Override
   public void doFilter(final ServletRequest request,
                        final ServletResponse response,
                        final FilterChain chain)
           throws IOException, ServletException {
      log.debug("doFilter");

      if (!(request instanceof HttpServletRequest)) {
         log.debug("Invalid request");

         throw new RuntimeException("Expecting a http request");
      }

      final HttpServletRequest httpRequest = (HttpServletRequest) request;
      final String authToken = httpRequest.getHeader("Auth-Token");
      final String userName = TokenUtils.getUserNameFromToken(authToken);

      if (userName != null) {
         log.debug("Read user name {} from token", userName);

         final UserDetails userDetails = this.userService.loadUserByUsername(userName);

         if (TokenUtils.validateToken(authToken, userDetails)) {
            final UsernamePasswordAuthenticationToken authentication =
               new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                    userDetails.getPassword());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
            SecurityContextHolder.getContext().setAuthentication(this.authManager.authenticate(authentication));
         } else {
            log.debug("Provided token was not valid, username {}", userName);
         }
      }

      chain.doFilter(request, response);
   }
}
