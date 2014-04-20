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

import com.arnellconsulting.tps.common.InvoicePeriod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "tps_customer",
       uniqueConstraints = {
         @UniqueConstraint(columnNames = {"name", "person_id"}, name="idx_customer_person")
       })
@JsonSerialize(using=Customer.CustomerSerializer.class)
public class Customer extends AbstractTimestampedObject<Long> {

   private static final long serialVersionUID = -390230121353660214L;

   /**
    * Customer name, mandatory.
    */
   @NotNull
   private String name;
   
   /**
    * Invoice period.
    */
   @Enumerated(EnumType.STRING)
   private InvoicePeriod invoicePeriod = InvoicePeriod.NONE;

   /**
    * Billings address, mandatory.
    */
   private Address billingAddress;

   /**
    * Reference person, optional.
    */
   private String referencePerson;

   /**
    * The projects owned by the project.
    */
   @OneToMany
   @JoinColumn(name="customerId")
   @JsonIgnore
   private Collection<Project> projects;

   /**
    * The owner of the entry
    *
    */
   @ManyToOne
   @JoinColumn(name="person_id")
   @JsonIgnore
   private Person person;

   public String getName() {
      return name;
   }

   public void setName(final String name) {
      this.name = name;
   }

   public Address getBillingAddress() {
      return billingAddress;
   }

   public void setBillingAddress(final Address billingAddress) {
      this.billingAddress = billingAddress;
   }

   public String getReferencePerson() {
      return referencePerson;
   }

   public void setReferencePerson(final String referencePerson) {
      this.referencePerson = referencePerson;
   }

   public InvoicePeriod getInvoicePeriod() {
      return invoicePeriod;
   }

   public void setInvoicePeriod(InvoicePeriod invoicePeriod) {
      this.invoicePeriod = invoicePeriod;
   }   
   
   public Collection<Project> getProjects() {
      return projects;
   }

   public void setProjects(final Collection<Project> projects) {
      this.projects = projects;
   }

   public Person getPerson() {
      return person;
   }

   public void setPerson(final Person person) {
      this.person = person;
   }   

   static class CustomerSerializer extends JsonSerializer<Customer> {

      @Override
      public void serialize(
              Customer customer,
              JsonGenerator jsonGenerator,
              SerializerProvider sp)
              throws IOException, JsonProcessingException {
         jsonGenerator.writeStartObject();
         jsonGenerator.writeNumberField("id", customer.getId());
         jsonGenerator.writeStringField("name", customer.getName());
         jsonGenerator.writeStringField("referencePerson", customer.getReferencePerson());
         if (customer.getLastModified() != null) {
            jsonGenerator.writeStringField("modified", customer.getLastModified().toString());
         }
         jsonGenerator.writeObjectField("billingAddress", customer.getBillingAddress());
         jsonGenerator.writeEndObject();
      }      
   }
}
