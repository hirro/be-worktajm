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



package com.arnellconsulting.tps.web;

import com.arnellconsulting.tps.exception.EmailNotUniqueException;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.service.TpsService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/registration/")
@Slf4j
public class RegistrationController {
   @Autowired
   private transient TpsService tpsService;

   @Transactional
   @RequestMapping("/create")
   public String create(
           @RequestParam(value = "email", required = false) final String email,
           @RequestParam(value = "password", required = false) final String password,
           @RequestParam(value = "company", required = false) final String company) throws EmailNotUniqueException {
      log.debug("create: email {}, password: {}, company: {}  ", email, password, company);

      // Make sure person is not already registered
      if (tpsService.findPersonByEmail(email) == null) {
         final Person person = new Person();
         person.setEmail(email);
         //person.setPassword(password);
         person.setLastName("Last name");
         person.setFirstName("First name");
         tpsService.savePerson(person);
      } else {
         throw new EmailNotUniqueException("Person already exists " + email);
      }

      return "redirect:/";
   }

   @RequestMapping("/")
   public String index() {
      log.debug("index");

      return null;
   }

   @RequestMapping("/register")
   public String register() {
      log.debug("register");

      return null;
   }

   @RequestMapping(value = "/checkEmail.do")
   @ResponseBody
   public boolean isEmailUnique(final HttpServletResponse response, @RequestParam final String email) {
      log.debug("isEmailUnique: {}", email);

      final Person person = tpsService.findPersonByEmail(email);

      return (person == null);
   }
}
