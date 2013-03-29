package com.arnellconsulting.tps.webflow;

import java.math.BigDecimal;

import org.joda.time.DateTime;
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
    private static final String DEFAULT_EMAIL = "example@example.org";
    private static final String DEFAULT_CORPORATE_NAME = "Example corporate name";
    
    @Override
    public Registration createRegistration() {
         Registration r = new Registration();
         r.setEmail(DEFAULT_EMAIL);
         r.setCorporateName(DEFAULT_CORPORATE_NAME);
         return r;
    }

    @Override
    public void persist(Registration registration) {
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
    }

    @Override
    public boolean verifyChallenge(Long id, String challenge) {
        return true;
    }

    @Override
    public void cancelRegistration(Long id) {
    }


}
