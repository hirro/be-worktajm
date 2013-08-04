
/*
* TBD.
 */
package com.arnellconsulting.tps.security;

import com.arnellconsulting.tps.model.PersonUserDetails;
import com.arnellconsulting.tps.repository.PersonUserDetailsRepository;
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
   private final transient PersonUserDetailsRepository repository;

   @Autowired
   public TpsUserDetailsService(final PersonUserDetailsRepository repository) {
      this.repository = repository;
   }

   //~--- methods -------------------------------------------------------------

   @Override
   public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
      log.debug("loadUserByUsername({}(", username);
      final PersonUserDetails person = repository.findByEmail(username);
      if (person == null) {
         throw new UsernameNotFoundException(username);
      }

      return person;
   }
}
