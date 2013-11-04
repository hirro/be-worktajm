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

package com.arnellconsulting.tps.web.service;

import com.arnellconsulting.tps.webflow.Registration;

public interface RegistrationService {
    Registration createRegistration();

    /**
     * Persist the registration to the database
     * @param registration the registration
     */
    void persist(Registration registration);

    /**
     * Sends the challenge to the provider email address.
     *
     * @param registration
     */
    void sendChallenge(Registration registration);

    /**
     * Verify challenge
     * @param id the registration id
     * @param challenge the challenge
     * @return true if challenge was matched.
     */
    boolean verifyChallenge(Registration registration);

    /**
     * Cancel an existing registration.
     * @param registration the registration id
     */
    void cancelRegistration(Registration registration);

    /**
     * Logins in the user after successful registration.
     * @param registration
     */
    void login(Registration registration);
    
    /**
     * Check if person already has been registered.
     * @param userName
     * @return 
     */
   boolean isUsernameUnique(final String userName);    
}
