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
package com.arnellconsulting.tps.rest.controllers;

import com.arnellconsulting.tps.common.TestConstants;
import com.arnellconsulting.tps.config.TestContext;
import com.arnellconsulting.tps.config.WebAppContext;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.model.TimeEntry;
import com.arnellconsulting.tps.security.PersonUserDetails;
import com.arnellconsulting.tps.service.TpsService;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import org.joda.time.DateTime;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author hirro
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
@WebAppConfiguration
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class TimeEntryControllerTest {

    private transient MockMvc mockMvc;
    private transient TimeEntry timeEntryA;
    private transient Person person1;
    private transient Person person2;
    private transient List<TimeEntry> timeEntries;
    @Autowired
    private transient TpsService tpsServiceMock;
    @Autowired
    private transient WebApplicationContext webApplicationContext;
    @Autowired
    private transient PersonUserDetails personUserDetails;
    private UsernamePasswordAuthenticationToken principal;

    @Before
    public void setUp() {

      // We have to reset our mock between tests because the mock objects
        // are managed by the Spring container. If we would not reset them,
        // stubbing and verified behavior would "leak" from one test to another.
        Mockito.reset(tpsServiceMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        person1 = spy(TestConstants.createPersonA());
        person2 = spy(TestConstants.createPersonA());
        Project project = TestConstants.createProjectA();
        timeEntryA = TestConstants.createTimeEntryA(person1, project);
        timeEntries = new ArrayList<TimeEntry>();
        timeEntries.add(timeEntryA);
        when(person1.getId()).thenReturn(1L);
        when(person2.getId()).thenReturn(2L);
        when(personUserDetails.getPerson()).thenReturn(person1);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(TestConstants.PERSON_A_EMAIL, TestConstants.PERSON_A_PASSWORD);
        principal = spy(token);
        when(principal.getPrincipal()).thenReturn(personUserDetails);
    }

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(
                post("/timeEntry")
                .principal(principal)
                .content(TestConstants.TIMEENTRY_A_CREATE)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        final ArgumentCaptor<TimeEntry> argument = ArgumentCaptor.forClass(TimeEntry.class);

        verify(tpsServiceMock, times(1)).saveTimeEntry(argument.capture());
        assertThat(argument.getValue().getComment(), is(TestConstants.TIMEENTRY_A_COMMENT));
        verifyNoMoreInteractions(tpsServiceMock);
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(
                delete("/timeEntry/1")
                .principal(principal)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
        verify(tpsServiceMock, times(1)).deleteTimeEntry(1L);
        verifyNoMoreInteractions(tpsServiceMock);
    }

    @Test
    public void testList() throws Exception {
        when(
                tpsServiceMock.getTimeEntriesForPerson(
                        Mockito.anyLong(),
                        Mockito.any(DateTime.class),
                        Mockito.any(DateTime.class)
                )
        ).thenReturn(timeEntries);
        mockMvc.perform(
                get("/timeEntry")
                .principal(principal)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        verify(tpsServiceMock, times(1)).getTimeEntriesForPerson(
                Mockito.anyLong(),
                Mockito.any(DateTime.class),
                Mockito.any(DateTime.class));
        verifyNoMoreInteractions(tpsServiceMock);
    }

    @Test
    public void testListWithDefinedRange() throws Exception {
        final DateTime fromDate = new DateTime(0);
        final DateTime toDate = new DateTime(1);
        when(
                tpsServiceMock.getTimeEntriesForPerson(
                        Mockito.anyLong(),
                        Mockito.any(DateTime.class),
                        Mockito.any(DateTime.class)
                )
        ).thenReturn(timeEntries);
        mockMvc.perform(
                get("/timeEntry")
                .param("from", fromDate.toString())
                .param("to", toDate.toString())
                .principal(principal)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        verify(
                tpsServiceMock,
                times(1)
        ).getTimeEntriesForPerson(
                Mockito.anyLong(),
                Mockito.any(DateTime.class),
                Mockito.any(DateTime.class));
        verifyNoMoreInteractions(tpsServiceMock);
    }

    @Test
    public void testRead() throws Exception {
        when(tpsServiceMock.getTimeEntry(1)).thenReturn(timeEntryA);
        mockMvc.perform(
                get("/timeEntry/1")
                .principal(principal)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestConstants.APPLICATION_JSON_UTF8))
                // XXX Works on two dev machines and Travis but not Codeship
                //.andExpect(jsonPath("startTime", is("2010-05-20T22:00:00Z")));
                .andExpect(jsonPath("startTime", startsWith("2010-05-20T")));

        verify(tpsServiceMock, times(1)).getTimeEntry(1L);
        verifyNoMoreInteractions(tpsServiceMock);
    }

    @Test
    public void testUpdate() throws Exception {
        final TimeEntry timeEntry = spy(timeEntryA);
        when(timeEntry.getId()).thenReturn(1L);
        when(timeEntry.getPerson()).thenReturn(person1);
        when(tpsServiceMock.getTimeEntry(1)).thenReturn(timeEntry);
        mockMvc.perform(
                put("/timeEntry/1")
                .principal(principal)
                .content(TestConstants.TIMEENTRY_A_UPDATE)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        final ArgumentCaptor<TimeEntry> argument = ArgumentCaptor.forClass(TimeEntry.class);

        verify(tpsServiceMock, times(1)).saveTimeEntry(argument.capture());
        verify(tpsServiceMock, times(1)).getTimeEntry(1);
        verifyNoMoreInteractions(tpsServiceMock);
    }

    @Test
    public void testUpdateNotExisting() throws Exception {
        final TimeEntry timeEntry = spy(timeEntryA);

        when(timeEntry.getId()).thenReturn(1L);
        when(timeEntry.getPerson()).thenReturn(person2);
        when(tpsServiceMock.getTimeEntry(1)).thenReturn(null);
        mockMvc.perform(
                put("/timeEntry/1")
                .principal(principal)
                .content(TestConstants.TIMEENTRY_A_UPDATE)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
        verify(tpsServiceMock, times(1)).getTimeEntry(1);
        verifyNoMoreInteractions(tpsServiceMock);
    }

    @Test
    public void testUpdateNotOwned() throws Exception {
        final TimeEntry timeEntry = spy(timeEntryA);

        when(timeEntry.getId()).thenReturn(1L);
        when(timeEntry.getPerson()).thenReturn(person2);
        when(tpsServiceMock.getTimeEntry(1)).thenReturn(timeEntry);
        mockMvc.perform(
                put("/timeEntry/1")
                .principal(principal)
                .content(TestConstants.TIMEENTRY_A_UPDATE)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isUnauthorized());
        verify(tpsServiceMock, times(1)).getTimeEntry(1);
        verifyNoMoreInteractions(tpsServiceMock);
    }

}
