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
import lombok.Getter;
import lombok.Setter;

/**
 * Time entries.
 * @author hirro
 */
@Entity
@SuppressWarnings("PMD")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeEntry extends AbstractPersistable<Long> {
   
   /**
    * Start date and time.
    */
   @NotNull
   @Temporal(TemporalType.TIMESTAMP)
   @DateTimeFormat(style = "M-")
   @Getter @Setter private Date  startTime;

   /**
    * End date and time
    */
   @Temporal(TemporalType.TIMESTAMP)
   @DateTimeFormat(style = "M-")
   @Getter @Setter private Date endTime;
   
   /**
    * Optional comment for the time entry.
    */
   @Getter @Setter private String comment;

   /**
    * The person who owns this time entry.
    */
   @ManyToOne
   @NotNull
   @JsonIgnore
   @JsonBackReference("activeTimeEntry")   
   @Getter @Setter private Person person;

   /**
    * The project that is associated with the time entry.
    */
   @ManyToOne
   @NotNull
   @Getter @Setter private Project project;
   
}
