/*
 * Time Reporing Server.
 *
 * Copyright 2013 Arnell Consulting AB
 */



package com.arnellconsulting.tps.repository;

import com.arnellconsulting.tps.model.Person;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jiar
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
   public Person findByEmail(String email);
}
