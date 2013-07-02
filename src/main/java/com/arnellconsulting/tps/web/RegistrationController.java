package com.arnellconsulting.tps.web;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

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
  public String create(@RequestParam(value="email", required=false) String email,
                         @RequestParam(value="password", required=false) String password,
                         @RequestParam(value="company", required=false) String company) {
    log.debug("create: email {}, password: {}, company: {}  ", email, password, company);
    
    return null;
  }

  @RequestMapping("/register")
  public String register(@RequestParam(value="email", required=false) String email,
                         @RequestParam(value="password", required=false) String password,
                         @RequestParam(value="company", required=false) String company) {
    log.debug("register: email {}, password: {}, company: {}  ", email, password, company);
    
    return null;
  }
}
