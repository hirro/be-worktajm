package com.arnellconsulting.tps.web;

import com.arnellconsulting.tps.model.Corporate;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.service.TpsService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/registration/")
@Slf4j
public class RegistrationController {
   @Autowired TpsService tpsService;
   
   @RequestMapping("/create")
   public String create(@RequestParam(value = "email", required = false) String email,
                        @RequestParam(value = "password", required = false) String password,
                        @RequestParam(value = "company", required = false) String company) {
      log.debug("create: email {}, password: {}, company: {}  ", email, password, company);

      Corporate corporate = new Corporate();
      corporate.setName(company);
      
      Person person = new Person();
      person.setUsername(email);
      person.setPassword(password);
      person.setEmployer(corporate);
      
      tpsService.update(person);
      return "/";
   }

   @RequestMapping("/")
   public String index() {
      log.debug("index");

      return null;
   }

   @RequestMapping("/register")
   public String register() {
      log.debug("register");

      return null;
   }

   //~--- get methods ---------------------------------------------------------

   @RequestMapping(value = "/checkEmail.do")
   @ResponseBody
   public boolean isEmailUnique(HttpServletResponse response, @RequestParam String email) {
      log.debug("isEmailUnique: {}", email);

      Person person = tpsService.findPersonByEmail(email);     
      return  (person == null);
   }
}
