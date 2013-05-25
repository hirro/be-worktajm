package com.arnellconsulting.tps.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project {

    @NotNull
    private String name;

    private String description;

    @ManyToOne
    private Contract contract;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<TimeEntry> projects = new HashSet<TimeEntry>();

}
