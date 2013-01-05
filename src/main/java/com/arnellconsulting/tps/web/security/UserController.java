package com.arnellconsulting.tps.web.security;

import com.arnellconsulting.tps.model.security.Principal;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/security/users")
@Controller
@RooWebScaffold(path = "security/users", formBackingObject = Principal.class)
public class UserController {
}
