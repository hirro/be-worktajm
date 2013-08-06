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



package com.arnellconsulting.tps.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;

/**
 *
 * @author jiar
 */
@Entity
public class PersonUserDetails extends Person implements UserDetails {
   private String password;
   private String authority;

   public PersonUserDetails(final Person person) {
      super();
      this.setFirstName(person.getFirstName());
      this.setLastName(person.getLastName());
      this.setEmail(person.getEmail());
      this.setEmailVerified(person.getEmailVerified());
      this.setActiveTimeEntry(person.getActiveTimeEntry());
   }

   //~--- get methods ---------------------------------------------------------

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      final ArrayList<GrantedAuthority> temp = new ArrayList<GrantedAuthority>();

      temp.add(new SimpleGrantedAuthority("ROLE_USER"));

      return temp;
   }

   @Override
   public String getUsername() {
      return this.getEmail();
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

   public String getAuthority() {
      return authority;
   }

   public String getPassword() {
      return password;
   }

   //~--- set methods ---------------------------------------------------------

   public void setAuthority(String authority) {
      this.authority = authority;
   }

   public void setPassword(String password) {
      this.password = password;
   }
}
