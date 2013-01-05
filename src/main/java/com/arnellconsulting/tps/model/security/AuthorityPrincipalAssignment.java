package com.arnellconsulting.tps.model.security;

import javax.persistence.ManyToOne;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "security_role_assignments")
public class AuthorityPrincipalAssignment {

    @ManyToOne
    private Principal username;

    @ManyToOne
    private Authority roleId;
}
