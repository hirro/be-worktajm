package com.arnellconsulting.tps.webflow;

import java.math.BigDecimal;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.arnellconsulting.tps.model.Contract;
import com.arnellconsulting.tps.model.Corporate;
import com.arnellconsulting.tps.model.Customer;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;

@Service("registrationService")
@Repository
public class JpaRegistrationService implements RegistrationService {
    private static final String DEFAULT_PROJECT_NAME = "Project X";
    private static final String DEFAULT_CORPORATE_NAME = "Example corporate name";

    private static final Logger log = LoggerFactory.getLogger(JpaRegistrationService.class);

    @Autowired
    private transient MailSender mailTemplate;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Override
    public Registration createRegistration() {
        log.debug("createRegistration");

        Registration r = new Registration();
        r.setCorporateName(DEFAULT_CORPORATE_NAME);
        r.setSentChallenge(RandomStringUtils.randomNumeric(4));
        return r;
    }

    @Override
    public void persist(Registration registration) {
        log.debug("persist({})", registration.getEmail());

        try {
            Corporate corporate = new Corporate();
            corporate.setName(registration.getCorporateName());
            Person person = new Person();
            person.setEmail(registration.getEmail());
            person.setEmployer(corporate);
    
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
            corporate.persist();
            customer.persist();
            contract.persist();
            project.persist();
            person.persist();
            log.debug("Successfully persisted entry with email {}", registration.getEmail());
        } catch (Exception e) {
            log.error("Failed to persist entry", e);
        }
    }

    @Override
    public boolean verifyChallenge(Registration registration) {
        log.debug("verifyChallenge user: %s, sent-otp: %s, received-otp: %s", 
                registration.getEmail(),
                registration.getSentChallenge(), 
                registration.getReceivedChallenge());
        
        if ((registration.getReceivedChallenge() != null) && (registration.getSentChallenge() != null )) {
            return registration.getReceivedChallenge().equals(registration.getSentChallenge());            
        } else {
            return false;
        }

    }

    @Override
    public void cancelRegistration(Long id) {
    }

    @Override
    public void sendChallenge(Registration registration) {
        log.debug("sendChallenge(sendChallenge: {}, email: {})", 
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
    public void login(Registration registration) {
        log.debug("login(email: {})", registration.getEmail());
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            registration.getEmail(), 
                            registration.getPassword()));
            if (authenticate.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authenticate);
                log.debug("You are logged in as {}", registration.getEmail());
            } else {
                log.error("You failed to log in as {}", registration.getEmail());
            }
            log.debug("Login succeeded");
        } catch (Exception e) {
            log.error("You failed to log in}", e);
        }
    }
}
