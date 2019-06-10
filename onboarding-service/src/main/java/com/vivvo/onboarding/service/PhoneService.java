package com.vivvo.onboarding.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.vivvo.onboarding.ApplicationProperties;
import com.vivvo.onboarding.PhoneDto;
import com.vivvo.onboarding.entity.Phone;
import com.vivvo.onboarding.exception.NotFoundException;
import com.vivvo.onboarding.exception.ValidationException;
import com.vivvo.onboarding.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private PhoneValidator phoneValidator;

    @Autowired
    private PhoneAssembler phoneAssembler;

    @Autowired
    private ApplicationProperties applicationProperties;

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

    public void verifyPhone(UUID userId, UUID phoneId) {
        PhoneDto dto = get(userId, phoneId);
        SecureRandom rand = new SecureRandom();
        String link = "http://localhost:4444/api/v1/users/"
        + userId
        + "/phones/"
        + phoneId
        + "/";

        for (int i = 0; i < 8; i++) {
            link += rand.nextInt(10);
        }

        sendMessage(dto.getPhoneNumber(), link);
        dto.setVerificationLink(link.substring(link.length() - 8));
        Optional.of(dto)
                .map(phoneAssembler::disassemble)
                .map(phoneRepository::save)
                .orElseThrow(() -> new NotFoundException(phoneId));
    }

    public void verifyPhone(UUID userId, UUID phoneId, String verifyLink) {
        PhoneDto dto = get(userId, phoneId);
        if (dto.getVerificationLink().equals(verifyLink)) {
            dto.setVerified(true);
            Optional.of(dto)
                    .map(phoneAssembler::disassemble)
                    .map(phoneRepository::save)
                    .orElseThrow(BadRequestException::new);
        } else {
            throw new BadRequestException();
        }
    }

    public void sendMessage(String phone, String link) {

        Twilio.init(applicationProperties.getTwilio().getAccountSID(), applicationProperties.getTwilio().getAuthToken());

        Message message = Message
                .creator(new PhoneNumber(phone), // to
                        new PhoneNumber("+16475603984"), // from
                        link)
                .create();

        System.out.println("SMS verification: " + message.getSid() + " link: " + link);

    }
}


