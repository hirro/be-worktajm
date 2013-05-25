package com.arnellconsulting.tps.webflow;

import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.arnellconsulting.tps.model.Person;

@RooJavaBean
@RooToString
@RooEquals
@RooSerializable
public class Registration{

    private static final Logger log = LoggerFactory.getLogger(Registration.class);

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

        verifyEmail(messages);

        verifyPassword(messages);
    }

    private void verifyEmail(MessageContext messages) {
        if (email == null || email.isEmpty()) {
            messages.addMessage(new MessageBuilder().error().source("email").
                    defaultText("Email must not be blank").build());
        } else {
            TypedQuery<Person> query = Person.findPeopleByUserName(email);
            boolean emailInUse = !query.getResultList().isEmpty();
            if (emailInUse) {
                messages.addMessage(new MessageBuilder().error().source("email").
                        defaultText("This email is already in use").build());
            }
        }
    }

    private void verifyPassword(MessageContext messages) {
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
