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
package com.arnellconsulting.worktajm.service;

import com.arnellconsulting.worktajm.service.TpsServiceImpl;
import com.arnellconsulting.worktajm.domain.Customer;
import com.arnellconsulting.worktajm.domain.Person;
import com.arnellconsulting.worktajm.domain.Project;
import com.arnellconsulting.worktajm.domain.TimeEntry;
import com.arnellconsulting.worktajm.repository.CompanyRepository;
import com.arnellconsulting.worktajm.repository.PersonRepository;
import com.arnellconsulting.worktajm.repository.ProjectRepository;
import com.arnellconsulting.worktajm.repository.TimeEntryRepository;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.is;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author hirro
 */
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class TpsServiceImplTest {

    private static final String EMAIL = "a@ab.com";
    private transient TpsServiceImpl service;
    private transient PersonRepository personRepository;
    private transient ProjectRepository projectRepository;
    private transient TimeEntryRepository timeEntryRepository;
    private transient Project projectA;
    private transient Project projectB;
    private transient List<Project> projects;
    private transient Person personA;
    private transient Person personB;
    private transient List<Person> persons;
    private transient TimeEntry timeEntryA;
    private transient List<TimeEntry> timeEntriesA;
    private transient Customer customerA;

    @Before
    public void setUp() {
        personRepository = mock(PersonRepository.class);
        projectRepository = mock(ProjectRepository.class);
        timeEntryRepository = mock(TimeEntryRepository.class);
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        service = new TpsServiceImpl(personRepository, projectRepository, timeEntryRepository, companyRepository);
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
        timeEntriesA = new ArrayList<TimeEntry>();
        timeEntriesA.add(timeEntryA);
        LocalDateTime dateA = new LocalDateTime(0);
        timeEntryA.setEndTime(dateA);
    }

    /**
     * Test of getProjetcs method, of class TpsServiceImpl.
     */
    @Test
    public void testGetProjetsForPerson() {
        when(projectRepository.findByPersonId(1)).thenReturn(this.projects);

        final List<Project> projs = service.getProjectsForPerson(1);

        assertThat(projectA.getName(), is(projs.get(0).getName()));
        assertThat(projectB.getName(), is(projs.get(1).getName()));
        verify(projectRepository, times(1)).findByPersonId(1);
        verifyNoMoreInteractions(projectRepository);
    }

    /**
     * Test of getProject method, of class TpsServiceImpl.
     */
    @Test
    public void testGetProject() {
        when(projectRepository.findOne(1L)).thenReturn(projectA);

        final Project project = service.getProject(1L);
        assertThat(projectA.getName(), is(project.getName()));

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

        final List<Person> projs = service.getPersons();

        assertThat(personA.getEmail(), is(projs.get(0).getEmail()));
        assertThat(personB.getEmail(), is(projs.get(1).getEmail()));
        verify(personRepository, times(1)).findAll();
        verifyNoMoreInteractions(personRepository);
    }

    /**
     * Test of getPerson method, of class TpsServiceImpl.
     */
    @Test
    public void testGetPerson() {
        when(personRepository.findOne(1L)).thenReturn(personA);

        final Person person = service.getPerson(1L);
        assertThat(personA.getEmail(), is(person.getEmail()));

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
    public void testSaveNewPerson() {
        service.savePerson(personA);
        //verify(personRepository, times(1)).findOne(personA.getId());
        verify(personRepository, times(1)).save(personA);
        verifyNoMoreInteractions(personRepository);
    }

    /**
     * Test of savePerson method, of class TpsServiceImpl.
     */
    @Test
    public void testSaveExistingPerson() {
        Person personMock = mock(Person.class);
        when(personMock.isNew()).thenReturn(Boolean.TRUE);
        when(personMock.getId()).thenReturn(1L);
        service.savePerson(personMock);
        //verify(personRepository, times(1)).findOne(personMock.getId());
        verify(personRepository, times(1)).save(personMock);
        verifyNoMoreInteractions(personRepository);
    }

    /**
     * Test of findPersonByEmail method, of class TpsServiceImpl.
     */
    @Test
    public void testFindPersonByEmail() {
        when(personRepository.findByEmail(EMAIL)).thenReturn(personA);
        final Person person = service.findPersonByEmail(EMAIL);
        assertThat(personA.getEmail(), is(person.getEmail()));
        verify(personRepository, times(1)).findByEmail(EMAIL);
        verifyNoMoreInteractions(personRepository);
    }

    /**
     * Test of getTimeEntriesForPerson method, of class TpsServiceImpl.
     */
    @Test
    public void testGetTimeEntries() {
        final LocalDateTime startTime = new LocalDateTime(0);
        final LocalDateTime endTime = new LocalDateTime();

        when(timeEntryRepository.findByPersonIdAndStartTimeBetween(1, startTime, endTime)).thenReturn(timeEntriesA);
        final List<TimeEntry> timeEntries = service.getTimeEntriesForPerson(1, startTime, endTime);
        assertThat(timeEntriesA.size(), is(timeEntries.size()));
        verify(timeEntryRepository, times(1)).findByPersonIdAndStartTimeBetween(1, startTime, endTime);
        verifyNoMoreInteractions(timeEntryRepository);
    }

    /**
     * Test of getTimeEntry method, of class TpsServiceImpl.
     */
    @Test
    public void testGetTimeEntry() {
        when(timeEntryRepository.findOne(1L)).thenReturn(timeEntryA);

        final TimeEntry timeEntry = service.getTimeEntry(1L);
        assertThat(timeEntryA.getComment(), is(timeEntry.getComment()));
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
