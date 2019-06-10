package com.vivvo.onboarding.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public class NotFoundException extends RuntimeException {

    @Getter
    private UUID id;
}