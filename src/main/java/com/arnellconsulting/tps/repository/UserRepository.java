/*
 * Time Reporing Server.
 * 
 * Copyright 2013 Arnell Consulting AB
 */
package com.arnellconsulting.tps.repository;

import com.arnellconsulting.tps.model.Person;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author jiar
 */
interface PersonRepository {

    /**
     * Retrieve all <code>Person</code>s from the data store.
     *
     * @return a <code>Collection</code> of <code>Person</code>s
     */
    List<Person> findPersonTypes() throws DataAccessException;

    /**
     * Retrieve a <code>Pet</code> from the data store by id.
     *
     * @param id the id to search for
     * @return the <code>Person</code> if found
     * @throws org.springframework.dao.DataRetrievalFailureException
     *          if not found
     */
    Person findById(int id) throws DataAccessException;

    /**
     * Save a <code>Pet</code> to the data store, either inserting or updating it.
     *
     * @param person the <code>Pet</code> to save
     * @see BaseEntity#isNew
     */
    void save(Person person) throws DataAccessException;
   
   
}
