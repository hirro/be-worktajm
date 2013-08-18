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



package com.arnellconsulting.tps.security;

import com.arnellconsulting.tps.model.Person;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author jiar
 */
public class PersonUserDetails implements UserDetails {
   private String password;
   private String authority;
   private Person person;

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      final ArrayList<GrantedAuthority> temp = new ArrayList<GrantedAuthority>();

      temp.add(new SimpleGrantedAuthority("ROLE_USER"));

      return temp;
   }

   @Override
   public String getPassword() {
      return password;
   }

   @Override
   public String getUsername() {
      return person.getEmail();
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

   public Person getPerson() {
      return person;
   }

   public void setAuthority(final String authority) {
      this.authority = authority;
   }

   public void setPassword(final String password) {
      this.password = password;
   }

   public void setPerson(final Person person) {
      this.person = person;
   }
}
