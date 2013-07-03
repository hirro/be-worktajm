package com.arnellconsulting.tps.web;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/registration/")
@Slf4j
public class RegistrationController {
   @RequestMapping("/")
   public String index() {
      log.debug("index");

      return null;
   }

   @RequestMapping("/create")
   public String create(@RequestParam(
      value = "email",
      required = false
   ) String email, @RequestParam(
      value = "password",
      required = false
   ) String password, @RequestParam(
      value = "company",
      required = false
   ) String company) {
      log.debug("create: email {}, password: {}, company: {}  ", email, password, company);

      return null;
   }

   @RequestMapping("/register")
   public String register(@RequestParam(
      value = "email",
      required = false
   ) String email, @RequestParam(
      value = "password",
      required = false
   ) String password, @RequestParam(
      value = "company",
      required = false
   ) String company) {
      log.debug("register: email {}, password: {}, company: {}  ", email, password, company);

      return null;
   }

   @RequestMapping(value = "/checkEmail.do")
   @ResponseBody
   public boolean isEmailUnique(HttpServletResponse response, @RequestParam String email) {
      log.debug("isEmailUnique: {}", email);

      return (email.length() % 2) == 0;
   }
}
