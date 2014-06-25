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


package com.arnellconsulting.worktajm.rest.v2;

import com.arnellconsulting.worktajm.domain.Customer;
import com.arnellconsulting.worktajm.domain.Person;
import com.arnellconsulting.worktajm.rest.v1.AccessDeniedException;
import com.arnellconsulting.worktajm.rest.v1.BaseController;
import com.arnellconsulting.worktajm.service.TpsService;
import java.security.Principal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;


/**
 * JSON API for Customer.
 *
 * @author hirro
 */
@RequestMapping("v2")
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ShortVariable" })
@Controller
public class CustomersController extends BaseController {

   private static final Logger LOG = LoggerFactory.getLogger(CustomersController.class);

   @Autowired
   private transient TpsService tpsService;

   @Transactional
   @RequestMapping("/customers")
   @ResponseBody
   public HttpEntity<Customers> customers(final Principal principal) 
           throws InterruptedException, 
                  AccessDeniedException {
      LOG.debug("v2.list");
      final Person person = getAuthenticatedPerson(principal);
      
      Customers result = new Customers();
      List<Customer> customers =  tpsService.getCustomersForPerson(person.getId());
      for (Customer c : customers) {
           result.add(linkTo(methodOn(CustomersController.class).customers(principal)).withSelfRel());
      }
      return new ResponseEntity<Customers>(result, HttpStatus.OK);
   }

}
