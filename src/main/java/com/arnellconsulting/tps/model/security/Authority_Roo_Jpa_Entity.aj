// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.arnellconsulting.tps.model.security;

import com.arnellconsulting.tps.model.security.Authority;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

privileged aspect Authority_Roo_Jpa_Entity {
    
    declare @type: Authority: @Entity;
    
    declare @type: Authority: @Table(name = "security_authorities");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Authority.id;
    
    @Version
    @Column(name = "version")
    private Integer Authority.version;
    
    public Long Authority.getId() {
        return this.id;
    }
    
    public void Authority.setId(Long id) {
        this.id = id;
    }
    
    public Integer Authority.getVersion() {
        return this.version;
    }
    
    public void Authority.setVersion(Integer version) {
        this.version = version;
    }
    
}
