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


package com.arnellconsulting.worktajm.web.rest.v1;

import com.arnellconsulting.worktajm.web.rest.v1.BaseController;
import com.arnellconsulting.worktajm.domain.Customer;
import com.arnellconsulting.worktajm.domain.Person;
import com.arnellconsulting.worktajm.web.rest.v1.AccessDeniedException;
import com.arnellconsulting.worktajm.service.TpsService;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * JSON API for Customer.
 *
 * @author hirro
 */
@RestController
@RequestMapping("customer")
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ShortVariable" })
public class CustomerController extends BaseController {

   private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

   @Autowired
   private transient TpsService tpsService;

   @Transactional
   @RequestMapping(method = RequestMethod.GET)
   public List<Customer> list(final Principal principal) throws InterruptedException, AccessDeniedException {
      LOG.debug("list");
      final Person person = getAuthenticatedPerson(principal);
      return tpsService.getCustomersForPerson(person.getId());
   }

   @Transactional
   @RequestMapping(method = RequestMethod.POST)
   public Customer create(@RequestBody final Customer customer,
                         final Principal principal) throws AccessDeniedException {
      LOG.debug("create: {}", customer.toString());

      // Customer must belong to a person
      final Person person = getAuthenticatedPerson(principal);
      customer.setPerson(person);

      tpsService.saveCustomer(customer);

      return customer;
   }

   @Transactional
   @RequestMapping(
           value = "/{id}",
           method = RequestMethod.GET
   )
   public Customer read(@PathVariable final Long id) {
      LOG.debug("read id: {}", id);

      return tpsService.getCustomer(id);
   }

   @Transactional
   @RequestMapping(
           value = "/{id}",
           method = RequestMethod.PUT
   )
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void update(@PathVariable final Long id,
                      @RequestBody final Customer customer,
                      final Principal principal) throws AccessDeniedException {
      LOG.debug("update name: {}");

      // Customer must belong to a person
      final Person person = getAuthenticatedPerson(principal);
      customer.setPerson(person);

      tpsService.saveCustomer(customer);
   }

   @RequestMapping(
           value = "/{id}",
           method = RequestMethod.DELETE
   )
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delete(@PathVariable final Long id) {
      LOG.debug("delete id: {}", id);
      tpsService.deleteCustomer(id);
   }
}
