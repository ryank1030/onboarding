package com.vivvo.onboarding.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class ValidationException extends RuntimeException {

    @Getter
    private Map<String, String> errors;
}
