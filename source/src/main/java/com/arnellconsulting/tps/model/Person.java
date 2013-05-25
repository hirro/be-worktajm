package com.arnellconsulting.tps.model;

import com.arnellconsulting.tps.common.PersonStatus;
import com.arnellconsulting.tps.common.RegistrationStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person {

    private String firstName;

    private String lastName;

    private String password;

    @Column(name = "username")
    @NotNull
    private String userName;

    private String authority;

    @Enumerated
    private PersonStatus status;

    private Boolean enabled;

    @Enumerated
    private RegistrationStatus registrationStatus;

    @ManyToOne
    private Corporate employer;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "contracters")
    private Set<Contract> contracts = new HashSet<Contract>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Person")
    private Set<TimeEntry> contract = new HashSet<TimeEntry>();

    public Person() {
        this.status = PersonStatus.NORMAL;
        this.registrationStatus = RegistrationStatus.PENDING;
    }

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
