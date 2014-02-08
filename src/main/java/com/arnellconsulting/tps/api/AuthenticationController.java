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
import com.arnellconsulting.tps.security.AuthenticationToken;
import com.arnellconsulting.tps.service.TpsService;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



/**
 * JSON API for Person.
 *
 * This object is intended to be used by corporate administrators.
 *
 * @author hirro
 */
@RestController
@RequestMapping("/authenticate")
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ShortVariable" })
public class AuthenticationController extends BaseController {

   private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

   @Autowired
   private transient TpsService tpsService;

   /**
    * Authenticate the user
    *
    * The typical use case would be adding new employees to to the organization.
    *
    * @param principal the authenticated principal
    * @return created person
    */
   @Transactional
   @RequestMapping(method = RequestMethod.GET)
   @Secured("ROLE_USER")
   public AuthenticationToken authentcate(final Principal principal) throws AccessDeniedException {
      LOG.debug("authenticate");
      final Person person = getAuthenticatedPerson(principal);

      return new AuthenticationToken(person.getId());
   }

}
