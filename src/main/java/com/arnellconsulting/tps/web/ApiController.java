
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

import java.util.List;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * test
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
   @RequestMapping(value = "projects", method = RequestMethod.GET)
   @ResponseBody
   public List<Project> getAllProjects() {
      log.debug("getAllProjects");
      return tpsService.getProjets();
   }

   @Transactional
   @RequestMapping("timeentry/list")
   @ResponseBody 
   public List<TimeEntry> getAllTimeEntries() {
      log.debug("getAllTimeEntries");
      return tpsService.getTimeEntries();
   }
   
   @RequestMapping(value = "project", method = RequestMethod.POST)
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void createProject(@RequestBody Project project) {
      log.debug("createProject {}", project.getName());
      tpsService.updateProject(project);
   }
   
   @RequestMapping(value = "project", method = RequestMethod.GET)
   @ResponseBody
   public Project readProject(@RequestParam Long id) {
      log.debug("readProject {}", id);
      return tpsService.getProjectById(id);
   }
    
   @RequestMapping(value = "project", method = RequestMethod.PUT)
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @ResponseBody
   public void updateProject(@RequestBody Project project) {
      log.debug("updateProject {}", project.getId());
      tpsService.updateProject(project);
   }
   
   @RequestMapping(value = "project", method = RequestMethod.DELETE)
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void deleteProject(@RequestParam Long id) {
      log.debug("deleteProject {}", id);
      tpsService.deleteProject(id);
   }

}
