/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arnellconsulting.tps.repository;

import com.arnellconsulting.tps.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jiar
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
