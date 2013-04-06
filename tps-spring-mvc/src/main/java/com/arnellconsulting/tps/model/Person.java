package com.arnellconsulting.tps.model;

import com.arnellconsulting.tps.enums.PersonStatus;
import com.arnellconsulting.tps.enums.RegistrationStatus;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findPeopleByEmail" })
public class Person {

    private String firstName;

    private String lastName;

    @NotNull
    private String email;

    @Enumerated
    private PersonStatus status;

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
}
