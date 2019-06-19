package com.vivvo.onboarding.repository;

import com.vivvo.onboarding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
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

    //List<User> findAllByFirstName(String firstName, Pageable pageable);

}

