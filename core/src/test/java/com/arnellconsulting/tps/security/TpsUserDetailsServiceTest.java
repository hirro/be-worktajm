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
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isNull;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 *
 * @author hirro
 */
public class TpsUserDetailsServiceTest {
   private static final String EMAIL = "a@example.com";
   private static final String UNKNOWN_EMAIL = "b@example.com";

   private final transient PersonRepository repositoryMock = Mockito.mock(PersonRepository.class);
   private transient TpsUserDetailsService tps;
   public Person person;

   @Before
   public void setUp() {
      tps = new TpsUserDetailsService(repositoryMock);
      person = TestConstants.createPersonA();
   }

   @Test(expected = UsernameNotFoundException.class)
   public void testLoadUserByUsernameButNotFound() {
      when(repositoryMock.findByEmail(UNKNOWN_EMAIL)).thenReturn(null);
      tps.loadUserByUsername(EMAIL);
      verify(repositoryMock, times(1)).findByEmail(UNKNOWN_EMAIL);
      verifyNoMoreInteractions(repositoryMock);
   }
   
   @Test
   public void testLoadUserByUsername() {
      when(repositoryMock.findByEmail(EMAIL)).thenReturn(person);

      final PersonUserDetails loadedPerson = (PersonUserDetails) tps.loadUserByUsername(EMAIL);

      assertThat(loadedPerson, notNullValue());
      assertThat(loadedPerson.getPassword(), is(TestConstants.PERSON_A_PASSWORD));
      assertThat(loadedPerson.getUsername(), is(TestConstants.PERSON_A_EMAIL));
      assertThat(loadedPerson.isAccountNonExpired(), is(true));
      assertThat(loadedPerson.isAccountNonLocked(), is(true));
      assertThat(loadedPerson.isCredentialsNonExpired(), is(true));
      assertThat(loadedPerson.isEnabled(), is(true));
      assertThat(loadedPerson.getAuthorities(), notNullValue());
      assertThat(loadedPerson.getPerson(), is(person));
      verify(repositoryMock, times(1)).findByEmail(EMAIL);
      verifyNoMoreInteractions(repositoryMock);
   }
   
}
