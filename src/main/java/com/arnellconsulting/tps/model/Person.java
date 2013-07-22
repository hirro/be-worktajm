package com.arnellconsulting.tps.model;

import com.arnellconsulting.tps.common.PersonStatus;
import com.arnellconsulting.tps.common.RegistrationStatus;

import lombok.Data;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

/**
 * TBD
 * @author jiar
 */
@Entity
@SuppressWarnings("PMD")
@Data
public class Person extends AbstractPersistable<Long> implements UserDetails {
   private static final long serialVersionUID = -3902301243341660214L;

// @ManyToMany(cascade = CascadeType.ALL, mappedBy = "contracters")
// private Set<Contract> contracts = new HashSet<Contract>();
// @OneToMany(cascade = CascadeType.ALL, mappedBy = "Person")
// private Set<TimeEntry> contract = new HashSet<TimeEntry>();
   private String firstName;
   private String lastName;
   private String password;
   @NotNull
   @Column(unique = true)
   private String email;
   private String authority;
   @Enumerated
   private PersonStatus status;
   private Boolean enabled;
   @Enumerated
   private RegistrationStatus registrationStatus;

// @ManyToOne
// private Corporate employer;

   @Transient
   private transient Boolean credentialsNonExpired;
   @Transient
   private transient Boolean credentialsNonLocked;
   @Transient
   private transient boolean accountNonLocked;
   @Transient
   private transient boolean accountNonExpired;

   public Person() {
      this.status = PersonStatus.NORMAL;
      this.registrationStatus = RegistrationStatus.PENDING;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      ArrayList<GrantedAuthority> temp = new ArrayList<GrantedAuthority>();

      temp.add(new SimpleGrantedAuthority("ROLE_USER"));

      return temp;
   }

   @Override
   public String getUsername() {
      return email;
   }

   @Override
   public boolean isAccountNonExpired() {
      return accountNonExpired;
   }

   @Override
   public boolean isAccountNonLocked() {
      return accountNonLocked;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return credentialsNonExpired;
   }

   @Override
   public boolean isEnabled() {
      return enabled;
   }
}
