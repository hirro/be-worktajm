// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.arnellconsulting.tps.model.security;

import com.arnellconsulting.tps.model.security.AuthorityPrincipalAssignment;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

privileged aspect AuthorityPrincipalAssignment_Roo_Jpa_Entity {
    
    declare @type: AuthorityPrincipalAssignment: @Entity;
    
    declare @type: AuthorityPrincipalAssignment: @Table(name = "security_role_assignments");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long AuthorityPrincipalAssignment.id;
    
    @Version
    @Column(name = "version")
    private Integer AuthorityPrincipalAssignment.version;
    
    public Long AuthorityPrincipalAssignment.getId() {
        return this.id;
    }
    
    public void AuthorityPrincipalAssignment.setId(Long id) {
        this.id = id;
    }
    
    public Integer AuthorityPrincipalAssignment.getVersion() {
        return this.version;
    }
    
    public void AuthorityPrincipalAssignment.setVersion(Integer version) {
        this.version = version;
    }
    
}
