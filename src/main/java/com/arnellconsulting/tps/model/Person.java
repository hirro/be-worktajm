package com.arnellconsulting.tps.model;

import com.arnellconsulting.tps.common.PersonStatus;
import com.arnellconsulting.tps.common.RegistrationStatus;

import lombok.Data;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

/**
 * TBD
 * @author jiar
 */
@Entity
@SuppressWarnings("PMD")
public class Person implements Serializable {
   @ManyToMany(cascade = CascadeType.ALL, mappedBy = "contracters")
   private Set<Contract> contracts = new HashSet<Contract>();
   @OneToMany(cascade = CascadeType.ALL, mappedBy = "Person")
   private Set<TimeEntry> contract = new HashSet<TimeEntry>();
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private String id;
   private String firstName;
   private String lastName;
   private String password;
   @NotNull
   private String username;
   private String authority;
   @Enumerated
   private PersonStatus status;
   private Boolean enabled;
   @Enumerated
   private RegistrationStatus registrationStatus;
   @ManyToOne
   private Corporate employer;

   public Person() {
      this.status = PersonStatus.NORMAL;
      this.registrationStatus = RegistrationStatus.PENDING;
   }

   //~--- get methods ---------------------------------------------------------

   public String getAuthority() {
      return authority;
   }

   public Set<TimeEntry> getContract() {
      return contract;
   }

   public Set<Contract> getContracts() {
      return contracts;
   }

   public Corporate getEmployer() {
      return employer;
   }

   public Boolean getEnabled() {
      return enabled;
   }

   public String getFirstName() {
      return firstName;
   }

   public String getId() {
      return id;
   }

   public String getLastName() {
      return lastName;
   }

   public String getPassword() {
      return password;
   }

   public RegistrationStatus getRegistrationStatus() {
      return registrationStatus;
   }

   public PersonStatus getStatus() {
      return status;
   }

   public String getUsername() {
      return username;
   }

   //~--- set methods ---------------------------------------------------------

   public void setAuthority(String authority) {
      this.authority = authority;
   }

   public void setContract(Set<TimeEntry> contract) {
      this.contract = contract;
   }

   public void setContracts(Set<Contract> contracts) {
      this.contracts = contracts;
   }

   public void setEmployer(Corporate employer) {
      this.employer = employer;
   }

   public void setEnabled(Boolean enabled) {
      this.enabled = enabled;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public void setId(String id) {
      this.id = id;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setRegistrationStatus(RegistrationStatus registrationStatus) {
      this.registrationStatus = registrationStatus;
   }

   public void setStatus(PersonStatus status) {
      this.status = status;
   }

   public void setUsername(String username) {
      this.username = username;
   }
}
