///*
// * Copyright 2013 Jim Arnell
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//
//
//
//package com.arnellconsulting.tps.model;
//
//import org.codehaus.jackson.annotate.JsonIgnore;
//import org.codehaus.jackson.annotate.JsonIgnoreProperties;
//
//import javax.persistence.*;
//
//import javax.validation.constraints.NotNull;
//
///**
// *
// */
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Entity
//@Table(
//   name = "tps_company",
//   uniqueConstraints = { @UniqueConstraint(
//      columnNames = { "name", "person_id" },
//      name = "idx_customer_person"
//   ) }
//)
//public class Company extends AbstractTimestampedObject<Long> {
//   private static final long serialVersionUID = -390230121353660214L;
//
//   /**
//    * Bank number.
//    */
//   private String bankNumber;
//
//   /**
//    * Customer name, mandatory.
//    */
//   @NotNull
//   private String name;
//
//   /**
//    * Postal address, optional.
//    */
//   private Address address;
//
//   /**
//    * Reference person, optional.
//    */
//   private String referencePerson;
//
//   /**
//    * Organizational number.
//    */
//   private String organizationNumber;
//
//   /**
//    * VAT
//    */
//   private String vatRegistrationNumber;
//
//   /**
//    * The administrator of the company
//    *
//    */
//   @ManyToOne
//   @JoinColumn(name = "person_id")
//   @JsonIgnore
//   private Person person;
//
//   public Address getAddress() {
//      return address;
//   }
//
//   public String getName() {
//      return name;
//   }
//
//   public String getOrganizationNumber() {
//      return organizationNumber;
//   }
//
//   public Person getPerson() {
//      return person;
//   }
//
//   public String getReferencePerson() {
//      return referencePerson;
//   }
//
//   public String getVatRegistrationNumber() {
//      return vatRegistrationNumber;
//   }
//
//   public void setAddress(final Address address) {
//      this.address = address;
//   }
//
//   public void setName(final String name) {
//      this.name = name;
//   }
//
//   public void setOrganizationNumber(String organizationNumber) {
//      this.organizationNumber = organizationNumber;
//   }
//
//   public void setPerson(final Person person) {
//      this.person = person;
//   }
//
//   public void setReferencePerson(final String referencePerson) {
//      this.referencePerson = referencePerson;
//   }
//
//   public void setVatRegistrationNumber(String vatRegistrationNumber) {
//      this.vatRegistrationNumber = vatRegistrationNumber;
//   }
//}
