package com.vivvo.onboarding;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class PhoneDto {

    private UUID phoneId;
    private UUID userId;
    private String phoneNumber;
    private boolean verified;
    private boolean primary;
    private String verificationLink;

}