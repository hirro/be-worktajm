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
package com.arnellconsulting.tps.service;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.model.TimeEntry;
import com.arnellconsulting.tps.repository.PersonRepository;
import com.arnellconsulting.tps.repository.ProjectRepository;
import com.arnellconsulting.tps.repository.TimeEntryRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 *
 * @author jiar
 */
public class TpsServiceImplTest {
   
   private TpsServiceImpl service;
   private PersonRepository personRepository;
   private ProjectRepository projectRepository;
   private TimeEntryRepository timeEntryRepository;
   private Project projectA;
   private ArrayList<Project> projects;
   private Project projectB;
   private Person personA;
   private Person personB;
   private ArrayList<Person> persons;
   private TimeEntry timeEntryA;
   private Date dateA;
   
   public TpsServiceImplTest() {
   }
   
   @BeforeClass
   public static void setUpClass() {
   }
   
   @AfterClass
   public static void tearDownClass() {
   }
   
   @Before
   public void setUp() {
      personRepository = mock(PersonRepository.class);
      projectRepository = mock(ProjectRepository.class);
      timeEntryRepository = mock(TimeEntryRepository.class);      
      service = new TpsServiceImpl(personRepository, projectRepository, timeEntryRepository);
      
      projectA = new Project();
      projectA.setName("Project A");
      projectB = new Project();
      projectB.setName("Project B");
      projects = new ArrayList<Project>();
      projects.add(projectA);
      projects.add(projectB);
      
      personA = new Person();
      personA.setEmail("a@test.com");

      personB = new Person();
      personB.setEmail("b@test.com");
      
      persons = new ArrayList<Person>();
      persons.add(personA);
      persons.add(personB);
      
      timeEntryA = new TimeEntry();
      timeEntryA.setPerson(personA);
      dateA = new Date(0);
      timeEntryA.setEndTime(dateA);      
}
   
   @After
   public void tearDown() {
   }

   /**
    * Test of getProjets method, of class TpsServiceImpl.
    */
   @Test
   public void testGetProjets() {
      when(projectRepository.findAll()).thenReturn(this.projects);
      List<Project> projects = service.getProjets();
      assertThat(projectA.getName(), is(projects.get(0).getName()));
      assertThat(projectB.getName(), is(projects.get(1).getName()));
      verify(projectRepository, times(1)).findAll();
      verifyNoMoreInteractions(projectRepository);
   }

   /**
    * Test of getProject method, of class TpsServiceImpl.
    */
   @Test
   public void testGetProject() {
      when(projectRepository.findOne(1L)).thenReturn(projectA);
      Project project = service.getProject(1L);
      verify(projectRepository, times(1)).findOne(1L);
      verifyNoMoreInteractions(projectRepository);
   }

   /**
    * Test of deleteProject method, of class TpsServiceImpl.
    */
   @Test
   public void testDeleteProject() {
      service.deleteProject(1L);
      verify(projectRepository, times(1)).delete(1L);
      verifyNoMoreInteractions(projectRepository);
   }

   /**
    * Test of saveProject method, of class TpsServiceImpl.
    */
   @Test
   public void testSaveProject() {
      service.saveProject(projectA);
      verify(projectRepository, times(1)).save(projectA);
      verifyNoMoreInteractions(projectRepository);
   }

   /**
    * Test of getPersons method, of class TpsServiceImpl.
    */
   @Test
   public void testGetPersons() {
      when(personRepository.findAll()).thenReturn(this.persons);
      final List<Person> ps = service.getPersons();
      assertThat(personA.getEmail(), is(ps.get(0).getEmail()));
      assertThat(personB.getEmail(), is(ps.get(1).getEmail()));
      verify(personRepository, times(1)).findAll();
      verifyNoMoreInteractions(personRepository);
   }

   /**
    * Test of getPerson method, of class TpsServiceImpl.
    */
   @Test
   public void testGetPerson() {
      when(personRepository.findOne(1L)).thenReturn(personA);
      Person person = service.getPerson(1L);
      verify(personRepository, times(1)).findOne(1L);
      verifyNoMoreInteractions(personRepository);
   }

   /**
    * Test of deletePerson method, of class TpsServiceImpl.
    */
   @Test
   public void testDeletePerson() {
      service.deletePerson(1L);
      verify(personRepository, times(1)).delete(1L);
      verifyNoMoreInteractions(personRepository);
   }

   /**
    * Test of savePerson method, of class TpsServiceImpl.
    */
   @Test
   public void testSavePerson() {
      service.savePerson(personA);
      verify(personRepository, times(1)).save(personA);
      verifyNoMoreInteractions(personRepository);
   }

   /**
    * Test of findPersonByEmail method, of class TpsServiceImpl.
    */
   @Test
   public void testFindPersonByEmail() {
   }

   /**
    * Test of getTimeEntries method, of class TpsServiceImpl.
    */
   @Test
   public void testGetTimeEntries() {
   }

   /**
    * Test of getTimeEntry method, of class TpsServiceImpl.
    */
   @Test
   public void testGetTimeEntry() {
      when(timeEntryRepository.findOne(1L)).thenReturn(timeEntryA);
      TimeEntry t = service.getTimeEntry(1L);
      verify(timeEntryRepository, times(1)).findOne(1L);
      verifyNoMoreInteractions(timeEntryRepository);
   }

   /**
    * Test of deleteTimeEntry method, of class TpsServiceImpl.
    */
   @Test
   public void testDeleteTimeEntry() {
      service.deleteTimeEntry(1L);
      verify(timeEntryRepository, times(1)).delete(1L);
      verifyNoMoreInteractions(timeEntryRepository);
   }

   /**
    * Test of saveTimeEntry method, of class TpsServiceImpl.
    */
   @Test
   public void testSaveTimeEntry() {
      service.saveTimeEntry(timeEntryA);
      verify(timeEntryRepository, times(1)).save(timeEntryA);
      verifyNoMoreInteractions(timeEntryRepository);
      
   }
}