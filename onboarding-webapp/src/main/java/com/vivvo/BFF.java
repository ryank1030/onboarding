package com.vivvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;

@SpringBootApplication
public class BFF {

    //TODO implement find for users
    // - by firstName
    // - by lastName
    // implement phone subresource (all crud operations) and tests
    // create action to make a phone number primary
    // use twillio to send an sms code and verify a phone number (more actions)


    @Autowired
    private MessageSource messageSource;

    public static void main(String[] args) {
        SpringApplication.run(BFF.class, args);
    }

    @Bean
    public UserClient userClient() {
        return new UserClient();
    }
}





