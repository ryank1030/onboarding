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

    //I don't use primitives in DTOs (other places are fine if you want to). The reason being they have a default value if they are not set (boolean false, int 0 etc).
    //A DTO represents and json object that could conceivably be send from a UI. If a ui doesn't include that primitive field, your java will default it when
    //it turns it into an object (deserialization) which could have weird behaviours (particularily if your boolean was notPrimary or something).

    private boolean verified;
    private boolean primary;
    private String verificationLink;

}