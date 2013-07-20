//package com.arnellconsulting.tps.model;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.util.HashSet;
//import java.util.Set;
//import lombok.Data;
//
//@Entity
//@Data
//@SuppressWarnings("PMD")
//public class Customer {
//    @Id
//    private String id;
//
//    @NotNull
//    private String name;
//
//    private String organisationalNumber;
//
//    private String street;
//
//    private String streetNumber;
//
//    private String zipCode;
//
//    private String country;
//
//    @ManyToOne
//    private Corporate client;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
//    private final Set<Contract> contracts = new HashSet<Contract>();
//}
