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
package com.arnellconsulting.worktajm.web.rest.v1;

import com.arnellconsulting.worktajm.domain.Person;
import com.arnellconsulting.worktajm.web.rest.v1.AccessDeniedException;
import com.arnellconsulting.worktajm.security.PersonUserDetails;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

/**
 *
 * @author hirro
 */
public class BaseController {

   private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
   
   public Person getAuthenticatedPerson(final Principal principal) throws AccessDeniedException {
      Person person = null;
      
      if (principal != null) {
         PersonUserDetails userDetails;
         userDetails = (PersonUserDetails) ((Authentication) principal).getPrincipal();
         if (userDetails != null) {
           person = userDetails.getPerson();
         }
      }
      if (person == null) {
         String error = "Failed to get principal";
         LOG.info(error);
         throw new AccessDeniedException(error);
      }
      return person;
   }
   
}
