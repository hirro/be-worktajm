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

import lombok.Data;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 * TBD
 * @author jiar
 */
@Entity
@SuppressWarnings("PMD")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person extends AbstractPersistable<Long> implements UserDetails {

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getAuthority() {
      return authority;
   }

   public void setAuthority(String authority) {
      this.authority = authority;
   }

   public Boolean getEmailVerified() {
      return emailVerified;
   }

   public void setEmailVerified(Boolean emailVerified) {
      this.emailVerified = emailVerified;
   }

   public TimeEntry getActiveTimeEntry() {
      return activeTimeEntry;
   }

   public void setActiveTimeEntry(TimeEntry activeTimeEntry) {
      this.activeTimeEntry = activeTimeEntry;
   }
   private static final long serialVersionUID = -3902301243341660214L;

// @ManyToMany(cascade = CascadeType.ALL, mappedBy = "contracters")
// private Set<Contract> contracts = new HashSet<Contract>();
// @OneToMany(cascade = CascadeType.ALL, mappedBy = "Person")
// private Set<TimeEntry> contract = new HashSet<TimeEntry>();
   private String firstName;
   private String lastName;
   private String password;
   @NotNull
   @Column(unique = true)
   private String email;
   private String authority;
   private Boolean emailVerified = true;
   @ManyToOne
   @JsonManagedReference
   TimeEntry activeTimeEntry = null;

// @ManyToOne
// private Corporate employer;

   public Person() {
      this.emailVerified = false;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      ArrayList<GrantedAuthority> temp = new ArrayList<GrantedAuthority>();

      temp.add(new SimpleGrantedAuthority("ROLE_USER"));

      return temp;
   }

   @Override
   public String getUsername() {
      return email;
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
}
