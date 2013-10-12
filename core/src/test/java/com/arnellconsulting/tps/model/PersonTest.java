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

   @Before
   public void setUp() {
      person = TestConstants.createPersonA();
   }

   @Test
   public void testGetters() {
     assertEquals(TestConstants.PERSON_A_FIRST_NAME, person.getFirstName());
      assertEquals(TestConstants.PERSON_A_LAST_NAME, person.getLastName());
     assertEquals(TestConstants.PERSON_A_EMAIL_VERIFIED, person.getEmailVerified());
  }
}
