package com.arnellconsulting.tps.web;

import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.service.TpsService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequestMapping("/registration/")
@Slf4j
public class RegistrationController {
   @Autowired
   private transient TpsService tpsService;

   //~--- methods -------------------------------------------------------------

   @Transactional 
   @RequestMapping("/create")
   public String create(@RequestParam(value = "email", required = false) final String email,
                        @RequestParam(value = "password", required = false) final String password,
                        @RequestParam(value = "company", required = false) final String company) {
      log.debug("create: email {}, password: {}, company: {}  ", email, password, company);

      final Person person = new Person();

      person.setEmail(email);
      person.setPassword(password);
      person.setLastName("Last name");
      person.setFirstName("First name");
      
      tpsService.create(person);

      return "redirect:/";
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
   public boolean isEmailUnique(final HttpServletResponse response, @RequestParam final String email) {
      log.debug("isEmailUnique: {}", email);

      final Person person = tpsService.findPersonByEmail(email);

      return (person == null);
   }
}
