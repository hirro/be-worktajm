package com.worktajm.web.rest;

import com.worktajm.Application;
import com.worktajm.domain.TimeEntry;
import com.worktajm.repository.TimeEntryRepository;
import com.worktajm.service.TimeEntryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TimeEntryResource REST controller.
 *
 * @see TimeEntryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TimeEntryResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_TIME_STR = dateTimeFormatter.format(DEFAULT_START_TIME);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_TIME_STR = dateTimeFormatter.format(DEFAULT_END_TIME);
    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    @Inject
    private TimeEntryRepository timeEntryRepository;

    @Inject
    private TimeEntryService timeEntryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTimeEntryMockMvc;

    private TimeEntry timeEntry;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimeEntryResource timeEntryResource = new TimeEntryResource();
        ReflectionTestUtils.setField(timeEntryResource, "timeEntryService", timeEntryService);
        this.restTimeEntryMockMvc = MockMvcBuilders.standaloneSetup(timeEntryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        timeEntry = new TimeEntry();
        timeEntry.setStartTime(DEFAULT_START_TIME);
        timeEntry.setEndTime(DEFAULT_END_TIME);
        timeEntry.setComment(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createTimeEntry() throws Exception {
        int databaseSizeBeforeCreate = timeEntryRepository.findAll().size();

        // Create the TimeEntry

        restTimeEntryMockMvc.perform(post("/api/timeEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeEntry)))
                .andExpect(status().isCreated());

        // Validate the TimeEntry in the database
        List<TimeEntry> timeEntrys = timeEntryRepository.findAll();
        assertThat(timeEntrys).hasSize(databaseSizeBeforeCreate + 1);
        TimeEntry testTimeEntry = timeEntrys.get(timeEntrys.size() - 1);
        assertThat(testTimeEntry.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTimeEntry.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testTimeEntry.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void getAllTimeEntrys() throws Exception {
        // Initialize the database
        timeEntryRepository.saveAndFlush(timeEntry);

        // Get all the timeEntrys
        restTimeEntryMockMvc.perform(get("/api/timeEntrys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(timeEntry.getId().intValue())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME_STR)))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME_STR)))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getTimeEntry() throws Exception {
        // Initialize the database
        timeEntryRepository.saveAndFlush(timeEntry);

        // Get the timeEntry
        restTimeEntryMockMvc.perform(get("/api/timeEntrys/{id}", timeEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(timeEntry.getId().intValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME_STR))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME_STR))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeEntry() throws Exception {
        // Get the timeEntry
        restTimeEntryMockMvc.perform(get("/api/timeEntrys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeEntry() throws Exception {
        // Initialize the database
        timeEntryRepository.saveAndFlush(timeEntry);

		int databaseSizeBeforeUpdate = timeEntryRepository.findAll().size();

        // Update the timeEntry
        timeEntry.setStartTime(UPDATED_START_TIME);
        timeEntry.setEndTime(UPDATED_END_TIME);
        timeEntry.setComment(UPDATED_COMMENT);

        restTimeEntryMockMvc.perform(put("/api/timeEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeEntry)))
                .andExpect(status().isOk());

        // Validate the TimeEntry in the database
        List<TimeEntry> timeEntrys = timeEntryRepository.findAll();
        assertThat(timeEntrys).hasSize(databaseSizeBeforeUpdate);
        TimeEntry testTimeEntry = timeEntrys.get(timeEntrys.size() - 1);
        assertThat(testTimeEntry.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTimeEntry.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testTimeEntry.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deleteTimeEntry() throws Exception {
        // Initialize the database
        timeEntryRepository.saveAndFlush(timeEntry);

		int databaseSizeBeforeDelete = timeEntryRepository.findAll().size();

        // Get the timeEntry
        restTimeEntryMockMvc.perform(delete("/api/timeEntrys/{id}", timeEntry.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeEntry> timeEntrys = timeEntryRepository.findAll();
        assertThat(timeEntrys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
