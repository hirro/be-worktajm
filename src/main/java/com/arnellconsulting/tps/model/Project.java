package com.arnellconsulting.tps.model;

import lombok.Data;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

/**
 * TBD
 * @author jiar
 */
@Entity
@Data
@SuppressWarnings("PMD")
public class Project extends AbstractPersistable<Long> {
   private static final long serialVersionUID = -3902305943341540214L;

   private String name;
   private String description;

// @ManyToOne
// private Contract contract;

// @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
// private final Set<TimeEntry> projects = new HashSet<TimeEntry>();
}
