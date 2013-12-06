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
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 * TBD
 * @author hirro
 */
@Entity
@SuppressWarnings("PMD")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person extends AbstractPersistable<Long> {
   private static final long serialVersionUID = -3902301243341660214L;

   /**
    * This indicates whether the person has responded to the email verification.
    */
   @Getter @Setter private Boolean emailVerified = true;

   /**
    * A person may have one active time entry.
    * This could be fetched from the database instead.
    */
   @ManyToOne
   @Getter @Setter TimeEntry activeTimeEntry = null;

   /**
    * One person may have one or more time entries.
    */
   @OneToMany
   @JsonIgnore   
   @Getter @Setter private Collection<TimeEntry> timeEntries;

   /**
    * First name of the person.
    */
   @Getter @Setter private String firstName;

   /**
    * Last name of the person
    */
   @Getter @Setter private String lastName;
   
   /**
    * Email of the person.
    */
   @NotNull
   @Column(unique = true)
   @Getter @Setter private String email;

   /**
    * Encrypted password of the person.
    */
   @NotNull
   @Getter @Setter private String password;

   /**
    * The projects owned by the person.
    */
   @OneToMany
   @JsonBackReference(value = "person->project")
   @Getter @Setter private Collection<Project> projects;
           
   
   public Person() {
      this.emailVerified = false;
   }

}
