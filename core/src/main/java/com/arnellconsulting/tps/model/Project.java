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
