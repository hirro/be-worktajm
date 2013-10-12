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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@SuppressWarnings("PMD")
public class Corporate  extends AbstractPersistable<Long> {
   
    @NotNull
    private String name;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
//    private final Set<Customer> customers = new HashSet<Customer>();
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employer")
//    private final Set<Person> Persons = new HashSet<Person>();
}
