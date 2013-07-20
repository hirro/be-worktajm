package com.arnellconsulting.tps.model;

import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * TBD
 * @author jiar
 */
@Entity
@Data
@SuppressWarnings("PMD")
public class TimeEntry  extends AbstractPersistable<Long> {

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date startTime;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date endTime;

    private String comment;

//    @ManyToOne
//    private Person Person;
//
//    @ManyToOne
//    private Project project;
}
