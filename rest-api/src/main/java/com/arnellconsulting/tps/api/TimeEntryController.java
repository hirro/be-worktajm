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

import com.arnellconsulting.tps.exception.AccessDeniedException;
import com.arnellconsulting.tps.exception.InvalidParameterExeception;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.TimeEntry;
import com.arnellconsulting.tps.security.PersonUserDetails;
import com.arnellconsulting.tps.service.TpsService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;
import java.util.Date;

import java.util.List;
import org.joda.time.DateTime;

/**
 * JSON API for TimeEntry.
 *
 * @author jiar
 */
@Controller
@RequestMapping("api/timeEntry")
@Slf4j
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ShortVariable" })
public class TimeEntryController extends BaseController {

   @Autowired
   private transient TpsService tpsService;

   //~--- methods -------------------------------------------------------------

   /**
    * Creates a new time entry for the logged in user.
    *
    * @param timeEntry
    * @return TimeEntry
    */
   @Transactional
   @RequestMapping(method = RequestMethod.POST, headers = { "Accept=application/json" })
   @ResponseBody
   @Secured("ROLE_USER")
   public TimeEntry create(@RequestBody final TimeEntry timeEntry, final Principal principal) {
      // Logged in person
      final Person person = getAuthenticatedPerson(principal);

      log.debug("create TimeEntry as user: {}", person.getId());
      timeEntry.setPerson(person);
      tpsService.saveTimeEntry(timeEntry);

      return timeEntry;
   }

   /**
    * Deletes a time entry belonging to the logged in user.
    * It may only be deleted by the person owning it.
    *
    * @param id logged in person
    */
   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @Secured("ROLE_USER")
   public void delete(@PathVariable final long id, final Principal principal) {
      // Logged in person
      final Person person = getAuthenticatedPerson(principal);

      log.debug("delete time entry with id: {} as user: {}", id, person.getId());
      tpsService.deleteTimeEntry(id);
   }

   /**
    * List all time entries belonging to the logged in user.
    *
    * @param from optional from date
    * @param to optional to date
    * @return list of TimeEntry
    */
   @Transactional
   @RequestMapping(method = RequestMethod.GET)
   @ResponseBody
   @Secured("ROLE_USER")
   public List<TimeEntry> list(@RequestParam(value = "from", required = false) final String fromDate,
                               @RequestParam(value = "to", required = false) final String toDate,
                               final Principal principal) {
      // Logged in person
      final Person person = getAuthenticatedPerson(principal);
   
      // Parse start time, default to 0
      DateTime from = new DateTime(0);
      if (fromDate != null) {
         from = DateTime.parse(fromDate);
      }

      // Parse to time, default to current time
      DateTime to = new DateTime();
      if (toDate != null) {
         to = DateTime.parse(toDate);
      }
      log.debug("list time entries as user: {}", person.getId());

      return tpsService.getTimeEntriesForPerson(person.getId(), from, to);
   }

   /**
    * Reads a time entry.
    *
    * @param id of logged in user
    * @return TimeEntry
    * @throws AccessDeniedException if user is not authorized.
    */
   @Transactional
   @RequestMapping(value = "/{id}", method = RequestMethod.GET)
   @ResponseBody
   @Secured("ROLE_USER")
   public TimeEntry read(@PathVariable final long id, final Principal principal) throws AccessDeniedException {
      final Person person = getAuthenticatedPerson(principal);

      log.debug("read id: {} as user: {}", id, person.getId());

      // Only return time entry if logged in person is the owner of the object.
      final TimeEntry timeEntry = tpsService.getTimeEntry(id);
      if (timeEntry.getPerson().getId() == person.getId()) {
         return timeEntry;
      } else {
         log.error("Tried to access unauthorized item");
         throw new AccessDeniedException("Tried to access unauthorized item");
      }
   }

   /**
    * Update a time entry.
    * A time entry may only be modified by a person which owns it.
    *
    * @param id the id of the person
    * @param timeEntry the time entry to be updated
    * @throws AccessDeniedException if time entry does not belong to user.
    */
   @Transactional
   @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @Secured("ROLE_USER")
   public void update(@PathVariable final long id,
                      @RequestBody final TimeEntry timeEntry,
                      final Principal principal)
           throws AccessDeniedException, InvalidParameterExeception {
      final Person person = getAuthenticatedPerson(principal);

      // Make sure it if the time entry already exists, it is owned by the person logged in
      // before it is updated.
      final TimeEntry existingTimeEntry = tpsService.getTimeEntry(timeEntry.getId());
      if (existingTimeEntry == null) {
         log.debug("No item to update");
         throw new InvalidParameterExeception("No item to update");
      } else if (existingTimeEntry.getPerson().getId() == person.getId()) {
         log.debug("Updating time entry with id: {} as person: {}", timeEntry.getId(), person.getId());
         timeEntry.setPerson(person);
         tpsService.saveTimeEntry(timeEntry);
      } else {
         // Acccess denied
         log.error("Tried to access unauthorized item");
         throw new AccessDeniedException("Tried to access unauthorized item");
      }
   }

}
