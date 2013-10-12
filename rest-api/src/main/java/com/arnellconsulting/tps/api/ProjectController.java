/*
 * Copyright 2013 Jim Arnell
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.arnellconsulting.tps.api;

import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.service.TpsService;

import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * JSON API for Project.
 *
 * @author jiar
 */
@Controller
@RequestMapping("api/project")
@Slf4j
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ShortVariable" })
public class ProjectController {

   @Autowired
   private transient TpsService tpsService;

   @Transactional
   @RequestMapping(method = RequestMethod.GET)
   @ResponseBody
   public List<Project> list() throws InterruptedException {
      log.debug("list");
      return tpsService.getProjets();
   }

   @Transactional
   @RequestMapping(method = RequestMethod.POST)
   @ResponseBody
   public Project create(@RequestBody final Project project) {
      log.debug("create: {}", project.toString());
      tpsService.saveProject(project);

      return project;
   }

   @Transactional
   @RequestMapping(
      value = "/{id}",
      method = RequestMethod.GET
   )
   @ResponseBody
   public Project read(@PathVariable final long id) {
      log.debug("read id: {}", id);

      return tpsService.getProject(id);
   }
 
   @Transactional
   @RequestMapping(
      value = "/{id}",
      method = RequestMethod.PUT
   )
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void update(@PathVariable final long id, @RequestBody final Project project) {
      log.debug("update name: {}");
      tpsService.saveProject(project);
   }

   @RequestMapping(
      value = "/{id}",
      method = RequestMethod.DELETE
   )
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delete(@PathVariable final long id) {
      log.debug("delete id: {}", id);
      tpsService.deleteProject(id);
   }
}
