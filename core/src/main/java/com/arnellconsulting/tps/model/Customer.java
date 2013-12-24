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

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 *
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer extends AbstractPersistable<Long>
{
    private static final long serialVersionUID = -390230121353660214L;

    /**
     * Customer name, mandatory.
     */
    @NotNull
    @Getter @Setter private String companyName;

    /**
     * Billings address, mandatory.
     */
    @NotNull
    @Getter @Setter private Address billingAddress;

    /**
     * Reference person, optional.
     */
    @Getter @Setter private String referencePerson;

    /**
     * The projects owned by the project.
     */
    @OneToMany
    @JsonIgnore
    @Getter @Setter private Collection<Project> projects;


   /**
    * The owner of the entry
    *
    */
   @ManyToOne
   @JsonIgnore
   @Getter @Setter private Person person;
}
