// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.arnellconsulting.tps.model;

import com.arnellconsulting.tps.model.Corporate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Corporate_Roo_Jpa_Entity {
    
    declare @type: Corporate: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Corporate.id;
    
    @Version
    @Column(name = "version")
    private Integer Corporate.version;
    
    public Long Corporate.getId() {
        return this.id;
    }
    
    public void Corporate.setId(Long id) {
        this.id = id;
    }
    
    public Integer Corporate.getVersion() {
        return this.version;
    }
    
    public void Corporate.setVersion(Integer version) {
        this.version = version;
    }
    
}
