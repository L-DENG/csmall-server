package com.li.csmall.passport;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptPasswordEncoderTests {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    @Test
    public void testEncode(){
        for (int i = 0; i < 5; i++) {
            String rawPassword = "123456";
            String encondedPassword = encoder.encode(rawPassword);
            System.out.println(rawPassword);
            System.out.println(encondedPassword);
        }
    }

    @Test
    public void testMatch(){
        String rawPassword = "123456";
        String encodedPassword = "$2a$10$vJVLCFL7n5gKCsYpam7MueWnQF2xSVvryRDar0KdDydPeCtazwJMC";
        boolean matches = encoder.matches(rawPassword, encodedPassword);
        System.out.println("is match ===>> " + matches);
    }
}
