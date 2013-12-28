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



package com.arnellconsulting.tps.service;

import com.arnellconsulting.tps.model.*;
import com.arnellconsulting.tps.repository.CompanyRepository;
import com.arnellconsulting.tps.repository.PersonRepository;
import com.arnellconsulting.tps.repository.ProjectRepository;
import com.arnellconsulting.tps.repository.TimeEntryRepository;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

import static org.junit.Assert.assertThat;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hirro
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class TpsServiceImplCustomerTest {
   private static final String EMAIL = "a@ab.com";
   private transient TpsServiceImpl service;
    private transient CompanyRepository companyRepository;
   private transient List<Customer> testCompanies;
    private transient Customer customerA;

   @Before
   public void setUp() {
       PersonRepository personRepository = mock(PersonRepository.class);
       ProjectRepository projectRepository = mock(ProjectRepository.class);
       TimeEntryRepository timeEntryRepository = mock(TimeEntryRepository.class);
      companyRepository = mock(CompanyRepository.class);
      service = new TpsServiceImpl(personRepository, projectRepository, timeEntryRepository, companyRepository);
      customerA = TestConstants.createCustomerA();
      testCompanies = new ArrayList<Customer>();
      testCompanies.add(customerA);
       Person personA = TestConstants.createPersonA();
   }

   /**
    * Test of getCompaniesForPerson method, of class TpsServiceImpl.
    */
   @Test
   public void testGetCompanyForPerson() {
      when(companyRepository.findByPersonId(1)).thenReturn(testCompanies);

      final List<Customer> companies = service.getCustomersForPerson(1);

      assertThat(customerA.getCompanyName(), is(companies.get(0).getCompanyName()));
      verify(companyRepository, times(1)).findByPersonId(1);
      verifyNoMoreInteractions(companyRepository);
   }

   /**
    * Test of getCompany method, of class TpsServiceImpl.
    */
   @Test
   public void testGetCompany() {
      when(companyRepository.findOne(1L)).thenReturn(customerA);

      final Customer customer = service.getCustomer(1L);
      assertThat(customerA.getCompanyName(), is(customer.getCompanyName()));

      verify(companyRepository, times(1)).findOne(1L);
      verifyNoMoreInteractions(companyRepository);
   }

   /**
    * Test of deleteCompany method, of class TpsServiceImpl.
    */
   @Test
   public void testDeleteProject() {
      service.deleteCustomer(1L);
      verify(companyRepository, times(1)).delete(1L);
      verifyNoMoreInteractions(companyRepository);
   }

   /**
    * Test of saveCompany method, of class TpsServiceImpl.
    */
   @Test
   public void testSaveProject() {
      service.saveCustomer(customerA);
      verify(companyRepository, times(1)).save(customerA);
      verifyNoMoreInteractions(companyRepository);
   }

}
