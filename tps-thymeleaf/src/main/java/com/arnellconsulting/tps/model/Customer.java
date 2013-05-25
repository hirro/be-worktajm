package com.arnellconsulting.tps.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
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
