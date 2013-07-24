/*
 * Time Reporing Server.
 *
 * Copyright 2013 Arnell Consulting AB
 */



package com.arnellconsulting.tps.service;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.model.TimeEntry;

import java.util.List;

/**
 * Separation between repositories.
 *
 * @author jiar
 */
@SuppressWarnings({ "PMD.UnusedModifier", "PMD.ShortVariable" })
public interface TpsService {

   /**
    * Find person by email.
    *
    * @param email
    * @return person if found, null otherwise.
    */
   Person findPersonByEmail(final String email);

   /**
    *  Create the person.
    *
    * @param person
    */
   Person create(final Person person);

   /**
    *  Delete the person.
    *
    * @param person
    */
   Person delete(final int id);

   /**
    *  Update the person.
    *
    * @param person
    */
   Person update(final Person person);

   public List<Project> getProjets();

   public Project getProjectById(final long id);

   public List<TimeEntry> getTimeEntries();

   public void deleteProject(final long id);

   public void saveProject(final Project project);

   public void updatePerson(final Person person);

   public void deletePerson(final long id);

   public Person getPersonById(final long id);

   public List<Person> getPersons();

   public void deleteTimeEntry(final long id);

   public void updateTimeEntry(final TimeEntry timeEntry);

   public TimeEntry getTimeEntryById(final long id);
}
