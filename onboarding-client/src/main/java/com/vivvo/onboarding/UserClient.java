package com.vivvo.onboarding;

import lombok.Setter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.UUID;

public class UserClient {

    private Client client;
    @Setter
    private String baseUri;

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

    private WebTarget userTarget() {
        return client.target("http://localhost:4444")
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
