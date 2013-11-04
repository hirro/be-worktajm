/**
 *
 * Copyright 2013 Jim Arnell
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.arnellconsulting.tps.web.service;

import com.arnellconsulting.tps.model.Contract;
import com.arnellconsulting.tps.model.Corporate;
import com.arnellconsulting.tps.model.Customer;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.webflow.Registration;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.RandomStringUtils;

import org.joda.time.DateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service("registrationService")
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@Repository
@Slf4j
public class JpaRegistrationService implements RegistrationService {
   private static final String DEFAULT_PROJECT_NAME = "Project X";
   private static final String DEFAULT_CORPORATE_NAME = "Example corporate name";

   private transient MailSender mailTemplate;

   @Autowired
   public void setMailTemplate(MailSender mailTemplate) {
      this.mailTemplate = mailTemplate;
   }

   public void setEm(EntityManager em) {
      this.em = em;
   }   

   /** PersistanceContext */
   @SuppressWarnings("PMD.ShortVariable")
   private EntityManager em;

   //~--- methods -------------------------------------------------------------

   @Override
   public void cancelRegistration(final Registration registration) {}

   @Override
   @Transactional(readOnly = true)
   public Registration createRegistration() {
      log.debug("createRegistration");

      final Registration r = new Registration();

      r.setSentChallenge(RandomStringUtils.randomNumeric(4));

      return r;
   }

   @Override
   public void login(final Registration registration) {}

   @Override
   @Transactional
   public void persist(final Registration registration) {
      log.debug("persist({})", registration.getEmail());

      try {
         final Corporate corporate = new Corporate();

         corporate.setName(registration.getCorporateName());

         final Person person = new Person();

//         person.setUsername(registration.getEmail());
//         person.setEmployer(corporate);
//         person.setPassword(registration.getPassword());
//         person.setEnabled(true);
//         person.setAuthority("ROLE_ADMIN");

         // Default customer
         final Customer customer = new Customer();

         customer.setName(registration.getCorporateName());

         // Default contract
         final Contract contract = new Contract();

//         contract.setCustomer(customer);

         // Mandatory
         contract.setValidFrom(new DateTime().toDate());

         // Mandatory
         contract.setValidTo(new DateTime().toDate());

         // Mandatory
         contract.setRate(BigDecimal.valueOf(0));

         // Default project
         final Project project = new Project();

         project.setName(DEFAULT_PROJECT_NAME);
//         project.setContract(contract);
//         contract.getProjects().add(project);

         // Persist
         em.persist(corporate);
         em.persist(customer);
         em.persist(corporate);
         em.persist(contract);
         em.persist(project);
         em.persist(person);
         log.debug("Successfully persisted entry with email {}", registration.getEmail());
      } catch (Exception e) {
         log.error("Failed to persist entry", e);
      }
   }

   @Override
   public void sendChallenge(final Registration registration) {
      log.debug("sendChallenge(sendChallenge: {}, email: {})",
                registration.getSentChallenge(),
                registration.getEmail());

      final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

      simpleMailMessage.setTo(registration.getEmail());
      simpleMailMessage.setText(registration.getSentChallenge());
      simpleMailMessage.setFrom("jim@arnellconsulting.com");
      simpleMailMessage.setSubject("Almost ready to report time");
      mailTemplate.send(simpleMailMessage);
   }

   @Override
   public boolean verifyChallenge(final Registration registration) {
      log.debug("verifyChallenge user: {}, sent-otp: {}, received-otp: {}",
                registration.getEmail(),
                registration.getSentChallenge(),
                registration.getReceivedChallenge());

      if ((registration.getReceivedChallenge() != null) && (registration.getSentChallenge() != null)) {
         return registration.getReceivedChallenge().equals(registration.getSentChallenge());
      } else {
         return false;
      }
   }

   //~--- get methods ---------------------------------------------------------

   @Override
   public final boolean isUsernameUnique(final String username) {
      final Object person =
         em.createQuery("select u from Person u where u.username = :username").setParameter("username",
              username).getSingleResult();

      return person == null;
   }

   //~--- set methods ---------------------------------------------------------

   @PersistenceContext
   public void setEntityManager(final EntityManager em) {
      this.em = em;
   }

   //~--- methods -------------------------------------------------------------

   private static String scramblePassword(final String password) {
      final StringBuffer sb = new StringBuffer();

      for (int i = 0; i < password.length(); i++) {
         sb.append('*');
      }

      return sb.toString();
   }
}
