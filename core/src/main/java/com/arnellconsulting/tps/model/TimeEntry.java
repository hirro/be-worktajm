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

import lombok.Data;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonManagedReference;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

/**
 * Time entries.
 * @author hirro
 */
@Entity
@SuppressWarnings("PMD")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeEntry extends AbstractPersistable<Long> {
   
   // Start date and time
   @NotNull
   @Temporal(TemporalType.TIMESTAMP)
   @DateTimeFormat(style = "M-")
   private Date  startTime;
   
   // End time
   // XXX: Change to duration?
   @Temporal(TemporalType.TIMESTAMP)
   @DateTimeFormat(style = "M-")
   private Date endTime;
   
   //
   private String comment;
   
   // Time entry must have one person.
   @ManyToOne
   @NotNull
   private Person person;
   
   // Time entry must have a project
   @ManyToOne
   @NotNull
   private Project project;

   public TimeEntry() {}

   public String getComment() {
      return comment;
   }

   public Date getEndTime() {
      return endTime;
   }

   @JsonIgnore
   @JsonBackReference("activeTimeEntry")
   public Person getPerson() {
      return person;
   }

   public Project getProject() {
      return project;
   }

   public Date getStartTime() {
      return startTime;
   }

   public void setComment(final String comment) {
      this.comment = comment;
   }

   public void setEndTime(final Date endTime) {
      this.endTime = endTime;
   }

   public void setPerson(final Person person) {
      this.person = person;
   }

   public void setProject(final Project project) {
      this.project = project;
   }

   public void setStartTime(final Date startTime) {
      this.startTime = startTime;
   }
}
