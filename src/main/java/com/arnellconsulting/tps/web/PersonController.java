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

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.service.TpsService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * JSON interface.
 *
 * @author jiar
 */
@Controller
@RequestMapping("api/person")
@Slf4j
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ShortVariable" })
public class PersonController {
   @Autowired
   private transient TpsService tpsService;

   @Transactional
   @RequestMapping(
      method = RequestMethod.GET
   )
   @ResponseBody
   public List<Person> findAll() {
      log.debug("findAll");

      return tpsService.getPersons();
   }

   @RequestMapping(
      method = RequestMethod.POST
   )
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void create(@RequestBody final Person person) {
      log.debug("create name: {}", person.getUsername());
      tpsService.updatePerson(person);
   }

   @RequestMapping(
      method = RequestMethod.GET,
      consumes = "application/json"
   )
   @ResponseBody
   public final Person read(@RequestParam final Long id) {
      log.debug("read id: {}", id);

      return tpsService.getPersonById(id);
   }

   @RequestMapping(
      method = RequestMethod.PUT
   )
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @ResponseBody
   public final void update(@RequestBody final Person person) {
      log.debug("update id: {}", person.getId());
      tpsService.updatePerson(person);
   }

   @RequestMapping(
      method = RequestMethod.DELETE
   )
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public final void delete(@RequestParam final Long id) {
      log.debug("delete id: {}", id);
      tpsService.deletePerson(id);
   }
}
