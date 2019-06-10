package com.vivvo.onboarding.service;

import com.vivvo.onboarding.PhoneDto;
import com.vivvo.onboarding.UserDto;
import com.vivvo.onboarding.entity.Phone;
import com.vivvo.onboarding.entity.User;
import com.vivvo.onboarding.exception.NotFoundException;
import com.vivvo.onboarding.exception.ValidationException;
import com.vivvo.onboarding.repository.PhoneRepository;
import com.vivvo.onboarding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private PhoneValidator phoneValidator;

    @Autowired
    private PhoneAssembler phoneAssembler;

    public PhoneDto create(PhoneDto dto) {
        Map<String, String> errors = phoneValidator.validate(dto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        return Optional.of(dto)
                .map(phoneAssembler::disassemble)
                .map(phoneRepository::save)
                .map(phoneAssembler::assemble)
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<PhoneDto> getList(UUID userId) {
        return phoneRepository.findByUserId(userId)
                .stream()
                .map(phoneAssembler::assemble)
                .collect(Collectors.toList());
    }

    public void delete(UUID phoneId) {
        Optional<Phone> phone = phoneRepository.findById(phoneId);
        if (phone.isPresent()) {
            phoneRepository.delete(phone.get());
        } else {
            throw new NotFoundException(phoneId);
        }
    }

    public PhoneDto update(PhoneDto dto) {
        /*
        Map<String, String> errors = phoneValidator.validateForUpdate(dto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
         */

        return Optional.of(dto)
                .map(phoneAssembler::disassemble)
                .map(phoneRepository::save)
                .map(phoneAssembler::assemble)
                .orElseThrow(IllegalArgumentException::new);
    }

    public PhoneDto get(UUID userId, UUID phoneId) {
        PhoneDto temp = phoneRepository.findById(phoneId)
                .map(phoneAssembler::assemble)
                .orElseThrow(() -> new NotFoundException(phoneId));
        /*
        if(temp.getUserId() != userId) {
            throw(new NotFoundException(phoneId));
        }

         */
        return temp;
    }

    public void makePrimary(UUID userId, UUID phoneId) {
        List<PhoneDto> dtos = getList(userId);
        for(PhoneDto dto : dtos) {
            if (dto.getPhoneId().equals(phoneId)) {
                Optional.of(dto.setPrimary(true))
                        .map(phoneAssembler::disassemble)
                        .map(phoneRepository::save)
                        .orElseThrow(IllegalArgumentException::new);
            } else {
                Optional.of(dto.setPrimary(false))
                        .map(phoneAssembler::disassemble)
                        .map(phoneRepository::save)
                        .orElseThrow(IllegalArgumentException::new);
            }
        }
    }
}


