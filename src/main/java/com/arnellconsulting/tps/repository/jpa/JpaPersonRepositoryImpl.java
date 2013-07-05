
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.arnellconsulting.tps.repository.jpa;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.repository.PersonRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jiar
 */
@Repository
@Slf4j
public class JpaPersonRepositoryImpl implements PersonRepository {
   @PersistenceContext
   private transient EntityManager em;

   //~--- methods -------------------------------------------------------------

   @Override
   public Person findByEmail(final String email) {
      final Query query = this.em.createQuery("SELECT p FROM Person p WHERE p.email = :email");

      query.setParameter("email", email);

      final List resultList = query.getResultList();
      final Person result;

      if (resultList.isEmpty()) {
         log.debug("findByEmail: Failed to find {}", email);
         result = null;
      } else {
         result = (Person) resultList.get(0);
         log.debug("findByEmail: Looked up {}, firstName: {}, lastName: {}",
                   email,
                   result.getFirstName(),
                   result.getLastName());
      }

      return result;
   }

   @Override
   public Person findById(final int id) throws DataAccessException {
      final Query query = this.em.createQuery("SELECT person FROM Person personWHERE person.id =:id");

      query.setParameter("id", id);

      final List resultList = query.getResultList();
      final Person result;

      if (resultList.isEmpty()) {
         result = null;
      } else {
         result = (Person) resultList.get(0);
      }

      return result;
   }

   @Override
   public void save(final Person person) throws DataAccessException {
      try {
         if (person.isNew()) {
            log.debug("Creating new person object");
            this.em.persist(person);
            em.flush();
         } else {
            log.debug("Updating person object");
            this.em.merge(person);
         }
         log.debug("Successfully saved");
      } catch (Exception e) {
         log.debug("dfsdf@", e);
      }
   }
}
