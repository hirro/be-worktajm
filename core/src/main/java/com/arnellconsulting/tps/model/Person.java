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



package com.arnellconsulting.tps.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.Collection;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

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

   // Active time entry
   @ManyToOne
   TimeEntry activeTimeEntry = null;

   // All time entreis associated with the person.
   @OneToMany
   private Collection<TimeEntry> timeEntries;

   // First name
   private String firstName;

   // Last name
   private String lastName;
   @NotNull
   @Column(unique = true)
   private String email;

// @ManyToMany(cascade = CascadeType.ALL, mappedBy = "contracters")
// private Set<Contract> contracts = new HashSet<Contract>();
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

   @JsonIgnore
   public Collection<TimeEntry> getTimeEntries() {
      return timeEntries;
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

   public void setTimeEntries(Collection<TimeEntry> timeEntries) {
      this.timeEntries = timeEntries;
   }
}
