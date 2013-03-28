package com.arnellconsulting.tps.webflow;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.stereotype.Controller;

import com.arnellconsulting.tps.model.Contract;
import com.arnellconsulting.tps.model.Corporate;
import com.arnellconsulting.tps.model.Customer;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;

public class Registration{

    private static final String DEFAULT_PROJECT_NAME = "Project X";

    @NotNull
    private String corporateName;

    @NotNull
    private String email;

    private String password;
    
    public Corporate persist() {
        Corporate corporate = new Corporate();
        corporate.setName(corporateName);
        Person person = new Person();
        person.setEmail(email);
        person.setEmployer(corporate);
        
        // Default customer
        Customer customer = new Customer();
        customer.setName(corporateName);
        
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
        corporate.persist();

        return corporate;
    }
}
