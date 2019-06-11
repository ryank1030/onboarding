package com.vivvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;

@SpringBootApplication
public class BFF {

    public static void main(String[] args) {
        SpringApplication.run(BFF.class, args);
    }

    @Bean
    public UserClient userClient() {
        return new UserClient();
    }
}





