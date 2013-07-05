/*
 * Time Reporing Server.
 *
 * Copyright 2013 Arnell Consulting AB
 */



package com.arnellconsulting.tps.repository;

import com.arnellconsulting.tps.model.Person;

import org.springframework.dao.DataAccessException;


/**
 *
 * @author jiar
 */
public interface PersonRepository {

   /**
    *
    * @param email
    * @return
    */
   Person findByEmail(final String email);

   Person findById(int id) throws DataAccessException;

   void save(Person person) throws DataAccessException;
}
