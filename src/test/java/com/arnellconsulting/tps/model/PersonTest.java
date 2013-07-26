/*
 * Copyright 2013 Arnell Consulting AB.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package com.arnellconsulting.tps.model;

import com.arnellconsulting.tps.common.PersonStatus;

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
   private static final Boolean ENABLED = Boolean.TRUE;
   private static final PersonStatus PERSON_STATUS = PersonStatus.NORMAL;
   private static final String EMAIL = "a@sdf.com";
   private static final String FIRST_NAME = "Firstname";
   private static final String LAST_NAME = "Lastname";
   private static final String PASSWORD = "Lastname";
   private static final boolean EMAIL_VERIFIED = true;

   @Before
   public void setUp() {
      person = new Person();
      person.setEmail(EMAIL);
      person.setFirstName(FIRST_NAME);
      person.setLastName(LAST_NAME);
      person.setPassword(PASSWORD);
      person.setEmailVerified(EMAIL_VERIFIED);
   }

   @Test
   public void testGetters() {
      assertEquals(ACCOUNT_NON_EXPIRED, person.isAccountNonExpired());
      assertEquals(ACCOUNT_NON_LOCKED, person.isAccountNonLocked());
      assertEquals(CREDENTIALS_NON_EXPIRED, person.isCredentialsNonExpired());
      assertEquals(PASSWORD, person.getPassword());
      assertEquals(EMAIL, person.getUsername());
      assertEquals(ENABLED, person.isEnabled());
      assertEquals(FIRST_NAME, person.getFirstName());
      assertEquals(LAST_NAME, person.getLastName());
      assertEquals(PASSWORD, person.getPassword());
      assertEquals(EMAIL_VERIFIED, person.isEmailVerified());
      assertEquals(null, person.getAuthority());
   }
}
