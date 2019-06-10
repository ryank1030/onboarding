package com.vivvo.onboarding.service;

// Install the Java helper library from twilio.com/docs/libraries/java
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsSender {
    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID =
            "";
    public static final String AUTH_TOKEN =
            "";

    public void sendMessage(String phone, String link) {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber(phone), // to
                        new PhoneNumber("+16475603984"), // from
                        link)
                .create();

        System.out.println("SMS verification: " + message.getSid() + " link: " + link);

    }
}

