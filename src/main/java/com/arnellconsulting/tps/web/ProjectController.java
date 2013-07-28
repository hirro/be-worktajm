/*
 * Copyright 2013 Arnell Consulting AB.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package com.arnellconsulting.tps.web;

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
   transient TpsService tpsService;

   @Transactional
   @RequestMapping(method = RequestMethod.GET)
   @ResponseBody
   public List<Project> list() {
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
