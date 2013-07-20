package com.arnellconsulting.tps.service;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;
import java.util.List;

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

   public List<Project> getProjets();
      
   public Project getProjectById(Long id);
   
}
