package com.arnellconsulting.tps.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * TBD
 * @author jiar
 */
@Entity
@Data
@SuppressWarnings("PMD")
public class Project extends AbstractPersistable<Long> {

	private static final long serialVersionUID = -3902305943341540214L;
   
@Id
  @GeneratedValue(strategy = GenerationType.AUTO)

   @NotNull
    private String name;

    private String description;

    @ManyToOne
    private Contract contract;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
//    private final Set<TimeEntry> projects = new HashSet<TimeEntry>();

}
