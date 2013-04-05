package com.arnellconsulting.tps.webflow;

import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEquals
@RooSerializable
public class Registration{

    private static final Logger log = Logger.getLogger(Registration.class);

    @NotNull
    private String corporateName = "";

    @NotNull
    private String email;

    private String password;
    
    private String passwordAgain;
    
    private String sentChallenge;
    
    private String receivedChallenge;
    
    public void validateUserInformationState(ValidationContext context) {
        MessageContext messages = context.getMessageContext();
        
        if (email == null || email.isEmpty()) {
            messages.addMessage(new MessageBuilder().error().source("email").
                    defaultText("Email must not be blank").build());
        }
        
        if (password != null && passwordAgain != null) {
            if (password.compareTo(passwordAgain) != 0) {
                messages.addMessage(new MessageBuilder().error().source("password").
                    defaultText("Passwords must match").build());
                messages.addMessage(new MessageBuilder().error().source("passwordAgain").
                    defaultText("Passwords must match").build());
                log.debug("Password missmatch");
            } else {
                log.debug("User information is valid");
            }
        } else {
            if (password == null) {
                log.debug("Password is blank");
                messages.addMessage(new MessageBuilder().error().source("password").
                        defaultText("Passwords must not be blank").build());
            }
            if (passwordAgain == null) {
                log.debug("PasswordAgain is blank");
                messages.addMessage(new MessageBuilder().error().source("passwordAgain").
                        defaultText("Passwords must not be blank").build());
            }            
        }
    }
    
    public void validateVerifyAccountState(ValidationContext context) {
        MessageContext messages = context.getMessageContext();
        if (sentChallenge != null && receivedChallenge != null) {
            if (sentChallenge.compareTo(receivedChallenge) != 0) {
                messages.addMessage(new MessageBuilder().error().source("receivedChallenge").
                    defaultText("Invalid challenge").build());
                log.debug("receivedChallenge does not match sentChallenge");
            } else {
                log.debug("Account verified");
            }
        } else if (sentChallenge == null) { 
            log.debug("Sent challenge is blank");             
        } else if (receivedChallenge == null) { 
            log.debug("Received challenge is blank");             
        }         
    }
}
