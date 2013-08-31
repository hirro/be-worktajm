
/*
* TBD.
 */
package com.arnellconsulting.tps.security;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author jiar
 */
@Slf4j
public class TpsUserDetailsService implements UserDetailsService {
   private final transient PersonRepository repository;

   @Autowired
   public TpsUserDetailsService(final PersonRepository repository) {
      this.repository = repository;
   }

   //~--- methods -------------------------------------------------------------

   @Override
   public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
      log.debug("loadUserByUsername({}(", username);
      final Person person = repository.findByEmail(username);
      if (person == null) {
         throw new UsernameNotFoundException(username);
      }

      log.debug("Loaded the user, firstName: {}, lastName: {}", person.getFirstName(), person.getLastName());
      final PersonUserDetails details = new PersonUserDetails();
      details.setPerson(person);
      details.setPassword("password");
      return details;
   }
}
