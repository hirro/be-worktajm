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

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@SuppressWarnings("PMD")
public class Contract  extends AbstractPersistable<Long> {

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MS")
    private Date validFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MS")
    private Date validTo;

    @NotNull
    private BigDecimal rate;

//    @ManyToMany(cascade = CascadeType.ALL)
//    private final Set<Person> contracters = new HashSet<Person>();
//
//    @ManyToOne
//    private Customer customer;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contract")
//    private final Set<Project> projects = new HashSet<Project>();
}
