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

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Created by jiar on 2013-12-22.
 */
@Embeddable
public class Address implements Serializable {

   private String line1;
   private String line2;
   private String city;
   private String state;
   private String country;
   private String zip;

   public String getState() {
      return state;
   }

   public void setState(final String state) {
      this.state = state;
   }

   public String getLine1() {
      return line1;
   }

   public void setLine1(final String line1) {
      this.line1 = line1;
   }

   public String getLine2() {
      return line2;
   }

   public void setLine2(final String line2) {
      this.line2 = line2;
   }

   public String getCity() {
      return city;
   }

   public void setCity(final String city) {
      this.city = city;
   }

   public String getCountry() {
      return country;
   }

   public void setCountry(final String country) {
      this.country = country;
   }

   public String getZip() {
      return zip;
   }

   public void setZip(final String zip) {
      this.zip = zip;
   }

}
