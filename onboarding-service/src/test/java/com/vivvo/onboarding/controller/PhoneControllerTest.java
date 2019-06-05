package com.vivvo.onboarding.controller;

import com.vivvo.onboarding.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:teardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PhoneControllerTest {

    private UserClient userClient;

    @LocalServerPort
    private int port;

    @Before
    public void init() {
        userClient = new UserClient();
        userClient.setBaseUri("http://localhost:" + port);
    }

    @Test
    public void testCreate_shouldSucceed() {
        PhoneDto createdPhone = getValidPhoneDto();
        createdPhone = userClient.create(createdPhone, createdPhone.getUserId());
        assertNotNull(createdPhone.getPhoneId());
    }

    @Test
    public void testCreateAndGetPhone_shouldSucceed() {
        PhoneDto createdPhone = getValidPhoneDto();
        createdPhone = userClient.create(createdPhone, createdPhone.getUserId());
        List<PhoneDto> getPhones = userClient.getPhones(createdPhone.getUserId());
        assertTrue(getPhones.contains(createdPhone));
    }

    private UserDto getValidUserDto() {
        return new UserDto()
            .setFirstName("Ryan")
            .setLastName("Kopp")
            .setUsername("koppr");
    }

    private PhoneDto getValidPhoneDto() {
        UserDto createdUser = userClient.create(getValidUserDto());
        return new PhoneDto()
                .setUserId(createdUser.getUserId())
                .setPhoneNumber("1112223333")
                .setPrimary(true)
                .setVerified(true);
    }

}
