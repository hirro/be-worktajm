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

import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * TBD
 * @author jiar
 */
@Entity
@SuppressWarnings("PMD")
public class TimeEntry  extends AbstractPersistable<Long> {

   public TimeEntry() {
   }
   public Date getStartTime() {
      return startTime;
   }

   public void setStartTime(Date startTime) {
      this.startTime = startTime;
   }

   public Date getEndTime() {
      return endTime;
   }

   public void setEndTime(Date endTime) {
      this.endTime = endTime;
   }

   public String getComment() {
      return comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public Person getPerson() {
      return person;
   }

   public void setPerson(Person person) {
      this.person = person;
   }

   public Project getProject() {
      return project;
   }

   public void setProject(Project project) {
      this.project = project;
   }

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date endTime;

    private String comment;

    @ManyToOne
    @JsonBackReference private Person person;

    @ManyToOne
    @JsonManagedReference
    private Project project;
}
