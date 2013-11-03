/*
 * Copyright 2013 Jim Arnell
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.arnellconsulting.tps.webflow;

import com.arnellconsulting.tps.web.service.RegistrationService;
import lombok.Data;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;

import javax.validation.constraints.NotNull;

/**
 * Webflow bean.
 * @author jiar
 */
@Data
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@Slf4j
public class Registration {
   @NotNull
   private final String corporateName;
   @NotNull
   private String email;
   private String password;
   private String passwordAgain;
   private String sentChallenge;
   private String receivedChallenge;
   @Autowired
   private RegistrationService registrationService;

   public Registration() {
      corporateName = "";
   }

   //~--- methods -------------------------------------------------------------

   public void validateUserInformationState(final ValidationContext context) {
      final MessageContext messages = context.getMessageContext();

      verifyEmail(messages);
      verifyPassword(messages);
   }

   public void validateVerifyAccountState(final ValidationContext context) {
      final MessageContext messages = context.getMessageContext();

      if ((sentChallenge != null) && (receivedChallenge != null)) {
         if (sentChallenge.compareTo(receivedChallenge) != 0) {
            messages.addMessage(
            new MessageBuilder().error().source("receivedChallenge").defaultText("Invalid challenge").build());
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

   private void verifyEmail(final MessageContext messages) {
      if ((email == null) || email.isEmpty()) {
         messages.addMessage(
         new MessageBuilder().error().source("email").defaultText("Email must not be blank").build());
      } else {
         if (registrationService.isUsernameUnique(email)) {
            log.debug("Person {} not found in database, creating it...", email);
         } else {
            messages.addMessage(
            new MessageBuilder().error().source("email").defaultText("This email is already in use").build());
         }
      }
   }

   private void verifyPassword(final MessageContext messages) {
      if ((password != null) && (passwordAgain != null)) {
         if (password.compareTo(passwordAgain) != 0) {
            messages.addMessage(
            new MessageBuilder().error().source("password").defaultText("Passwords must match").build());
            messages.addMessage(
            new MessageBuilder().error().source("passwordAgain").defaultText("Passwords must match").build());
            log.debug("Password missmatch");
         } else {
            log.debug("User information is valid");
         }
      } else {
         if (password == null) {
            log.debug("Password is blank");
            messages.addMessage(
            new MessageBuilder().error().source("password").defaultText("Passwords must not be blank").build());
         }

         if (passwordAgain == null) {
            log.debug("PasswordAgain is blank");
            messages.addMessage(
            new MessageBuilder().error().source("passwordAgain").defaultText("Passwords must not be blank").build());
         }
      }
   }
}
