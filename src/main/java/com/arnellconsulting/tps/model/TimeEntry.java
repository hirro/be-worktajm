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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Time entries.
 *
 * @author hirro
 */
@Entity
@Table(name = "tps_time_entry")
@SuppressWarnings("PMD")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(using = TimeEntry.TimeEntrySerializer.class)
public class TimeEntry extends AbstractTimestampedObject<Long> {

   /**
    * Start date and time.
    */
   @NotNull
   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
   @Column(name="start_time")   
   private LocalDateTime startTime;

   /**
    * End date and time
    */
   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
   @Column(name="end_time")   
   private LocalDateTime endTime;

   /**
    * Optional comment for the time entry.
    */
   private String comment;

   /**
    * The person who owns this time entry.
    */
   @ManyToOne
   @JoinColumn(name = "person_id")
   @NotNull
   @JsonIgnore
   @JsonBackReference("activeTimeEntry")
   private Person person;

   /**
    * The project that is associated with the time entry.
    */
   @ManyToOne
   @JoinColumn(name = "project_id")
   @NotNull
   private Project project;

   public LocalDateTime getStartTime() {
      return startTime;
   }

   public void setStartTime(final LocalDateTime startTime) {
      this.startTime = startTime;
   }

   public LocalDateTime getEndTime() {
      return endTime;
   }

   public void setEndTime(final LocalDateTime endTime) {
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

   protected static class TimeEntrySerializer extends JsonSerializer<TimeEntry> {

      @Override
      public void serialize(
              TimeEntry timeEntry,
              JsonGenerator jsonGenerator,
              SerializerProvider sp)
              throws IOException, JsonProcessingException {
         jsonGenerator.writeStartObject();
         if (timeEntry.getId() != null) {
            jsonGenerator.writeNumberField("id", timeEntry.getId());
         }
         if (timeEntry.getProject() != null && timeEntry.getProject().getId() != null) {
            jsonGenerator.writeNumberField("projectId", timeEntry.getProject().getId());
         }
          DateTimeFormatter fmt = ISODateTimeFormat.dateTimeNoMillis();
         if (timeEntry.getStartTime() != null) {
            jsonGenerator.writeStringField("startTime", fmt.print(timeEntry.getStartTime()));
         }
         if (timeEntry.getEndTime() != null) {
            jsonGenerator.writeStringField("endTime", fmt.print(timeEntry.getEndTime()));
         }
         jsonGenerator.writeEndObject();
      }
   }
}
