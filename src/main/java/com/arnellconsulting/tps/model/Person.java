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


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 * TBD
 * @author jiar
 */
@Entity
@SuppressWarnings("PMD")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person extends AbstractPersistable<Long> {
   private static final long serialVersionUID = -3902301243341660214L;

   private Boolean emailVerified = true;
   @ManyToOne
   @JsonIgnore
   TimeEntry activeTimeEntry = null;

// @ManyToMany(cascade = CascadeType.ALL, mappedBy = "contracters")
// private Set<Contract> contracts = new HashSet<Contract>();
   @OneToMany
   @JsonBackReference(value="timeentry->person")
   //@JsonIgnore
   private Collection<TimeEntry> contract;
   private String firstName;
   private String lastName;
   @NotNull
   @Column(unique = true)
   private String email;

// @ManyToOne
// private Corporate employer;
   public Person() {
      this.emailVerified = false;
   }

   //~--- get methods ---------------------------------------------------------

   public TimeEntry getActiveTimeEntry() {
      return activeTimeEntry;
   }

   public String getEmail() {
      return email;
   }

   public Boolean getEmailVerified() {
      return emailVerified;
   }

   public String getFirstName() {
      return firstName;
   }

   public String getLastName() {
      return lastName;
   }

   //~--- set methods ---------------------------------------------------------

   public void setActiveTimeEntry(TimeEntry activeTimeEntry) {
      this.activeTimeEntry = activeTimeEntry;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public void setEmailVerified(Boolean emailVerified) {
      this.emailVerified = emailVerified;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }
}
