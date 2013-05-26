package com.arnellconsulting.tps.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Entity
@Data
@SuppressWarnings("PMD")
public class Contract {
    @Id
    private String id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MS")
    private Date validFrom;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MS")
    private Date validTo;

    @NotNull
    private BigDecimal rate;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Person> contracters = new HashSet<Person>();

    @ManyToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contract")
    private Set<Project> projects = new HashSet<Project>();
}
