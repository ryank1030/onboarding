package com.vivvo.BFF.controller;

import com.vivvo.onboarding.PhoneDto;
import com.vivvo.onboarding.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/phones")
public class PhoneController {

    @Autowired
    private UserClient userClient;

    //working - sends bad request when sending same number
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PhoneDto create(@PathVariable UUID userId, @RequestBody PhoneDto dto) {
        return userClient.create(dto, userId);
    }

    //working
    @GetMapping
    public List<PhoneDto> getList(@PathVariable UUID userId) { return userClient.getList(userId); }

    //working
    @GetMapping("/{phoneId}")
    public PhoneDto get(@PathVariable UUID userId, @PathVariable UUID phoneId) { return userClient.get(userId, phoneId); }

    //working
    @DeleteMapping("/{phoneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID userId, @PathVariable UUID phoneId) { userClient.delete(phoneId, userId); }

    //working - sends bad request when phone number is in data base, or same number
    @PutMapping("/{phoneId}")
    public PhoneDto update(@PathVariable UUID userId, @PathVariable UUID phoneId, @RequestBody PhoneDto dto) {
        dto.setPhoneId(phoneId);
        return userClient.update(userId, phoneId, dto);
    }

    //working
    @PutMapping("/{phoneId}/makePrimary")
    public void makePrimary(@PathVariable UUID userId, @PathVariable UUID phoneId) { userClient.makePrimary(phoneId, userId); }

    //working
    @GetMapping("/{phoneId}/verifyPhone")
    public void verifyPhone(@PathVariable UUID userId, @PathVariable UUID phoneId) { userClient.verifyPhone(userId, phoneId); }

    //working
    @GetMapping("/{phoneId}/{verifyLink}")
    public void verify(@PathVariable UUID userId, @PathVariable UUID phoneId, @PathVariable String verifyLink) { userClient.verifyPhone(userId, phoneId, verifyLink); }

}

