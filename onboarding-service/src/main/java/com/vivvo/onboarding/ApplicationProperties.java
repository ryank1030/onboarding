package com.vivvo.onboarding;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="app")
@Data
public class ApplicationProperties {

    public TwilioProperties twilio = new TwilioProperties();

    @Data
    public static class TwilioProperties {
        private String authToken;
        private String accountSID;
    }
}
