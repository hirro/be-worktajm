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

import javax.persistence.Embeddable;

/**
 * Created by jiar on 2013-12-22.
 */
@Embeddable
public class Address {
    @Getter @Setter
    private String line1;

    @Getter @Setter
    private String line2;

    @Getter @Setter
    private String city;

    @Getter @Setter
    private String state;

    @Getter @Setter
    private String country;

    @Getter @Setter
    private String zip;
}
