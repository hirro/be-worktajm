package com.arnellconsulting.tps.webflow;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service("registrationService")
@Repository
public class JpaRegistrationService implements RegistrationService {

    @Override
    public Registration createRegistration() {
        return new Registration();
    }

    @Override
    public void persistBooking(Registration registration) {
        registration.persist();        
    }

    @Override
    public boolean verifyChallenge(Long id, String challenge) {
        return true;
    }

    @Override
    public void cancelRegistration(Long id) {
    }


}
