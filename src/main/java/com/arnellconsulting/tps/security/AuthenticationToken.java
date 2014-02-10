/*
 * Copyright 2014 Jim Arnell
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

/**
 * Contains information retrieved during authentication.
 * 
 * @author jiar
 */
public class AuthenticationToken {
   private final Long id;
   private final String email;
   
   public AuthenticationToken(final Person person) {
      this.id = person.getId();
      this.email = person.getEmail();
   }
   
   public final long getId() {
      return id;
   }
   
   public final String getEmail() {
      return email;
   }
}
