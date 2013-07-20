package com.arnellconsulting.tps.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@SuppressWarnings("PMD")
public class Customer  extends AbstractPersistable<Long> {

    @NotNull
    private String name;

    private String organisationalNumber;

    private String street;

    private String streetNumber;

    private String zipCode;

    private String country;

//    @ManyToOne
//    private Corporate client;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
//    private final Set<Contract> contracts = new HashSet<Contract>();
}
