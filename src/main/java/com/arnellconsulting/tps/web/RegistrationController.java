package com.arnellconsulting.tps.web;

import com.arnellconsulting.tps.model.Registration;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/registrations")
@Controller
@RooWebScaffold(path = "registrations", formBackingObject = Registration.class, delete=false, update=false)
public class RegistrationController {
}
