
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.arnellconsulting.tps.web;

import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.service.TpsService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author jiar
 */
@Controller
@RequestMapping("api")
@Slf4j
public class ProjectController {
   @Autowired
   private transient TpsService tpsService;

   //~--- methods -------------------------------------------------------------

   @Transactional
   @RequestMapping("project/list")
   public @ResponseBody
   List<Project> index() {
      return tpsService.getProjets();
   }

   //~--- get methods ---------------------------------------------------------

   @RequestMapping("project/{id}")
   @ResponseBody
   public Project getById(@PathVariable Long id) {
      return tpsService.getProjectById(id);
   }
}
