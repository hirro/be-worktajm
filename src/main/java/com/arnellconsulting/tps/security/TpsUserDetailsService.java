
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
   private final transient PersonRepository personRepository;

   @Autowired
   public TpsUserDetailsService(final PersonRepository personRepository) {
      this.personRepository = personRepository;
   }

   //~--- methods -------------------------------------------------------------

   @Override
   public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
      log.debug("loadUserByUsername({}(", username);
      final Person person = personRepository.findByEmail(username);
      if (person == null) {
         throw new UsernameNotFoundException(username);
      }

      return null;
   }
}
