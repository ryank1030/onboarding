package com.vivvo.onboarding.service;

import com.vivvo.onboarding.PhoneDto;
import com.vivvo.onboarding.repository.PhoneRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class PhoneValidator {

    private final PhoneRepository phoneRepository;

    @Autowired
    public PhoneValidator(PhoneRepository phoneRepository) { this.phoneRepository = phoneRepository; }

    public static String PHONE_REQUIRED = "phone.REQUIRED";
    public static String PRIMARY_PHONE_LT_16 = "phone.primary.LT_16";
    public static String INVALID_PHONE = "phone.INVALID";

    public Map<String, String> validate(PhoneDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.putAll(validatePhoneNumber(dto));
        return errors;
    }

    private Map<String, String> validatePhoneNumber(PhoneDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();
        if (StringUtils.isBlank(dto.getPhoneNumber())) {
            errors.put("phoneNumber", PHONE_REQUIRED);
        } else if (dto.getPhoneNumber().length() > 16) {
            errors.put("phoneNumber", PRIMARY_PHONE_LT_16);
        } else if (!StringUtils.isNumeric(dto.getPhoneNumber())) {
            errors.put("phoneNumber", INVALID_PHONE);
        }
        return errors;
    }

}
