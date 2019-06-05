package com.vivvo.onboarding.controller;

import com.vivvo.onboarding.PhoneDto;
import com.vivvo.onboarding.UserDto;
import com.vivvo.onboarding.service.PhoneService;
import com.vivvo.onboarding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/phones")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PhoneDto create(@RequestBody PhoneDto dto) {
        return phoneService.create(dto);
    }

    @GetMapping
    public List<PhoneDto> getList(@PathVariable UUID userId) { return phoneService.getList(userId); }

    @GetMapping("/{phoneId}")
    public PhoneDto get(@PathVariable UUID phoneId) { return phoneService.get(phoneId); }

    @DeleteMapping("/{phoneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID phoneId) { phoneService.delete(phoneId); }

    @PutMapping("/{phoneId}")
    public PhoneDto update(@PathVariable UUID phoneId, @RequestBody PhoneDto dto) {
        dto.setPhoneId(phoneId);
        return phoneService.update(dto);
    }

    @PutMapping("/{phoneId}/makePrimary")
    public void makePrimary(@PathVariable UUID phoneId) { phoneService.makePrimary(phoneId); }

}
