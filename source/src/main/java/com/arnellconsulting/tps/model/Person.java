package com.arnellconsulting.tps.model;

import com.arnellconsulting.tps.common.PersonStatus;
import com.arnellconsulting.tps.common.RegistrationStatus;
import java.io.Serializable;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

/**
 * TBD
 * @author jiar
 */
@Entity
@Data
@SuppressWarnings("PMD")
public class Person implements Serializable {

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
   private final PersonStatus status;

   private Boolean enabled;

   @Enumerated
   private final RegistrationStatus registrationStatus;

   @ManyToOne
   private Corporate employer;

   @ManyToMany(
      cascade = CascadeType.ALL,
      mappedBy = "contracters"
   )
   private final Set<Contract> contracts = new HashSet<Contract>();

   @OneToMany(
      cascade = CascadeType.ALL,
      mappedBy = "Person"
   )
   private final Set<TimeEntry> contract = new HashSet<TimeEntry>();

   public Person() {
      this.status = PersonStatus.NORMAL;
      this.registrationStatus = RegistrationStatus.PENDING;
   }
}
