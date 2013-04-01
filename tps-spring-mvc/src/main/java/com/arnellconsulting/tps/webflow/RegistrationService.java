package com.arnellconsulting.tps.webflow;

public interface RegistrationService {
    public Registration createRegistration();

    /**
     * Persist the registration to the database
     * @param registration the registration
     */
    public void persist(Registration registration);

    /**
     * Sends the challenge to the provider email address.
     * 
     * @param registration
     */
    public void sendChallenge(Registration registration);
    
    /**
     * Verify challenge
     * @param id the registration id
     * @param challenge the challenge
     * @return true if challenge was matched.
     */
    public boolean verifyChallenge(Registration registration);

    /**
     * Cancel an existing registration.
     * @param id the registration id
     */
    public void cancelRegistration(Long id);
}
