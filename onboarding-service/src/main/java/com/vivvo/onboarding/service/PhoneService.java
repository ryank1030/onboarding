package com.vivvo.onboarding.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.vivvo.onboarding.ApplicationProperties;
import com.vivvo.onboarding.PhoneDto;
import com.vivvo.onboarding.Utility;
import com.vivvo.onboarding.entity.Phone;
import com.vivvo.onboarding.exception.NotFoundException;
import com.vivvo.onboarding.exception.ValidationException;
import com.vivvo.onboarding.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
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

    public PhoneDto create(UUID userId, PhoneDto dto) {
        dto.setVerified(false);
        dto.setPrimary(false);
        dto.setUserId(userId);

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
        List<PhoneDto> dtos =
                phoneRepository.findByUserId(userId)
                .stream()
                .map(phoneAssembler::assemble)
                .collect(Collectors.toList());
        dtos = sortListByPrimary(dtos);
        return dtos;
    }

    private List<PhoneDto> sortListByPrimary(List<PhoneDto> dtos) {
        if (dtos.size() == 1 ) {
            //we only have one phone
            dtos.get(0).setPrimary(true);
        } else {
            //we have more than one phone
            for (int i = 0; i < dtos.size(); i++) {
                //look for the primary phone
                if (dtos.get(i).isPrimary()) {
                    //the phone is primary so move it to position 0
                    PhoneDto temp = dtos.get(i);
                    dtos.set(i, dtos.get(0));
                    dtos.set(0, temp);
                }
            }
        }
        return dtos;
    }

    public void delete(UUID phoneId) {
        Optional<Phone> phone = phoneRepository.findById(phoneId);
        if (phone.isPresent()) {
            phoneRepository.delete(phone.get());
        } else {
            throw new NotFoundException(phoneId);
        }
    }

    public PhoneDto update(UUID userId, PhoneDto dto) {
        dto.setUserId(userId);
        Map<String, String> errors = phoneValidator.validateForUpdate(dto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        PhoneDto temp = get(userId, dto.getPhoneId());
        temp.setPhoneNumber(dto.getPhoneNumber());
        temp.setVerified(false);

        return Optional.of(temp)
                .map(phoneAssembler::disassemble)
                .map(phoneRepository::save)
                .map(phoneAssembler::assemble)
                .orElseThrow(IllegalArgumentException::new);
    }

    public PhoneDto get(UUID userId, UUID phoneId) {
        PhoneDto temp = phoneRepository.findById(phoneId)
                .map(phoneAssembler::assemble)
                .orElseThrow(() -> new NotFoundException(phoneId));

        if(!temp.getUserId().equals(userId)) {
            throw(new NotFoundException(phoneId));
        }
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

//String concatenation is a little hard to read also you're building the string in multiple steps which is a little
//hard to reason about
        Utility util = new Utility();
        String pin = util.generatePin(8);
        String link = String.format("http://localhost:4444/api/v1/users/%s/phones/%s/%s", userId, phoneId, pin);
        sendMessage(dto.getPhoneNumber(), link);
        dto.setVerificationLink(pin);
        Optional.of(dto)
                .map(phoneAssembler::disassemble)
                .map(phoneRepository::save)
                .orElseThrow(() -> new NotFoundException(phoneId));
    }

    public void verifyPhone(UUID userId, UUID phoneId, String verifyLink) {
        PhoneDto dto = get(userId, phoneId);
        Map<String, String> errors = phoneValidator.validateLink(dto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

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

        //removed unused variable
        Message.creator(new PhoneNumber(phone), // to
                        new PhoneNumber("+16475603984"), // from
                        link)
                        .create();
    }
}


