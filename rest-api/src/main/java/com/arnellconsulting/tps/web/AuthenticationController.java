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



package com.arnellconsulting.tps.web;

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

/**
 *
 * @author jiar
 */
@Controller
@RequestMapping("api/authenticate")
@Slf4j
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ShortVariable" })
public class AuthenticationController {
   @Autowired
   private transient UserDetailsService userService;
   @Autowired
   @Qualifier("authenticationManager")
   private transient AuthenticationManager authManager;

   @RequestMapping(method = RequestMethod.GET)
   @ResponseBody
   public UserTransfer authenticate(@RequestParam final String username, @RequestParam final String password) {
      log.debug("authenticate, username: {}, password: ******", username);

      final UsernamePasswordAuthenticationToken authenticationToken;

      authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
      log.debug("Authenticating...");

      final Authentication authentication = this.authManager.authenticate(authenticationToken);

      SecurityContextHolder.getContext().setAuthentication(authentication);

      /*
       * Reload user as password of authentication principal will be null after authorization and
       * password is needed for token generation
       */
      log.debug("Reloading user details");

      final UserDetails userDetails = this.userService.loadUserByUsername(username);
      final Map<String, Boolean> roles = new HashMap<String, Boolean>();

      for (GrantedAuthority authority : userDetails.getAuthorities()) {
         log.debug("  authority: {}", authority);
         roles.put(authority.toString(), Boolean.TRUE);
      }

      return new UserTransfer(userDetails.getUsername(), roles, TokenUtils.createToken(userDetails));
   }
}
