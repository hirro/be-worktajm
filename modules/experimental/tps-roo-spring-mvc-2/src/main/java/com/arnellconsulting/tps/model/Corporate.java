package com.arnellconsulting.tps.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Corporate {

    @NotNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Set<Customer> customers = new HashSet<Customer>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employer")
    private Set<Person> Persons = new HashSet<Person>();
}
