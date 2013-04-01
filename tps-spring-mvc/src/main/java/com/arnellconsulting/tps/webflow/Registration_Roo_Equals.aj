// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.arnellconsulting.tps.webflow;

import com.arnellconsulting.tps.webflow.Registration;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

privileged aspect Registration_Roo_Equals {
    
    public boolean Registration.equals(Object obj) {
        if (!(obj instanceof Registration)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Registration rhs = (Registration) obj;
        return new EqualsBuilder().append(corporateName, rhs.corporateName).append(email, rhs.email).append(password, rhs.password).append(receivedChallenge, rhs.receivedChallenge).append(sentChallenge, rhs.sentChallenge).isEquals();
    }
    
    public int Registration.hashCode() {
        return new HashCodeBuilder().append(corporateName).append(email).append(password).append(receivedChallenge).append(sentChallenge).toHashCode();
    }
    
}
