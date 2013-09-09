package com.arnellconsulting.tps.webflow;

import com.arnellconsulting.tps.model.Contract;
import com.arnellconsulting.tps.model.Corporate;
import com.arnellconsulting.tps.model.Customer;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;

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

   @Autowired
   private transient MailSender mailTemplate;

   /** PersistanceContext */
   @SuppressWarnings("PMD.ShortVariable")
   private EntityManager em;

   //~--- methods -------------------------------------------------------------

   @Override
   public void cancelRegistration(final Long id) {}

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
