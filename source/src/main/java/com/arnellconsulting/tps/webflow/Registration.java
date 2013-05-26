package com.arnellconsulting.tps.webflow;

import com.arnellconsulting.tps.model.Person;

import lombok.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;

import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Webflow bean.
 * @author jiar
 */
@Data
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class Registration {
   private static final Logger LOG = LoggerFactory.getLogger(Registration.class);

   @NotNull
   private String corporateName;
   @NotNull
   private String email;
   private String password;
   private String passwordAgain;
   private String sentChallenge;
   private String receivedChallenge;
   @Autowired
   private RegistrationService registrationService;

   public Registration() {
      this.corporateName = "";
   }

   //~--- methods -------------------------------------------------------------

   public void validateUserInformationState(final ValidationContext context) {
      MessageContext messages = context.getMessageContext();

      verifyEmail(messages);
      verifyPassword(messages);
   }

   public void validateVerifyAccountState(ValidationContext context) {
      MessageContext messages = context.getMessageContext();

      if ((sentChallenge != null) && (receivedChallenge != null)) {
         if (sentChallenge.compareTo(receivedChallenge) != 0) {
            messages.addMessage(
            new MessageBuilder().error().source("receivedChallenge").defaultText("Invalid challenge").build());
            LOG.debug("receivedChallenge does not match sentChallenge");
         } else {
            LOG.debug("Account verified");
         }
      } else if (sentChallenge == null) {
         LOG.debug("Sent challenge is blank");
      } else if (receivedChallenge == null) {
         LOG.debug("Received challenge is blank");
      }
   }

   private void verifyEmail(MessageContext messages) {
      if ((email == null) || email.isEmpty()) {
         messages.addMessage(
         new MessageBuilder().error().source("email").defaultText("Email must not be blank").build());
      } else {
         if (registrationService.isUsernameUnique(email)) {
            LOG.debug("Person {} not found in database, creating it...", email);
         } else {
            messages.addMessage(
            new MessageBuilder().error().source("email").defaultText("This email is already in use").build());
         }
      }
   }

   private void verifyPassword(MessageContext messages) {
      if ((password != null) && (passwordAgain != null)) {
         if (password.compareTo(passwordAgain) != 0) {
            messages.addMessage(
            new MessageBuilder().error().source("password").defaultText("Passwords must match").build());
            messages.addMessage(
            new MessageBuilder().error().source("passwordAgain").defaultText("Passwords must match").build());
            LOG.debug("Password missmatch");
         } else {
            LOG.debug("User information is valid");
         }
      } else {
         if (password == null) {
            LOG.debug("Password is blank");
            messages.addMessage(
            new MessageBuilder().error().source("password").defaultText("Passwords must not be blank").build());
         }

         if (passwordAgain == null) {
            LOG.debug("PasswordAgain is blank");
            messages.addMessage(
            new MessageBuilder().error().source("passwordAgain").defaultText("Passwords must not be blank").build());
         }
      }
   }
}
