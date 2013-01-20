package com.arnellconsulting.tps.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.CorporateRegistration;

@RequestMapping("/registrations")
@Controller
@RooWebScaffold(path = "registrations", formBackingObject = CorporateRegistration.class, delete = false, update = false)
public class CorporateRegistrationController {

    Logger logger = LoggerFactory.getLogger(CorporateRegistrationController.class);

    @Autowired
    private transient MailSender mailTemplate;

    @Autowired
    private transient SimpleMailMessage simpleMailMessage;

    @Resource(name = "authenticationManager")
    private AuthenticationManager authenticationManager; // specific for Spring Security

    @Autowired
    private LdapTemplate ldapTemplate;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid CorporateRegistration registration, BindingResult bindingResult, Model uiModel,
            HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, registration);
            logger.error("binding result error");
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
        create(userName, password);

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

    public void create(String userName, String password) {
        String baseDn = "ou=users,dc=arnellconsulting,dc=com";
        DistinguishedName distinguisedName = new DistinguishedName(baseDn);
        distinguisedName.add("cn", userName);
 
        Attributes userAttributes = new BasicAttributes();
        userAttributes.put("sn", userName);
        userAttributes.put("givenName", userName);
        //userAttributes.put("telephoneNumber", p);
        userAttributes.put("userPassword", password);
        LdapShaPasswordEncoder l = new LdapShaPasswordEncoder();
        String encodedPassword = l.encodePassword(password, null);
        logger.debug(String.format("Password: %s, Encrypted password: %s", password, encodedPassword));
 
        BasicAttribute classAttribute = new BasicAttribute("objectclass");
        classAttribute.add("top");
        classAttribute.add("organizationalPerson");
        classAttribute.add("inetOrgPerson");
        classAttribute.add("person");
        userAttributes.put(classAttribute);
 
        ldapTemplate.bind(distinguisedName, null, userAttributes);
    }
}
