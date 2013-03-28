package com.arnellconsulting.tps.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Customer {

    @NotNull
    private String name;

    private String organisationalNumber;

    private String street;

    private String streetNumber;

    private String zipCode;

    private String country;

    @ManyToOne
    private Corporate client;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Contract> contracts = new HashSet<Contract>();
}
