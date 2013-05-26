package com.arnellconsulting.tps.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Entity
@Data
@SuppressWarnings("PMD")
public class Corporate {
    @Id
    private String id;

    @NotNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Set<Customer> customers = new HashSet<Customer>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employer")
    private Set<Person> Persons = new HashSet<Person>();
}
