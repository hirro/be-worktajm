package com.arnellconsulting.tps.service;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implements the TpsService interface.
 *
 * @author jiar
 */
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
      this.personRepository.save(person);
      return person;
   }

   @Override
   public Person delete(final int id) {
      throw new UnsupportedOperationException("Not supported yet.");    // To change body of generated methods, choose Tools | Templates.
   }

   @Override
   public Person findPersonByEmail(final String email) {
      return this.personRepository.findByEmail(email);
   }

   @Override
   public Person update(final Person person) {
      this.personRepository.save(person);

      return person;
   }
}
