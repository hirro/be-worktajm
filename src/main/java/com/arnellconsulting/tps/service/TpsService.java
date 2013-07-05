package com.arnellconsulting.tps.service;

import com.arnellconsulting.tps.model.Person;

/**
 *
 * @author jiar
 */
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
   
}
