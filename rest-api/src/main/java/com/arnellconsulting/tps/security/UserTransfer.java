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

import java.util.Map;

/**
 *
 * @author hirro
 */
public class UserTransfer {
   private final transient String name;
   private final transient Map<String, Boolean> roles;
   private final transient String token;

   public UserTransfer(final String userName, final Map<String, Boolean> roles, final String token) {
      this.name = userName;
      this.roles = roles;
      this.token = token;
   }

   public String getName() {
      return this.name;
   }

   public Map<String, Boolean> getRoles() {
      return this.roles;
   }

   public String getToken() {
      return this.token;
   }
}
