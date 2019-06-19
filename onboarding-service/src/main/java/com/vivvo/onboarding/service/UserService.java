package com.vivvo.onboarding.service;

import com.vivvo.onboarding.PhoneDto;
import com.vivvo.onboarding.UserDto;
import com.vivvo.onboarding.controller.PhoneController;
import com.vivvo.onboarding.entity.User;
import com.vivvo.onboarding.exception.NotFoundException;
import com.vivvo.onboarding.exception.ValidationException;
import com.vivvo.onboarding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAssembler userAssembler;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private PhoneController phoneController;

    @Autowired
    private PhoneValidator phoneValidator;

    public UserDto create(UserDto dto) {
        Map<String, String> errors = userValidator.validate(dto);
        errors.putAll(phoneValidator.validateList(dto.getPhones()));
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        UserDto user = Optional.of(dto)
                .map(userAssembler::disassemble)
                .map(userRepository::save)
                .map(userAssembler::assemble)
                .orElseThrow(IllegalArgumentException::new);

        List<PhoneDto> phones = new ArrayList<>();
        for (PhoneDto p_dto : dto.getPhones()) {
            p_dto.setUserId(user.getUserId());
            phones.add(phoneController.create(user.getUserId(), p_dto));
        }
        user.setPhones(phones);
        return user;
    }

    public UserDto update(UserDto dto) {
        Map<String, String> errors = userValidator.validateForUpdate(dto);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        return Optional.of(dto)
                .map(userAssembler::disassemble)
                .map(userRepository::save)
                .map(userAssembler::assemble)
                .orElseThrow(IllegalArgumentException::new);
    }

    public UserDto get(UUID userId) {
        UserDto dto = userRepository.findById(userId)
                .map(userAssembler::assemble)
                .orElseThrow(() -> new NotFoundException(userId));

        List<PhoneDto> dtos = phoneController.getList(userId);
        return dto;
    }

    public List<UserDto> get() {
        List<UserDto> temp = userRepository.findAll()
                .stream()
                .map(userAssembler::assemble)
                .collect(Collectors.toList());

        return temp;
    }

    public void delete(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<PhoneDto> dtos = phoneController.getList(userId);
            for (PhoneDto dto : dtos) {
                phoneController.delete(dto.getPhoneId());
            }
            userRepository.delete(user.get());
        } else {
            throw new NotFoundException(userId);
        }
    }

    public List<UserDto> findByFirstName(String firstName){
        return userRepository.findByFirstName(firstName)
                .stream()
                .map(userAssembler::assemble)
                .collect(Collectors.toList());
    }

    public List<UserDto> findByLastName(String lastName){
        return userRepository.findByLastName(lastName)
                .stream()
                .map(userAssembler::assemble)
                .collect(Collectors.toList());
    }

    public List<UserDto> sortByFirstName(){
        return get().stream()
                .sorted(Comparator.comparing(UserDto::getFirstName))
                .collect(Collectors.toList());
    }

    public List<UserDto> sortByLastName(){
        return get().stream()
                .sorted(Comparator.comparing(UserDto::getLastName))
                .collect(Collectors.toList());
    }

    public Page<UserDto> getPageSorted(){
        //getting a page request
        PageRequest firstPageWithTwoElements = PageRequest.of(0,2);
        Page<UserDto> allUsers = userRepository.findAll(firstPageWithTwoElements).map(userAssembler::assemble);
        System.out.println("Elements: " + allUsers.getTotalElements());
        System.out.println("Pages: " + allUsers.getTotalPages());
        allUsers.forEach(System.out::println);

        System.out.println("--break--");

        //getting sorted request
        List<UserDto> allUsersByFirstName = userRepository.findAll(Sort.by("firstName"))
                .stream()
                .map(userAssembler::assemble)
                .collect(Collectors.toList());
        allUsersByFirstName.forEach(System.out::println);

        System.out.println("--break--");

        //getting sorted page request
        PageRequest firstPageWithTwoElementsSorted = PageRequest.of(0,2, Sort.by("username"));
        List<UserDto> allUsersByUser = userRepository.findAll(firstPageWithTwoElementsSorted)
                .stream()
                .map(userAssembler::assemble)
                .collect(Collectors.toList());
        allUsersByUser.forEach(System.out::println);

        System.out.println("--break--");

        //this will be the final function
        PageRequest getPageSortedByFirstName = PageRequest.of(0, 2, Sort.by("firstName")); //make these variables in the future
        Page<UserDto> userPage = userRepository.findAll(getPageSortedByFirstName)
                .map(userAssembler::assemble);
        System.out.println("Elements: " + userPage.getTotalElements());
        System.out.println("Pages: " + userPage.getTotalPages());
        userPage.forEach(System.out::println);

        return userPage;
    }

    public Page<UserDto> getPage(int i) {
        return userRepository.findAll(PageRequest.of(i, 3, Sort.by("firstName")))
                .map(userAssembler::assemble);
    }
}
