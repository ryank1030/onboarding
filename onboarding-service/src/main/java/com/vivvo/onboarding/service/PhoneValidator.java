package com.vivvo.onboarding.service;

import com.vivvo.onboarding.PhoneDto;
import com.vivvo.onboarding.repository.PhoneRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class PhoneValidator {

    private final PhoneRepository phoneRepository;

    @Autowired
    public PhoneValidator(PhoneRepository phoneRepository) { this.phoneRepository = phoneRepository; }

    @Autowired
    PhoneService phoneService;

    public static String PHONE_REQUIRED = "phone.REQUIRED";
    public static String PHONE_LT_16 = "phone.primary.LT_16";
    public static String INVALID_PHONE = "phone.INVALID";
    public static String PHONE_DUPLICATE = "phone.DUPLICATE";
    public static String PHONE_ALREADY_EXISTS = "phone.already.EXISTS";
    public static String VERIFY_EMPTY = "phone.verify.EMPTY";

    public Map<String, String> validate(PhoneDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.putAll(validatePhoneNumber(dto));
        errors.putAll(validatePhoneNumberNotStored(dto));
        return errors;
    }

    public Map<String, String> validateForUpdate(PhoneDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();
        errors.putAll(validatePhoneNumber(dto));
        errors.putAll(validatePhoneNumberNotStored(dto));
        return errors;
    }

    public Map<String, String> validateList(List<PhoneDto> dtos) {
        Map<String, String> errors = new LinkedHashMap<>();
        if (dtos.isEmpty()) {
            errors.put("phones", PHONE_REQUIRED);
        }
        else {
            for (PhoneDto dto : dtos) {
                errors.putAll(validatePhoneNumber(dto));
            }
            errors.putAll(validatePhoneNumberDuplicates(dtos));
        }
        return errors;
    }

    private Map<String, String> validatePhoneNumber(PhoneDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();
        if (StringUtils.isBlank(dto.getPhoneNumber())) {
            errors.put("phoneNumber", PHONE_REQUIRED);
        } else if (dto.getPhoneNumber().length() > 16) {
            errors.put("phoneNumber", PHONE_LT_16);
        } else if (!StringUtils.isNumeric(dto.getPhoneNumber())) {
            errors.put("phoneNumber", INVALID_PHONE);
        }
        return errors;
    }

    //may break when one phone number is passed
    private Map<String, String> validatePhoneNumberDuplicates(List<PhoneDto> dtos) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (int i = 0; i < dtos.size(); i++) {
            for (int j = i + 1; j < dtos.size(); j++){
                if (dtos.get(i).getPhoneNumber().equals(dtos.get(j).getPhoneNumber())) {
                    errors.put("phoneNumber", PHONE_DUPLICATE);
                }
            }
        }
        return errors;
    }

    public Map<String, String> validateLink(PhoneDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();
        if (dto.getVerificationLink() == null) {
            errors.put("verificationLink", VERIFY_EMPTY);
        }
        return errors;
    }


    private Map<String, String> validatePhoneNumberNotStored(PhoneDto dto) {
        Map<String, String> errors = new LinkedHashMap<>();
        if(dto.getUserId() != null) {  //using this to break when running validator tests | passed value should always have userId set
            List<PhoneDto> dtos = phoneService.getList(dto.getUserId());
            if (!dtos.isEmpty()) {
                for (PhoneDto p_dto : dtos) {
                    if (p_dto.getPhoneNumber().equals(dto.getPhoneNumber())) {
                        errors.put("phoneNumber", PHONE_ALREADY_EXISTS);
                    }
                }
            }
        }
        return errors;
    }

}
