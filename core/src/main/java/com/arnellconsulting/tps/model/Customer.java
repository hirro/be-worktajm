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

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "CUSTOMER",
       uniqueConstraints = {
         @UniqueConstraint(columnNames = {"name", "person"})
       })
public class Customer extends AbstractPersistable<Long> {

   private static final long serialVersionUID = -390230121353660214L;

   /**
    * Customer name, mandatory.
    */
   @NotNull
   private String name;

   /**
    * Billings address, mandatory.
    */
   @NotNull
   private Address billingAddress;

   /**
    * Reference person, optional.
    */
   private String referencePerson;

   /**
    * The projects owned by the project.
    */
   @OneToMany
   @JoinColumn(name="customerId")
   @JsonIgnore
   private Collection<Project> projects;

   /**
    * The owner of the entry
    *
    */
   @ManyToOne
   @JsonIgnore
   private Person person;

   public String getName() {
      return name;
   }

   public void setName(final String name) {
      this.name = name;
   }

   public Address getBillingAddress() {
      return billingAddress;
   }

   public void setBillingAddress(final Address billingAddress) {
      this.billingAddress = billingAddress;
   }

   public String getReferencePerson() {
      return referencePerson;
   }

   public void setReferencePerson(final String referencePerson) {
      this.referencePerson = referencePerson;
   }

   public Collection<Project> getProjects() {
      return projects;
   }

   public void setProjects(final Collection<Project> projects) {
      this.projects = projects;
   }

   public Person getPerson() {
      return person;
   }

   public void setPerson(final Person person) {
      this.person = person;
   }   
}
