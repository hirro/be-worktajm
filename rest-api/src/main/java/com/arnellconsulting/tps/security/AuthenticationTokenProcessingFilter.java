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



package com.arnellconsulting.tps.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

   private static final Logger LOG = LoggerFactory.getLogger(AuthenticationTokenProcessingFilter.class);

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
      LOG.debug("doFilter");

      if (!(request instanceof HttpServletRequest)) {
         LOG.debug("Invalid request");

         throw new RuntimeException("Expecting a http request");
      }

      final HttpServletRequest httpRequest = (HttpServletRequest) request;
      final String authToken = httpRequest.getHeader("Auth-Token");
      final String userName = TokenUtils.getUserNameFromToken(authToken);

      if (userName != null) {
         LOG.debug("Read user name {} from token", userName);

         final UserDetails userDetails = this.userService.loadUserByUsername(userName);

         if (TokenUtils.validateToken(authToken, userDetails)) {
            final UsernamePasswordAuthenticationToken authentication =
               new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                    userDetails.getPassword());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
            SecurityContextHolder.getContext().setAuthentication(this.authManager.authenticate(authentication));
         } else {
            LOG.debug("Provided token was not valid, username {}", userName);
         }
      }

      chain.doFilter(request, response);
   }
}
