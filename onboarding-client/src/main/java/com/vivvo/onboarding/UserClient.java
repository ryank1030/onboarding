package com.vivvo.onboarding;

import lombok.Setter;
import sun.jvm.hotspot.debugger.Page;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

public class UserClient {

    private Client client;
    @Setter
    private String baseUri = "http://localhost:4444";

    public UserClient() {
        this.client = ClientBuilder.newClient();
    }

    public UserDto create(UserDto dto) {
        return userTarget()
                .request()
                .post(Entity.json(dto), UserDto.class);
    }

    public List<UserDto> get() {
        return userTarget()
                .request()
                .get(new GenericType<List<UserDto>>(){});
    }

    public Object getPageSorted() {
        return userTarget()
                .path("/page")
                .request()
                .get(Object.class);
    }

    public UserDto update(UserDto dto) {
        return userTarget(dto.getUserId())
                .request()
                .put(Entity.json(dto), UserDto.class);
    }

    public void delete(UUID userId) {
        userTarget(userId)
                .request()
                .delete(Void.class);
    }

    public UserDto get(UUID userId) {
        return userTarget(userId)
                .request()
                .get(UserDto.class);
    }

    public List<UserDto> findByFirstName(String firstName) {
        return userTarget("firstName", firstName)
                .request()
                .get(new GenericType<List<UserDto>>(){});
    }

    public List<UserDto> findByLastName(String lastName) {
        return userTarget("lastName", lastName)
                .request()
                .get(new GenericType<List<UserDto>>(){});  //Don't understand this
    }

    public List<UserDto> sortByFirstName() {
        return userTarget()
                .path("/sortFirst")
                .request()
                .get(new GenericType<List<UserDto>>(){});
    }

    public List<UserDto> sortByLastName() {
        return userTarget()
                .path("/sortLast")
                .request()
                .get(new GenericType<List<UserDto>>(){});
    }

    private WebTarget userTarget() {
        return client.target(baseUri)
                .path("api")
                .path("v1")
                .path("users");
    }

    private WebTarget userTarget(UUID userId) {
        return userTarget()
                .path(userId.toString());
    }

    private WebTarget userTarget(String param, String name) {
        return userTarget()
                .queryParam(param, name);
    }



    //--- Phone Actions ---//

    public PhoneDto create(PhoneDto dto, UUID userId) {
        return phoneTarget(userId)
                .request()
                .post(Entity.json(dto), PhoneDto.class);
    }

    public List<PhoneDto> getPhones(UUID userId) {
        return phoneTarget(userId)
                .request()
                .get(new GenericType<List<PhoneDto>>(){});
    }

    public PhoneDto getPhone(UUID phoneId, UUID userId) {
        return phoneTarget(phoneId, userId)
                .request()
                .get(PhoneDto.class);
    }

    public void makePrimary(UUID phoneId, UUID userId) {
        phoneTarget(phoneId, userId)
                .path("makePrimary")
                .request()
                .put(Entity.json(userId), void.class);
    }

    public void delete(UUID phoneId, UUID userId) {
        phoneTarget(phoneId, userId)
                .request()
                .delete(Void.class);
    }

    public List<PhoneDto> getList(UUID userId) {
        return phoneTarget(userId)
                .request()
                .get(new GenericType<List<PhoneDto>>(){});
    }

    public PhoneDto update(UUID userId, UUID phoneId, PhoneDto dto) {
        return phoneTarget(phoneId, userId)
                .request()
                .put(Entity.json(dto), PhoneDto.class);
    }

    public PhoneDto get(UUID userId, UUID phoneId) {
        return phoneTarget(phoneId, userId)
                .request()
                .get(PhoneDto.class);
    }

    public void verifyPhone(UUID userId, UUID phoneId){
        phoneTarget(phoneId, userId)
                .path("verifyPhone")
                .request()
                .get();
    }

    public void verifyPhone(UUID userId, UUID phoneId, String verifyLink){
        phoneTarget(phoneId, userId)
                .path(verifyLink)
                .request()
                .get();
    }


    private WebTarget phoneTarget(UUID userId) {
        return userTarget()
                .path(userId.toString())
                .path("phones");
    }

    private WebTarget phoneTarget(UUID phoneId, UUID userId) {
        return phoneTarget(userId)
                .path(phoneId.toString());
    }

}
