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

import com.arnellconsulting.tps.security.PersonUserDetails;
import com.arnellconsulting.tps.security.TokenUtils;
import com.arnellconsulting.tps.security.UserTransfer;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author hirro
 */
@Controller
@RequestMapping("api/authenticate")
@Slf4j
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ShortVariable" })
public class AuthenticationController {
   @Autowired
   private transient UserDetailsService userDetailsService;
   @Autowired
   @Qualifier("authenticationManager")
   private transient AuthenticationManager authenticationManager;

   @RequestMapping(method = RequestMethod.GET)
   @ResponseBody
   public UserTransfer authenticate(@RequestParam final String username, @RequestParam final String password) {
      final UsernamePasswordAuthenticationToken token;
      final Authentication authentication;
      
      log.debug("authenticate, username: {}, password: ******", username);

      try {
         token = new UsernamePasswordAuthenticationToken(username, password);
         authentication = authenticationManager.authenticate(token);

         SecurityContextHolder.getContext().setAuthentication(authentication);

         /*
          * Reload user as password of authentication principal will be null after authorization and
          * password is needed for token generation
          */
         log.debug("Authenticaton succeeded, loading user details");

         final PersonUserDetails userDetails = (PersonUserDetails) userDetailsService.loadUserByUsername(username);
         final Map<String, Boolean> roles = new HashMap<String, Boolean>();

         for (GrantedAuthority authority : userDetails.getAuthorities()) {
            log.debug("  authority: {}", authority);
            roles.put(authority.toString(), Boolean.TRUE);
         }

         return new UserTransfer(userDetails.getPerson().getId(), 
                                 roles, 
                                 TokenUtils.createToken(userDetails));
      } catch (AuthenticationException e) {
         log.error("Failed to authenticate user: {}", username);
         throw e;
      }
   }
}
