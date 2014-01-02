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

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

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
@Table(name = "tps_time_entry")
@SuppressWarnings("PMD")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeEntry extends AbstractPersistable<Long> {

   /**
    * Start date and time.
    */
   @NotNull
   @Temporal(TemporalType.TIMESTAMP)
   @DateTimeFormat(style = "M-")
   private Date  startTime;

   /**
    * End date and time
    */
   @Temporal(TemporalType.TIMESTAMP)
   @DateTimeFormat(style = "M-")
   private Date endTime;

   /**
    * Optional comment for the time entry.
    */
   private String comment;

   /**
    * The person who owns this time entry.
    */
   @ManyToOne
   @JoinColumn(name="person_id")
   @NotNull
   @JsonIgnore
   @JsonBackReference("activeTimeEntry")
   private Person person;

   /**
    * The project that is associated with the time entry.
    */
   @ManyToOne
   @JoinColumn(name="project_id")
   @NotNull
   private Project project;

   public Date getStartTime() {
      return startTime;
   }

   public void setStartTime(final Date startTime) {
      this.startTime = startTime;
   }

   public Date getEndTime() {
      return endTime;
   }

   public void setEndTime(final Date endTime) {
      this.endTime = endTime;
   }

   public String getComment() {
      return comment;
   }

   public void setComment(final String comment) {
      this.comment = comment;
   }

   public Person getPerson() {
      return person;
   }

   public void setPerson(final Person person) {
      this.person = person;
   }

   public Project getProject() {
      return project;
   }

   public void setProject(final Project project) {
      this.project = project;
   }

}
