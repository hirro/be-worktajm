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

import com.arnellconsulting.tps.domain.Person;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author hirro
 */
public class PersonUserDetails implements UserDetails {
   private String password;
   private String authority;
   private Person person;

   PersonUserDetails() {
      //
   }
   
   
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

   public Person getPerson() {
      return person;
   }

   public void setPassword(final String password) {
      this.password = password;
   }

   public void setPerson(final Person person) {
      this.person = person;
   }
}
