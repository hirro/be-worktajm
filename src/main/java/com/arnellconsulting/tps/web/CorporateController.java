package com.arnellconsulting.tps.web;

import com.arnellconsulting.tps.model.Corporate;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/corporates")
@Controller
@RooWebScaffold(path = "corporates", formBackingObject = Corporate.class)
public class CorporateController {
}
