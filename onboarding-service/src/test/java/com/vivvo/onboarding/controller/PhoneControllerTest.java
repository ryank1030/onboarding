package com.vivvo.onboarding.controller;

import com.vivvo.onboarding.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
        UserDto createdUser = getValidUserDto();
        PhoneDto createdPhone = createdUser.getPhones().get(0);
        createdPhone = userClient.create(createdPhone, createdPhone.getUserId());
        assertNotNull(createdPhone.getPhoneId());
    }

    @Test
    public void testCreateAndGetPhone_shouldSucceed() {
        UserDto createdUser = getValidUserDto();
        PhoneDto createdPhone = createdUser.getPhones().get(0);
        createdPhone = userClient.create(createdPhone, createdPhone.getUserId());
        List<PhoneDto> getPhones = userClient.getPhones(createdPhone.getUserId());
        assertTrue(getPhones.contains(createdPhone));
    }

    /*
    @Test
    public void testCreateAndDeletePhone_shouldSucceed() {
        PhoneDto createdPhone = getValidPhoneDto();
        createdPhone = userClient.create(createdPhone, createdPhone.getUserId());
        assertNull(createdPhone.delete(createdPhone.getPhoneId(), createdPhone.getUserId()));
    }
     */

    private UserDto getValidUserDto() {
        return new UserDto()
                .setFirstName("Ryan")
                .setLastName("Kopp")
                .setUsername("koppr")
                .setUserId(UUID.randomUUID())
                .setPhones(Collections.singletonList(getValidPhoneDto(UUID.randomUUID())));
    }

    private PhoneDto getValidPhoneDto(UUID userId) {
        return new PhoneDto()
                .setUserId(userId)
                .setPhoneId(UUID.randomUUID())
                .setPhoneNumber("1112223333")
                .setVerified(true)
                .setPrimary(true);
    }

}
