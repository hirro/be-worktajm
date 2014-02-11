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


package com.arnellconsulting.tps.rest.controllers;

import com.arnellconsulting.tps.rest.BaseController;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.rest.AccessDeniedException;
import com.arnellconsulting.tps.service.TpsService;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



/**
 * JSON API for Person.
 *
 * This object is intended to be used by corporate administrators.
 *
 * @author hirro
 */
@RestController
@RequestMapping("/person")
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ShortVariable" })
public class PersonController extends BaseController {

   private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

   @Autowired
   private transient TpsService tpsService;

   //~--- methods -------------------------------------------------------------

   /**
    * Create a user.
    * This operation may only be performed by an corporate administrator.
    *
    * The typical use case would be adding new employees to to the organization.
    *
    * @param person to create
    * @return created person
    */
   @Transactional
   @RequestMapping(method = RequestMethod.POST, headers = { "Accept=application/json" })
   @Secured("ROLE_ADMIN")
   public Person create(@RequestBody final Person person) {
      LOG.debug("create");
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
   public void delete(@PathVariable final Long id) {
      LOG.debug("delete id: {}", id);
      tpsService.deletePerson(id);
   }

   /**
    * Get the currently logged in person.
    * @param principal
    * @return list of persons
    * @throws com.arnellconsulting.tps.rest.AccessDeniedException
    */
   @Transactional
   @RequestMapping(method = RequestMethod.GET)
   public Person get(final Principal principal) throws AccessDeniedException {
      LOG.debug("get");
      final Person person = getAuthenticatedPerson(principal);
      return person;
   }

   /**
    * Retrieves information about the specified person.
    * @param id person id
    * @return the person with the provided id.
    */
   @Transactional
   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
   @Secured("ROLE_USER")
   public Person read(@PathVariable final Long id) {
      LOG.debug("read id: {}", id);

      final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

      if (auth == null) {
         LOG.debug("No user");
      } else {
         final String name = auth.getName();

         LOG.debug("Current user is {}", name);
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
      LOG.debug("update - email: {}", person.getEmail());
      tpsService.savePerson(person);
   }
}
