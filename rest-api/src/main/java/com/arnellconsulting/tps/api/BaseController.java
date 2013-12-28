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
import com.arnellconsulting.tps.security.PersonUserDetails;
import java.security.Principal;
import org.springframework.security.core.Authentication;

/**
 *
 * @author hirro
 */
public class BaseController {

   Person getAuthenticatedPerson(final Principal principal) throws AccessDeniedException {
      Person person = null;
      
      if (principal != null) {
         PersonUserDetails userDetails;
         userDetails = (PersonUserDetails) ((Authentication) principal).getPrincipal();
         if (userDetails != null) {
           person = userDetails.getPerson();
         }
      }
      if (person == null) {
         throw new AccessDeniedException("Failed to map principal to person");
      }
      return person;
   }
   
}
