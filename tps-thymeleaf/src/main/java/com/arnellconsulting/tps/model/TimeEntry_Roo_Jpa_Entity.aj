// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.arnellconsulting.tps.model;

import com.arnellconsulting.tps.model.TimeEntry;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect TimeEntry_Roo_Jpa_Entity {
    
    declare @type: TimeEntry: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long TimeEntry.id;
    
    @Version
    @Column(name = "version")
    private Integer TimeEntry.version;
    
    public Long TimeEntry.getId() {
        return this.id;
    }
    
    public void TimeEntry.setId(Long id) {
        this.id = id;
    }
    
    public Integer TimeEntry.getVersion() {
        return this.version;
    }
    
    public void TimeEntry.setVersion(Integer version) {
        this.version = version;
    }
    
}
