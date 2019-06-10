package com.vivvo.onboarding.service;


import com.vivvo.onboarding.PhoneDto;
import com.vivvo.onboarding.UserDto;
import com.vivvo.onboarding.entity.Phone;
import com.vivvo.onboarding.repository.PhoneRepository;
import com.vivvo.onboarding.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PhoneValidatorTest {

    private PhoneValidator phoneValidator;

    private PhoneRepository phoneRepository;

    @Before
    public void init() {
        phoneRepository = getMockPhoneRepository();
        phoneValidator = new PhoneValidator(phoneRepository);
    }

    @Test
    public void testPhoneRequired() {
        PhoneDto dto = getValidPhoneDto()
                .setPhoneNumber(null);
        Map<String, String> errors = phoneValidator.validate(dto);

        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("phoneNumber"));
        assertEquals(phoneValidator.PHONE_REQUIRED, errors.get("phoneNumber"));
    }

    @Test
    public void testPhoneLT_16() {
        PhoneDto dto = getValidPhoneDto()
                .setPhoneNumber("12345678901234567");
        Map<String, String> errors = phoneValidator.validate(dto);

        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("phoneNumber"));
        assertEquals(phoneValidator.PHONE_LT_16, errors.get("phoneNumber"));
    }

    @Test
    public void testInvalidPhone() {
        PhoneDto dto = getValidPhoneDto()
                .setPhoneNumber("a");
        Map<String, String> errors = phoneValidator.validate(dto);

        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("phoneNumber"));
        assertEquals(phoneValidator.INVALID_PHONE, errors.get("phoneNumber"));
    }

    @Test
    public void testListPhoneRequired() {
        List<PhoneDto> dtos = new ArrayList<>();
        Map<String, String> errors = phoneValidator.validateList(dtos);

        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("phones"));
        assertEquals(phoneValidator.PHONE_REQUIRED, errors.get("phones"));
    }

    @Test
    public void testListPhoneDuplicate() {
        List<PhoneDto> dtos = new ArrayList<>();
        dtos.add(getValidPhoneDto());
        dtos.add(getValidPhoneDto());
        Map<String, String> errors = phoneValidator.validateList(dtos);

        assertEquals(1, errors.size());
        assertTrue(errors.containsKey("phoneNumber"));
        assertEquals(phoneValidator.PHONE_DUPLICATE, errors.get("phoneNumber"));
    }

    private PhoneDto getValidPhoneDto() {
        return new PhoneDto()
                .setPhoneId(UUID.randomUUID())
                .setPhoneNumber("1112223333")
                .setPrimary(true)
                .setVerified(true);
        }

    private PhoneRepository getMockPhoneRepository() {
        return mock(PhoneRepository.class);
    }
}
