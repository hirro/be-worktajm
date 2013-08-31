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
 * @author jiar
 */
@Entity
@SuppressWarnings("PMD")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeEntry extends AbstractPersistable<Long> {
   @NotNull
   @Temporal(TemporalType.TIMESTAMP)
   @DateTimeFormat(style = "M-")
   private Date  startTime;
   @Temporal(TemporalType.TIMESTAMP)
   @DateTimeFormat(style = "M-")
   private Date endTime;
   private String comment;
   @ManyToOne
   private Person person;
   @ManyToOne
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
