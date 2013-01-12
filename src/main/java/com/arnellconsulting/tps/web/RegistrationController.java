package com.arnellconsulting.tps.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.arnellconsulting.tps.model.Registration;

@RequestMapping("/registrations")
@Controller
@RooWebScaffold(path = "registrations", formBackingObject = Registration.class, delete = false, update = false)
public class RegistrationController {

    Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private transient MailSender mailTemplate;

    @Autowired
    private transient SimpleMailMessage simpleMailMessage;

    @Resource(name = "authenticationManager")
    private AuthenticationManager authenticationManager; // specific for Spring Security

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Registration registration, BindingResult bindingResult, Model uiModel,
            HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, registration);
            logger.error("Some error");
            return "registrations/create";
        }
        uiModel.asMap().clear();

        logger.debug("Storing registration");
        registration.persist();
        
        // Create user
        logger.debug("Creating principal");
        
        // Send registration mail
        sendMessage(registration.getEmail(), "TBD: Rehistrarion URL!");

        login(httpServletRequest, registration.getEmail(), registration.getPassword());

        return "redirect:/";
    }

    public void login(HttpServletRequest request, String userName, String password) {
        
        //
        logger.debug("Trying to set user");
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password);

        //
        logger.debug("Authenticate the user");
        Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        //
        logger.debug("Create a new session and add the security context.");
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }

    public void sendMessage(String mailTo, String message) {
        logger.debug("Sending registration confirmation mail");
        simpleMailMessage.setTo(mailTo);
        simpleMailMessage.setText(message);
        mailTemplate.send(simpleMailMessage);
    }

}
