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

import com.arnellconsulting.tps.exception.AccessDeniedException;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.TimeEntry;
import com.arnellconsulting.tps.security.PersonUserDetails;
import com.arnellconsulting.tps.service.TpsService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
 * JSON API for TimeEntry.
 *
 * @author jiar
 */
@Controller
@RequestMapping("api/timeEntry")
@Slf4j
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ShortVariable" })
public class TimeEntryController {
   @Autowired
   private transient TpsService tpsService;
   @Autowired
   private transient PersonUserDetails userDetails;

   //~--- methods -------------------------------------------------------------

   /**
    * Creates a new time entry.
    * @param timeEntry
    * @return TimeEntry
    */
   @Transactional
   @RequestMapping(method = RequestMethod.POST, headers = { "Accept=application/json" })
   @ResponseBody
   @Secured("ROLE_USER")
   public TimeEntry create(@RequestBody final TimeEntry timeEntry) {
      log.debug("create: ");
      log.debug("User details {}", userDetails.getPerson());
      timeEntry.setPerson(userDetails.getPerson());
      tpsService.saveTimeEntry(timeEntry);

      return timeEntry;
   }

   /**
    * Deletes a time entry owner by the logged in person.
    * @param id logged in person
    */
   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @Secured("ROLE_USER")
   public void delete(@PathVariable final long id) {
      log.debug("delete id: {}", id);
      tpsService.deleteTimeEntry(id);
   }

   /**
    * List all time entries owned by the logged in user.
    * @return list of TimeEntry
    */
   @Transactional
   @RequestMapping(method = RequestMethod.GET)
   @ResponseBody
   @Secured("ROLE_USER")
   public List<TimeEntry> list() {
      Person person = userDetails.getPerson();
      log.debug("list as {}", person.getId());

      return tpsService.getTimeEntriesForPerson(person.getId());
   }

   /**
    * Read a time entry. May only be read by owner.
    * @param id of logged in user
    * @return TimeEntry
    * @throws AccessDeniedException if user is not authorized.
    */
   @Transactional
   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
   @ResponseBody
   @Secured("ROLE_USER")
   public TimeEntry read(@PathVariable final long id) throws AccessDeniedException {
      final Person person = userDetails.getPerson();
      log.debug("read id: {} as user: {}", id, person.getId());
      final TimeEntry timeEntry = tpsService.getTimeEntry(id);
      if (timeEntry.getPerson().getId() ==  person.getId()) {
         return timeEntry;
      } else {
         // Acccess denied
         log.error("Tried to access unauthorized item");
         throw new AccessDeniedException("Tried to access unauthorized item");
      }
   }

   /**
    * Update a time entry. A time entry may only be modified by a person which owns it.
    * @param id the id of the person
    * @param timeEntry the time entry to be updated
    * @throws AccessDeniedException if time entry does not belong to user.
    */
   @Transactional
   @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @Secured("ROLE_USER")
   public void update(@PathVariable final long id, @RequestBody final TimeEntry timeEntry) throws AccessDeniedException {
      final Person person = userDetails.getPerson();
      log.debug("update time entry with id: {} as person: {}", timeEntry.getId(), person.getId());
      
      final TimeEntry existingTimeEntry = tpsService.getTimeEntry(timeEntry.getId());
      if (existingTimeEntry.getPerson().getId() ==  person.getId()) {
         tpsService.saveTimeEntry(timeEntry);
      } else {
         // Acccess denied
         log.error("Tried to access unauthorized item");
         throw new AccessDeniedException("Tried to access unauthorized item");
      }
   }
}
