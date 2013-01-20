package com.arnellconsulting.tps.ldap;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;

public class LdapTest {

    @Test
    public void test() {
        String ENCODED_PASSWORD_PASSWORD_1 = "{SSHA}HNRlADQVNyCnP0zSQu1VE1pMa0ORJ0QWBOKlmA==";
        String ENCODED_PASSWORD_PASSWORD_2 = "{SSHA}mquG5Nlz5ExoB0E582KRrLpIBaWlkEt+TGIA2w==";
        String ENCODED_PASSWORD_PASSWORD_3 = "{SSHA}y6Y4VNTjFjtmrpi5+e1+7qjb3e2Jm/Ec2Mix+Q==";
        String ENCODED_PASSWORD_PASSWORD_E = "{SSHA}mquG5Nlz5ExoB0E583KRrLpIBaWlkEt+TGIA2w==";
        String ENCODED_PASSWORD_PASSWORD_4 = "{SSHA}MQy2HKLZDPCP80/oIv0HuIH5ADfjbZHyuHXRrw==";
        String ENCODED_PASSWORD_PASSWORD_5 = "{SSHA}/kKbjY11VO13KrvJAoHqR13U8qIiab+BjtEMdg==";
        String password = "password";
        String salt = null;
        LdapShaPasswordEncoder l = new LdapShaPasswordEncoder();
        String encodedPassword = l.encodePassword(password, salt);
        assertTrue(l.isPasswordValid(encodedPassword, password, salt));
        assertTrue(l.isPasswordValid(ENCODED_PASSWORD_PASSWORD_1, password, salt));
        assertTrue(l.isPasswordValid(ENCODED_PASSWORD_PASSWORD_2, password, salt));
        assertTrue(l.isPasswordValid(ENCODED_PASSWORD_PASSWORD_3, password, salt));
        assertTrue(l.isPasswordValid(ENCODED_PASSWORD_PASSWORD_4, password, salt));
        assertTrue(l.isPasswordValid(ENCODED_PASSWORD_PASSWORD_5, password, salt));
        assertFalse(l.isPasswordValid(ENCODED_PASSWORD_PASSWORD_E, password, salt));
    }

}
