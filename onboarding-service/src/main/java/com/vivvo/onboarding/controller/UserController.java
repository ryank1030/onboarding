package com.vivvo.onboarding.controller;

import com.vivvo.onboarding.UserDto;
import com.vivvo.onboarding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> get() { return userService.get(); }

    @GetMapping("/sortFirst")
    public List<UserDto> sortByFirstName() { return userService.sortByFirstName();}

    @GetMapping("/sortLast")
    public List<UserDto> sortByLastName() { return userService.sortByLastName();}

    @GetMapping(params = "createUsers")
    public void createMassUsers(@RequestParam int createUsers) { userService.createMassUsers(createUsers);}

    @GetMapping(params = {"page", "size"})
    public Page<UserDto> getPageSortedByFirst(@RequestParam int page, @RequestParam int size) { return userService.getPageSortedByFirst(page, size);}

    @GetMapping(params = {"page", "size", "search"})
    public Page<UserDto> getPageSearch(@RequestParam int page, @RequestParam int size, @RequestParam String search) { return userService.getPageSearch(page, size, search);}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto dto) {
        return userService.create(dto);
    }

    @GetMapping("/{userId}")
    public UserDto get(@PathVariable UUID userId) {
        return userService.get(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID userId) {
        userService.delete(userId);
    }

    @PutMapping("/{userId}")
    public UserDto update(@PathVariable UUID userId, @RequestBody UserDto dto) {
        dto.setUserId(userId);
        return userService.update(dto);
    }

    @GetMapping(params = "firstName")
    public List<UserDto> findByFirstName(@RequestParam String firstName) {
        return userService.findByFirstName(firstName);
    }

    @GetMapping(params = "lastName")
    public List<UserDto> findByLastName(@RequestParam String lastName) {
        return userService.findByLastName(lastName);
    }
}
