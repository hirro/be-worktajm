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
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Project POJO
 * 
 * @author hirro
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends AbstractPersistable<Long> {
   private static final long serialVersionUID = -3902305943341540214L;

   /**
    * Project name.
    */
   @NotNull
   @Getter @Setter private String name;
   
   /**
    * Project description.
    */
   @Getter @Setter private String description;

   /**
    * Charging for this project.
    */
   @Getter @Setter private BigDecimal rate;

   /**
    * Time entries associated to this project.
    */
   @OneToMany
   @JsonBackReference(value = "timeentry->project")
   @Getter @Setter private Collection<TimeEntry> timeEntries;

   /**
    * The person who owns this project.
    */
   @ManyToOne
   @NotNull
   @JsonIgnore
   @JsonBackReference("person->project")   
   @Getter @Setter private Person person;
   
}
