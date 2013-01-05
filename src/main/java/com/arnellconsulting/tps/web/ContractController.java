package com.arnellconsulting.tps.web;

import com.arnellconsulting.tps.model.Contract;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/contracts")
@Controller
@RooWebScaffold(path = "contracts", formBackingObject = Contract.class)
public class ContractController {
}
