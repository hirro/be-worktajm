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
           @RequestParam(value = "company", required = false) final String company) throws Exception {
      log.debug("create: email {}, password: {}, company: {}  ", email, password, company);

      // Make sure person is not already registered
      if (tpsService.findPersonByEmail(email) == null) {
         // XXX
         throw new Exception("Person exists");
      } else {
         final Person person = new Person();
         person.setEmail(email);
         //person.setPassword(password);
         person.setLastName("Last name");
         person.setFirstName("First name");         
         tpsService.savePerson(person);
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
