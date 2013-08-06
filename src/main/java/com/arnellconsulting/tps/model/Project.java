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

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.springframework.data.jpa.domain.AbstractPersistable;

import java.math.BigDecimal;

import java.util.Collection;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

/**
 * TBD
 * @author jiar
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends AbstractPersistable<Long> {
   private static final long serialVersionUID = -3902305943341540214L;
   private String name;
   private String description;
   private BigDecimal rate;
   @OneToMany
   @JsonBackReference(value = "timeentry->project")
   private Collection<TimeEntry> timeEntries;

   public Collection<TimeEntry> getTimeEntries() {
      return timeEntries;
   }

   public String getDescription() {
      return description;
   }

   @NotNull
   public String getName() {
      return name;
   }

   public BigDecimal getRate() {
      return rate;
   }

   public void setDescription(final String description) {
      this.description = description;
   }

   public void setName(final String name) {
      this.name = name;
   }

   public void setRate(final BigDecimal rate) {
      this.rate = rate;
   }

   public void setTimeEntries(final Collection<TimeEntry> timeEntries) {
      this.timeEntries = timeEntries;
   }
}
