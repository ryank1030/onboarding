package com.vivvo.onboarding;

import java.security.SecureRandom;

public class Utility {

    public String generatePin(int length) {
        SecureRandom random = new SecureRandom();
        String pin = "";
        for (int i = 0; i < length; i++) {
            pin += random.nextInt(10);
        }
        return pin;
    }

}
