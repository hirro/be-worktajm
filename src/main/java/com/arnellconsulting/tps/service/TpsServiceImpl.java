package com.arnellconsulting.tps.service;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * Implements the TpsService interface.
 *
 * @author jiar
 */
@Slf4j
@Service
public class TpsServiceImpl implements TpsService {
   private final PersonRepository personRepository;

   @Autowired
   public TpsServiceImpl(final PersonRepository personRepository) {
      this.personRepository = personRepository;
   }

   //~--- methods -------------------------------------------------------------

   @Override
   public Person create(final Person person) {
      try {
         log.debug("Create person");
         this.personRepository.save(person);
      } catch (DataAccessException e) {
         log.debug("Failed to create entry", e);
      }
      return person;
   }

   @Override
   public Person delete(final int id) {
      log.debug("Delete person");
      throw new UnsupportedOperationException("Not supported yet.");    // To change body of generated methods, choose Tools | Templates.
   }

   @Override
   public Person findPersonByEmail(final String email) {
     log.debug("Find person by email");
      return this.personRepository.findByEmail(email);
   }

   @Override
   public Person update(final Person person) {
      log.debug("Update person");
      this.personRepository.save(person);

      return person;
   }
}
