
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.arnellconsulting.tps.web;

import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.model.TimeEntry;
import com.arnellconsulting.tps.service.TpsService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jiar
 */
@Controller
@RequestMapping("api")
@Slf4j
public class ApiController {
   @Autowired
   private transient TpsService tpsService;

   //~--- get methods ---------------------------------------------------------

   @Transactional
   @RequestMapping("project/list")
   public @ResponseBody
   List<Project> getAllProjects() {
      return tpsService.getProjets();
   }

   @Transactional
   @RequestMapping("timeentry/list")
   public @ResponseBody
   List<TimeEntry> getAllTimeEntries() {
      return tpsService.getTimeEntries();
   }

   @RequestMapping("project/{id}")
   @ResponseBody
   public Project getById(@PathVariable Long id) {
      return tpsService.getProjectById(id);
   }
}
