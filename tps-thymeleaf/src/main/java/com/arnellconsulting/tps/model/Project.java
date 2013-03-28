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
public class Project {

    @NotNull
    private String name;

    private String description;

    @ManyToOne
    private Contract contract;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<TimeEntry> projects = new HashSet<TimeEntry>();
}
