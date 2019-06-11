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

    /*
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PhoneDto create(@PathVariable UUID userId, @RequestBody PhoneDto dto) {
        return phoneService.create(userId, dto);
    }

    @GetMapping
    public List<PhoneDto> getList(@PathVariable UUID userId) { return phoneService.getList(userId); }

    @GetMapping("/{phoneId}")
    public PhoneDto get(@PathVariable UUID userId, @PathVariable UUID phoneId) { return phoneService.get(userId, phoneId); }

    @DeleteMapping("/{phoneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID phoneId) { phoneService.delete(phoneId); }

    @PutMapping("/{phoneId}")
    public PhoneDto update(@PathVariable UUID userId, @PathVariable UUID phoneId, @RequestBody PhoneDto dto) {
        dto.setPhoneId(phoneId);
        return phoneService.update(userId, dto);
    }

    @PutMapping("/{phoneId}/makePrimary")
    public void makePrimary(@PathVariable UUID userId, @PathVariable UUID phoneId) { phoneService.makePrimary(userId, phoneId); }

    @GetMapping("/{phoneId}/verifyPhone")
    public void verifyPhone(@PathVariable UUID userId, @PathVariable UUID phoneId) { phoneService.verifyPhone(userId, phoneId); }

    @GetMapping("/{phoneId}/{verifyLink}")
    public void verify(@PathVariable UUID userId, @PathVariable UUID phoneId, @PathVariable String verifyLink) { phoneService.verifyPhone(userId, phoneId, verifyLink); }
    */
}

