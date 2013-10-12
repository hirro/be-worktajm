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


package com.arnellconsulting.tps.security;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.TestConstants;
import com.arnellconsulting.tps.repository.PersonRepository;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import static org.junit.Assert.assertThat;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 *
 * @author jiar
 */
public class TpsUserDetailsServiceTest {
   private static final String EMAIL = "a@example.com";
   private static final String UNKNOWN_EMAIL = "b@example.com";

   private transient PersonRepository repositoryMock = Mockito.mock(PersonRepository.class);
   private transient TpsUserDetailsService tps;
   private transient PersonUserDetails userDetails;
   public Person person;

   //~--- methods -------------------------------------------------------------

   @Test(expected = UsernameNotFoundException.class)
   public void testLoadUserByUsernameButNotFound() {
      when(repositoryMock.findByEmail(UNKNOWN_EMAIL)).thenReturn(null);
      tps.loadUserByUsername(EMAIL);
      verify(repositoryMock, times(1)).findByEmail(UNKNOWN_EMAIL);
      verifyNoMoreInteractions(repositoryMock);
   }

   //~--- set methods ---------------------------------------------------------

   @Before
   public void setUp() {
      tps = new TpsUserDetailsService(repositoryMock);
      person = TestConstants.createPersonA();
      userDetails = new PersonUserDetails();
      userDetails.setPerson(person);
      userDetails.setAuthority(TestConstants.PERSON_A_AUTHORITY);
      userDetails.setPassword(TestConstants.PERSON_A_PASSWORD);
   }

   //~--- methods -------------------------------------------------------------

   public void testLoadUserByUsername() {
      when(repositoryMock.findByEmail(EMAIL)).thenReturn(person);

      final PersonUserDetails person = (PersonUserDetails) tps.loadUserByUsername(EMAIL);

      assertThat(person, notNullValue());
      assertThat(person.getAuthority(), is(TestConstants.PERSON_A_AUTHORITY));
      assertThat(person.getPassword(), is(TestConstants.PERSON_A_PASSWORD));
      assertThat(person.getUsername(), is(TestConstants.PERSON_A_EMAIL));
      assertThat(person.isAccountNonExpired(), is(true));
      assertThat(person.isAccountNonLocked(), is(true));
      assertThat(person.isCredentialsNonExpired(), is(true));
      assertThat(person.isEnabled(), is(true));
      assertThat(person.getAuthorities(), notNullValue());
      verify(repositoryMock, times(1)).findByEmail(EMAIL);
      verifyNoMoreInteractions(repositoryMock);
   }
}
