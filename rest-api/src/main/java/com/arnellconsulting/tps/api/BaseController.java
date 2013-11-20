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
package com.arnellconsulting.tps.api;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.security.PersonUserDetails;
import java.security.Principal;
import org.springframework.security.core.Authentication;

/**
 *
 * @author jiar
 */
public class BaseController {

   //~--- get methods ---------------------------------------------------------
   public Person getAuthenticatedPerson(final Principal principal) {
      PersonUserDetails userDetails;
      userDetails = (PersonUserDetails) ((Authentication) principal).getPrincipal();
      return userDetails.getPerson();
   }
   
}
