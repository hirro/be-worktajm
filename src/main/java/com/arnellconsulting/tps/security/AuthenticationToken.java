/*
 * Copyright 2014 jiar.
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
