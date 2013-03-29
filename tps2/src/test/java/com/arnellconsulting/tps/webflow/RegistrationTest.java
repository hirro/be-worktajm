package com.arnellconsulting.tps.webflow;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;

import com.arnellconsulting.tps.model.Corporate;

@RooIntegrationTest(entity = Corporate.class)
public class RegistrationTest {

    private static final String CORPORATE_NAME = "example.com";
    private static final String EMAIL = "user@example.com";
    private static final String PASSWORD = "secret";
    
    @Test
    public void testRegistration() {
        Registration registration = new Registration();
        registration.setCorporateName(CORPORATE_NAME);
        registration.setEmail(EMAIL);
        registration.setPassword(PASSWORD);
        Corporate corporate = registration.persist();
        
        // Validate
        Corporate c = Corporate.findCorporate(corporate.getId());
        assertNotNull(c);
        assertEquals(CORPORATE_NAME, c.getName());
    }

}
