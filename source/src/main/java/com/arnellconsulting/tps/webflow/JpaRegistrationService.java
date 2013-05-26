package com.arnellconsulting.tps.webflow;

import com.arnellconsulting.tps.model.Contract;
import com.arnellconsulting.tps.model.Corporate;
import com.arnellconsulting.tps.model.Customer;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;

import org.apache.commons.lang3.RandomStringUtils;

import org.joda.time.DateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class JpaRegistrationService implements RegistrationService {
   private static final String DEFAULT_PROJECT_NAME = "Project X";
   private static final String DEFAULT_CORPORATE_NAME = "Example corporate name";
   private static final Logger LOG = LoggerFactory.getLogger(JpaRegistrationService.class);

   @Autowired
   private transient MailSender mailTemplate;

   /** PersistanceContext */
   @SuppressWarnings("PMD.ShortVariable")
   private EntityManager em;

   //~--- methods -------------------------------------------------------------

   @Override
   public void cancelRegistration(Long id) {}

   @Override
   @Transactional(readOnly = true)
   public Registration createRegistration() {
      LOG.debug("createRegistration");

      Registration r = new Registration();

      r.setCorporateName(DEFAULT_CORPORATE_NAME);
      r.setSentChallenge(RandomStringUtils.randomNumeric(4));

      return r;
   }

   @Override
   public void login(Registration registration) {}

   @Override
   @Transactional
   public void persist(Registration registration) {
      LOG.debug("persist({})", registration.getEmail());

      try {
         Corporate corporate = new Corporate();

         corporate.setName(registration.getCorporateName());

         Person person = new Person();

         person.setUserName(registration.getEmail());
         person.setEmployer(corporate);
         person.setPassword(registration.getPassword());
         person.setEnabled(true);
         person.setAuthority("ROLE_ADMIN");

         // Default customer
         Customer customer = new Customer();

         customer.setName(registration.getCorporateName());

         // Default contract
         Contract contract = new Contract();

         contract.setCustomer(customer);

         // Mandatory
         contract.setValidFrom(new DateTime().toDate());

         // Mandatory
         contract.setValidTo(new DateTime().toDate());

         // Mandatory
         contract.setRate(BigDecimal.valueOf(0));

         // Default project
         Project project = new Project();

         project.setName(DEFAULT_PROJECT_NAME);
         project.setContract(contract);
         contract.getProjects().add(project);

         // Persist
         em.persist(corporate);
         em.persist(customer);
         em.persist(corporate);
         em.persist(contract);
         em.persist(project);
         em.persist(person);
         LOG.debug("Successfully persisted entry with email {}", registration.getEmail());
      } catch (Exception e) {
         LOG.error("Failed to persist entry", e);
      }
   }

   @Override
   public void sendChallenge(Registration registration) {
      LOG.debug("sendChallenge(sendChallenge: {}, email: {})",
                registration.getSentChallenge(),
                registration.getEmail());

      SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

      simpleMailMessage.setTo(registration.getEmail());
      simpleMailMessage.setText(registration.getSentChallenge());
      simpleMailMessage.setFrom("jim@arnellconsulting.com");
      simpleMailMessage.setSubject("Almost ready to report time");
      mailTemplate.send(simpleMailMessage);
   }

   @Override
   public boolean verifyChallenge(Registration registration) {
      LOG.debug("verifyChallenge user: {}, sent-otp: {}, received-otp: {}",
                registration.getEmail(),
                registration.getSentChallenge(),
                registration.getReceivedChallenge().toString());

      if ((registration.getReceivedChallenge() != null) && (registration.getSentChallenge() != null)) {
         return registration.getReceivedChallenge().equals(registration.getSentChallenge());
      } else {
         return false;
      }
   }

   //~--- set methods ---------------------------------------------------------

   @PersistenceContext
   public void setEntityManager(EntityManager em) {
      this.em = em;
   }

   //~--- methods -------------------------------------------------------------

   @Override
      public final boolean isUsernameUnique(final String username) 
   {
      Object person = em.createQuery("select u from Person u where u.username = :username").setParameter("username",
              username).getSingleResult();
      return person == null;
      
   }

   private String scramblePassword(String password) {
      StringBuffer sb = new StringBuffer();

      for (int i = 0; i < password.length(); i++) {
         sb.append('*');
      }

      return sb.toString();
   }
}
