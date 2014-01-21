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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.springframework.data.jpa.domain.AbstractPersistable;

import java.math.BigDecimal;

import java.util.Collection;

import javax.persistence.*;

import javax.validation.constraints.NotNull;


import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Project POJO
 * 
 * @author hirro
 */
@Entity
@Table(name="tps_project",
        uniqueConstraints={
           @UniqueConstraint(columnNames = {"name", "person_id"}, name="idx_project_person")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends TpsObject {
   private static final long serialVersionUID = -3902305943341540214L;

   /**
    * Project name.
    */
   @NotNull
   private String name;

   /**
    * Project description.
    */
   private String description;

   /**
    * Charging for this project.
    */
   private BigDecimal rate;

   /**
    * Time entries associated to this project.
    */
   @OneToMany(mappedBy="project")
   @JsonIgnore
   private Collection<TimeEntry> timeEntries;

   /**
    * The person who owns this project.
    */
   @ManyToOne
   @JoinColumn(name="person_id")
   @NotNull
   @JsonIgnore
   private Person person;

   /**
    * The optional customer who runs this project.
    */
   private Long customerId;

   public String getName() {
      return name;
   }

   public void setName(final String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(final String description) {
      this.description = description;
   }

   public BigDecimal getRate() {
      return rate;
   }

   public void setRate(final BigDecimal rate) {
      this.rate = rate;
   }

   public Collection<TimeEntry> getTimeEntries() {
      return timeEntries;
   }

   public void setTimeEntries(final Collection<TimeEntry> timeEntries) {
      this.timeEntries = timeEntries;
   }

   public Person getPerson() {
      return person;
   }

   public void setPerson(final Person person) {
      this.person = person;
   }

   public Long getCustomerId() {
      return customerId;
   }

   public void setCustomerId(final Long customerId) {
      this.customerId = customerId;
   }

}
