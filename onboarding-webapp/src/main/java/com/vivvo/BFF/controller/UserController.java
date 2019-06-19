package com.vivvo.BFF.controller;

import com.vivvo.onboarding.UserClient;
import com.vivvo.onboarding.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sun.jvm.hotspot.debugger.Page;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserClient userClient;

    //working
    @GetMapping
    public List<UserDto> get() { return userClient.get(); }

    @GetMapping("/sortFirst")
    public List<UserDto> sortByFirstName() { return userClient.sortByFirstName();}

    @GetMapping("/sortLast")
    public List<UserDto> sortByLastName() { return userClient.sortByLastName();}

    @GetMapping("/page")
    public Object getPageSorted() { return userClient.getPageSorted(); }

    @GetMapping(params = "page")
    public Object getPage(@RequestParam int page) { return userClient.getPage(page);}

    //working
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto dto) {
        return userClient.create(dto);
    }

    //working
    @GetMapping("/{userId}")
    public UserDto get(@PathVariable UUID userId) {
        return userClient.get(userId);
    }

    //working
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID userId) {
        userClient.delete(userId);
    }

    //working
    @PutMapping("/{userId}")
    public UserDto update(@PathVariable UUID userId, @RequestBody UserDto dto) {
        dto.setUserId(userId);
        return userClient.update(dto);
    }

    //working
    @GetMapping(params = "firstName")
    public List<UserDto> findByFirstName(@RequestParam String firstName) {
        return userClient.findByFirstName(firstName);
    }

    //working
    @GetMapping(params = "lastName")
    public List<UserDto> findByLastName(@RequestParam String lastName) {
        return userClient.findByLastName(lastName);
    }

}
