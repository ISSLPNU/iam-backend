package com.isslpnu.backend.api.validator;

import com.isslpnu.backend.api.dto.SingUpRequest;
import com.isslpnu.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class SignUpValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return SingUpRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SingUpRequest request = (SingUpRequest) target;

        if (userService.existsByEmail(request.getEmail())) {
            errors.reject("validation.signUp.emailExist", "Specified email already exists.");
        }
    }
}
