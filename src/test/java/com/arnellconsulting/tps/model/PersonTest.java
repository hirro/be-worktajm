
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.arnellconsulting.tps.model;

import com.arnellconsulting.tps.common.PersonStatus;
import com.arnellconsulting.tps.common.RegistrationStatus;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Collection;

/**
 *
 * @author jiar
 */
public class PersonTest {
   private transient Person person;
   private static final Boolean ACCOUNT_NON_EXPIRED = Boolean.TRUE;
   private static final Boolean ACCOUNT_NON_LOCKED = Boolean.TRUE;
   private static final Boolean CREDENTIALS_NON_EXPIRED = Boolean.TRUE;
   private static final Boolean CREDENTIALS_NON_LOCKED = Boolean.TRUE;
   private static final Boolean ENABLED = Boolean.TRUE;
   private static final PersonStatus PERSON_STATUS = PersonStatus.NORMAL;
   private static final RegistrationStatus REGISTRATION_STATUS = RegistrationStatus.PENDING;
   private static final String EMAIL = "a@sdf.com";
   private static final String FIRST_NAME = "Firstname";
   private static final String LAST_NAME = "Lastname";
   private static final String PASSWORD = "Lastname";

   @Before
   public void setUp() {
      person = new Person();
      person.setAccountNonLocked(ACCOUNT_NON_LOCKED);
      person.setAccountNonExpired(ACCOUNT_NON_EXPIRED);
      person.setCredentialsNonExpired(CREDENTIALS_NON_EXPIRED);
      person.setCredentialsNonLocked(CREDENTIALS_NON_LOCKED);
      person.setEmail(EMAIL);
      person.setEnabled(ENABLED);
      person.setFirstName(FIRST_NAME);
      person.setLastName(LAST_NAME);
      person.setPassword(PASSWORD);
      person.setRegistrationStatus(REGISTRATION_STATUS);
   }

   @Test
   public void testGetters() {
      assertEquals(ACCOUNT_NON_EXPIRED, person.isAccountNonExpired());
      assertEquals(ACCOUNT_NON_LOCKED, person.isAccountNonLocked());
      assertEquals(CREDENTIALS_NON_EXPIRED, person.isCredentialsNonExpired());
      assertEquals(PASSWORD, person.getPassword());
      assertEquals(EMAIL, person.getUsername());
      assertEquals(ENABLED, person.getEnabled());
      assertEquals(ENABLED, person.isEnabled());
      assertEquals(FIRST_NAME, person.getFirstName());
      assertEquals(LAST_NAME, person.getLastName());
      assertEquals(PASSWORD, person.getPassword());
      assertEquals(PERSON_STATUS, person.getStatus());
      assertEquals(REGISTRATION_STATUS, person.getRegistrationStatus());
      assertEquals(null, person.getAuthority());
   }
}
