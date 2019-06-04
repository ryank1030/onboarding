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

    public static String PRIMARY_PHONE_REQUIRED = "phone.primary.REQUIRED";
    public static String PRIMARY_PHONE_LT_16 = "phone.primary.LT_16";

    public Map<String, String> validate(PhoneDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.putAll(validatePrimaryPhone(dto));
        return errors;
    }

    private Map<String, String> validatePrimaryPhone(PhoneDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();
        if (StringUtils.isBlank(dto.getPhoneNumber())) {
            errors.put("primaryPhone", PRIMARY_PHONE_REQUIRED);
        } else if (dto.getPhoneNumber().length() > 16) {
            errors.put("primaryPhone", PRIMARY_PHONE_LT_16);
        }
        return errors;
    }

}
