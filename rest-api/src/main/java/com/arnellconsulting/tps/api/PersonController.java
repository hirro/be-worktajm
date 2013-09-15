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

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.service.TpsService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * JSON API for Person.
 *
 * This object is intended to be used by corporate administrators.
 *
 * @author jiar
 */
@Controller
@RequestMapping("api/person")
@Slf4j
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ShortVariable" })
public class PersonController {
   @Autowired
   private transient TpsService tpsService;

   //~--- methods -------------------------------------------------------------

   /**
    * Create a user.
    * This operation may only be performed by an corporate administrator.
    *
    * The typical use case would be adding new employees to to the organization.
    *
    * @param person
    * @return
    */
   @Transactional
   @RequestMapping(method = RequestMethod.POST, headers = { "Accept=application/json" })
   @ResponseBody
   @Secured("ROLE_ADMIN")
   public Person create(@RequestBody final Person person) {
      log.debug("create");
      tpsService.savePerson(person);

      return person;
   }

   /**
    * Delete a user.
    * This operation may only be performed by an administrator of the corporate to which the user belongs.
    *
    * The typical use case would be removing a user which never has reported time for the organization.
    * Once a user has reported time, it may only be disabled not removed.
    *
    * @param id user id to remove
    */
   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @Secured("ROLE_ADMIN")
   public void delete(@PathVariable final long id) {
      log.debug("delete id: {}", id);
      tpsService.deletePerson(id);
   }

   /**
    * Lists all persons belonging to the organization of the logged in administrator.
    * @return list of persons
    */
   @Transactional
   @RequestMapping(method = RequestMethod.GET)
   @ResponseBody
   @Secured("ROLE_ADMIN")
   public List<Person> list() {
      log.debug("list");

      return tpsService.getPersons();
   }

   /**
    * Retrieves information about the specified person.
    * @param id
    * @return the person with the provided id.
    */
   @Transactional
   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
   @ResponseBody
   @Secured("ROLE_USER")
   public Person read(@PathVariable final long id) {
      log.debug("read id: {}", id);

      final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

      if (auth == null) {
         log.debug("No user");
      } else {
         final String name = auth.getName();

         log.debug("Current user is {}", name);
      }

      return tpsService.getPerson(id);
   }

   /**
    * Lets the logged in user update user profile information.
    *
    * Obviously, user may only update its own user information.
    *
    * @param person the updated user information
    */
   @Transactional
   @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @Secured("ROLE_USER")
   public void update(@RequestBody final Person person) {
      log.debug("update - email: {}", person.getEmail());
      tpsService.savePerson(person);
   }
}
