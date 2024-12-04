package com.isslpnu.backend.api;

import com.isslpnu.backend.api.dto.PasswordRestoreRequest;
import com.isslpnu.backend.api.dto.SignInResponse;
import com.isslpnu.backend.api.dto.SingInRequest;
import com.isslpnu.backend.api.dto.SingUpRequest;
import com.isslpnu.backend.api.dto.action.AuthenticationActionRequest;
import com.isslpnu.backend.api.dto.action.PasswordRestoreActionRequest;
import com.isslpnu.backend.api.dto.action.TwoFactorRequest;
import com.isslpnu.backend.api.validator.SignInValidator;
import com.isslpnu.backend.api.validator.SignUpValidator;
import com.isslpnu.backend.service.AuthenticationActionService;
import com.isslpnu.backend.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationService service;
    private final SignUpValidator signUpValidator;
    private final SignInValidator signInValidator;
    private final AuthenticationActionService actionService;

    @InitBinder("singUpRequest")
    public void initSingUpRequestBinder(WebDataBinder binder) {
        binder.addValidators(signUpValidator);
    }

    @InitBinder("singInRequest")
    public void initSingInRequestBinder(WebDataBinder binder) {
        binder.addValidators(signInValidator);
    }

    @PostMapping("/singIn")
    public SignInResponse singIn(@Valid @RequestBody SingInRequest request) {
        return service.signIn(request);
    }

    @PostMapping("/singUp")
    public void singUp(@Valid @RequestBody SingUpRequest request) {
        service.signUp(request);
    }

    @PostMapping("/restorePassword")
    public void restorePassword(@Valid @RequestBody PasswordRestoreRequest request) {
        service.restorePassword(request);
    }

    @PostMapping("/action/confirmSignUp")
    public void confirmSignUp(@Valid @RequestBody AuthenticationActionRequest request) {
        actionService.confirmSignUp(request);
    }

    @PostMapping("/action/twoFactorConfirm")
    public SignInResponse twoFactorConfirm(@Valid @RequestBody TwoFactorRequest request) {
        return actionService.twoFactorConfirm(request);
    }

    @PostMapping("/action/restorePasswordConfirm")
    public void restorePasswordConfirm(@Valid @RequestBody PasswordRestoreActionRequest request) {
        actionService.restorePasswordConfirm(request);
    }
}
