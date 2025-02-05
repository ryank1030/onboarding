package com.vivvo.onboarding.repository;

import com.vivvo.onboarding.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUsername(String username);

    //fname we don't generally abbreviate. It usually leaves the next developer guessing. particularily when you get into
    //business names. One i just ran into yesterday was someone that abbreviated prodFeat. I initially thought prod was 'production'
    //so was thinking it was production feature. but it is actually product feature.
    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    Page<User> findByFirstNameContainsOrLastNameContainsOrUsernameContains(String firstName, String lastName, String username, Pageable pageable);

    //Page<User> findAllByUsername(String username, Pageable pageable);

    //Page<User> findAllByFirstName(String firstName, Pageable pageable);

    //Page<User> findAllByLastName(String lastName, Pageable pageable);

    //Page<User> findAllByPhoneNumber(String phoneNumber, Pageable pageable);





}

