//package com.arnellconsulting.tps.webflow;
//
//public interface RegistrationService {
//    Registration createRegistration();
//
//    /**
//     * Persist the registration to the database
//     * @param registration the registration
//     */
//    void persist(Registration registration);
//
//    /**
//     * Sends the challenge to the provider email address.
//     *
//     * @param registration
//     */
//    void sendChallenge(Registration registration);
//
//    /**
//     * Verify challenge
//     * @param id the registration id
//     * @param challenge the challenge
//     * @return true if challenge was matched.
//     */
//    boolean verifyChallenge(Registration registration);
//
//    /**
//     * Cancel an existing registration.
//     * @param id the registration id
//     */
//    void cancelRegistration(Long id);
//
//    /**
//     * Logins in the user after successful registration.
//     * @param registration
//     */
//    void login(Registration registration);
//    
//    /**
//     * Check if person already has been registered.
//     * @param userName
//     * @return 
//     */
//   boolean isUsernameUnique(final String userName);    
//}
