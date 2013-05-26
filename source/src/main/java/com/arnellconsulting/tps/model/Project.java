package com.arnellconsulting.tps.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

/**
 * TBD
 * @author jiar
 */
@Entity
@Data
@SuppressWarnings("PMD")
public class Project implements Serializable {

   @Id
    private String id;

    @NotNull
    private String name;

    private String description;

    @ManyToOne
    private Contract contract;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<TimeEntry> projects = new HashSet<TimeEntry>();

}
