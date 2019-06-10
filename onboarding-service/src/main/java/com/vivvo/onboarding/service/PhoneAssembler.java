package com.vivvo.onboarding.service;

import com.vivvo.onboarding.PhoneDto;
import com.vivvo.onboarding.entity.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PhoneAssembler {

    public PhoneDto assemble(Phone entity) {
        return new PhoneDto()
                .setPhoneId(entity.getPhoneId())
                .setUserId(entity.getUserId())
                .setPhoneNumber(entity.getPhoneNumber())
                .setVerified(entity.isVerified())
                .setPrimary(entity.isPrimary())
                .setVerificationLink(entity.getVerificationLink());
    }

    public Phone disassemble(PhoneDto dto) {
        return new Phone()
                .setPhoneId(dto.getPhoneId() == null? UUID.randomUUID() : dto.getPhoneId())
                .setUserId(dto.getUserId())
                .setPhoneNumber(dto.getPhoneNumber())
                .setVerified(dto.isVerified())
                .setPrimary(dto.isPrimary())
                .setVerificationLink(dto.getVerificationLink());
    }

}
