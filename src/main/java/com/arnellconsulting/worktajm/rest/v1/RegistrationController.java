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



package com.arnellconsulting.worktajm.rest.v1;

import com.arnellconsulting.worktajm.domain.Person;
import com.arnellconsulting.worktajm.rest.v1.InvalidParameterExeception;
import com.arnellconsulting.worktajm.rest.v1.Registration;
import com.arnellconsulting.worktajm.rest.v1.Registration;
import com.arnellconsulting.worktajm.service.TpsService;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles registration of new users.
 * 
 * @author hirro
 */
@RestController
@RequestMapping("/registration")
public class RegistrationController {

   private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

   @Autowired
   private transient TpsService tpsService;

   StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder();
   
   @Transactional
   @RequestMapping(method = RequestMethod.POST, headers = { "Accept=application/json" })
   public String create(@RequestBody final Registration registration) throws Exception {
      LOG.debug("create: email {}, ...", registration.getEmail());

      // Check all required values
      if (registration.getEmail() == null) {
         LOG.warn("Email must be specified");
         throw new InvalidParameterExeception("Person must be specified!");
      }

      // Check uniqueness
      if (tpsService.findPersonByEmail(registration.getEmail()) != null) {
         throw new InvalidParameterExeception("Email is already registered");
      }

      // Normal processing
      final Person person = new Person();

      person.setEmail(registration.getEmail());
      person.setLastName(registration.getLastName());
      person.setFirstName(registration.getFirstName());
      person.setPassword(passwordEncoder.encode(registration.getPassword()));

      tpsService.savePerson(person);

      return "redirect:/";
   }

   //~--- get methods ---------------------------------------------------------

   @RequestMapping(value = "/isEmailAvailable")
   public boolean isEmailAvailable(final HttpServletResponse response, @RequestParam final String email) {
      LOG.debug("isEmailAvailable: {}", email);

      final Person person = tpsService.findPersonByEmail(email);

      return (person == null);
   }
}
