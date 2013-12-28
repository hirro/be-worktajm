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
 *
 * @author hirro
 */
@Entity
@Table(name = "PERSON")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person extends AbstractPersistable<Long> {

   private static final long serialVersionUID = -3902301243341660214L;
   /**
    * This indicates whether the person has responded to the email verification.
    */
   private Boolean emailVerified = true;

   /**
    * A person may have one active time entry. This could be fetched from the database instead.
    */
   @ManyToOne
   private TimeEntry activeTimeEntry = null;

   /**
    * One person may have one or more time entries.
    */
   @OneToMany
   @JsonIgnore
   private Collection<TimeEntry> timeEntries;

   /**
    * First name of the person.
    */
   private String firstName;

   /**
    * Last name of the person
    */
   private String lastName;

   /**
    * Email of the person.
    */
   @NotNull
   @Column(unique = true)
   private String email;

   /**
    * Encrypted password of the person.
    */
   @NotNull
   private String password;

   /**
    * The projects owned by the person.
    */
   @OneToMany
   @JsonIgnore
   private Collection<Project> projects;

   public Person() {
      super();
      this.emailVerified = false;
   }

   public Boolean getEmailVerified() {
      return emailVerified;
   }

   public void setEmailVerified(final Boolean emailVerified) {
      this.emailVerified = emailVerified;
   }

   public TimeEntry getActiveTimeEntry() {
      return activeTimeEntry;
   }

   public void setActiveTimeEntry(final TimeEntry activeTimeEntry) {
      this.activeTimeEntry = activeTimeEntry;
   }

   public Collection<TimeEntry> getTimeEntries() {
      return timeEntries;
   }

   public void setTimeEntries(final Collection<TimeEntry> timeEntries) {
      this.timeEntries = timeEntries;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(final String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(final String lastName) {
      this.lastName = lastName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(final String email) {
      this.email = email;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(final String password) {
      this.password = password;
   }

   public Collection<Project> getProjects() {
      return projects;
   }

   public void setProjects(final Collection<Project> projects) {
      this.projects = projects;
   }
}
