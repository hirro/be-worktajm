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

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.service.TpsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Handles registration of new users.
 * 
 * @author hirro
 */
@Controller
@RequestMapping("/api/registration")
public class RegistrationController {

   private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

   @Autowired
   private transient TpsService tpsService;

   //~--- methods -------------------------------------------------------------

   @Transactional
   @RequestMapping(method = RequestMethod.GET)
   @ResponseBody
   public String create(@RequestParam(value = "email", required = false) final String email,
                        @RequestParam(value = "password", required = false) final String password,
                        @RequestParam(value = "company", required = false) final String company)
           throws Exception {
      LOG.debug("create: email {}, password: {}, company: {}  ", email, password, company);

      // Check all required values
      if (email == null) {
         LOG.warn("Email must be specified");
         throw new InvalidParameterExeception("Person must be specified!");
      }

      // Check uniqueness
      if (tpsService.findPersonByEmail(email) != null) {
         throw new InvalidParameterExeception("Email is already registered");
      }

      // Normal processing
      final Person person = new Person();

      person.setEmail(email);
      person.setLastName("Last name");
      person.setFirstName("First name");
      person.setPassword(password);

      tpsService.savePerson(person);

      return "redirect:/";
   }

   //~--- get methods ---------------------------------------------------------

   @RequestMapping(value = "/isEmailAvailable")
   @ResponseBody
   public boolean isEmailAvailable(final HttpServletResponse response, @RequestParam final String email) {
      LOG.debug("isEmailAvailable: {}", email);

      final Person person = tpsService.findPersonByEmail(email);

      return (person == null);
   }
}
