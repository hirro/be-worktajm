/*
 * Copyright 2013 Arnell Consulting AB.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arnellconsulting.tps.web.service;

import com.arnellconsulting.tps.webflow.Registration;
import javax.persistence.EntityManager;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author jiar
 */
public class JpaRegistrationServiceTest {
   
   JpaRegistrationService service;
   private EntityManager emMock;
   private MailSender mailSenderMock;
   
   public JpaRegistrationServiceTest() {
      service = new JpaRegistrationService();
   }
   
   @Before
   public void setUp() {
      mailSenderMock = Mockito.mock(MailSender.class);
      service.setMailTemplate(mailSenderMock);
      emMock = Mockito.mock(EntityManager.class);
      service.setEntityManager(emMock);
   }   

   @Test
   public void testCancelRegistration() {
      Registration registration = service.createRegistration();
      service.cancelRegistration(registration);
      
      // Verify
   }

   @Test
   public void testCreateRegistration() {
      Registration registration = service.createRegistration();      
   }

   @Test
   public void testLogin() {
      Registration registration = service.createRegistration();
      service.login(registration);
   }

   @Test
   public void testPersist() {
      Registration registration = service.createRegistration();
      service.login(registration);
      service.persist(registration);
   }

   @Test
   public void testSendChallenge() {
      Registration registration = service.createRegistration();
      service.sendChallenge(registration);
      verify(mailSenderMock, times(1)).send(any(SimpleMailMessage.class));
   }

   @Test
   public void testVerifyChallenge() {
      Registration registration = service.createRegistration();
      registration.setReceivedChallenge("A");
      registration.setSentChallenge("A");
      assertTrue(service.verifyChallenge(registration));
   }

   @Test
   public void testSetEntityManager() {
   }
   
}
