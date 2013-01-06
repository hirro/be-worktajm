package com.arnellconsulting.tps.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping("/")
@Controller
public class RootController {
	
	Logger logger = LoggerFactory.getLogger(RootController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		String indexPage = "register";
		if (isAuthenticated()) {
			indexPage = "index";
		}
		logger.info("Using index page; " + indexPage);
		return indexPage;
	}

	private boolean isAuthenticated() {
		return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
	}
}
