
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.arnellconsulting.tps.repository.jpa;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.repository.PersonRepository;

import org.springframework.dao.DataAccessException;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

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
      log.debug("findByEmail: {}", email);
      Query query = this.em.createQuery("SELECT p FROM Person WHERE username = :email");

      query.setParameter("email", email);

      return (Person) query.getSingleResult();
   }

   @Override
   public Person findById(final int id) throws DataAccessException {
      Query query = this.em.createQuery("SELECT person FROM Person WHERE person.id =:id");

      query.setParameter("id", id);

      return (Person) query.getSingleResult();
   }

   @Override
   public void save(final Person person) throws DataAccessException {
        this.em.merge(person);
   }
}
