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


package com.arnellconsulting.tps.security;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author hirro
 */
public class TpsUserDetailsService implements UserDetailsService {
   private static final Logger LOG = LoggerFactory.getLogger(TpsUserDetailsService.class);
   private final transient PersonRepository repository;

   @Autowired
   public TpsUserDetailsService(final PersonRepository repository) {
      this.repository = repository;
   }

   //~--- methods -------------------------------------------------------------

   @Override
   public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
      final Person person = repository.findByEmail(username);
      if (person == null) {
         LOG.error("Failed loadUserByUsername({})", username);
         throw new UsernameNotFoundException(username);
      }

      LOG.debug("Loaded the user, firstName: {}, lastName: {}, password: PASSWORD", person.getFirstName(), person.getLastName());
      final PersonUserDetails details = new PersonUserDetails();
      details.setPerson(person);
      details.setPassword(person.getPassword());
      return details;
   }
}
