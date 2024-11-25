package com.isslpnu.backend.api;

import com.isslpnu.backend.api.dto.AuthenticationActionRequest;
import com.isslpnu.backend.api.dto.PasswordRestoreRequest;
import com.isslpnu.backend.api.dto.SingInRequest;
import com.isslpnu.backend.api.dto.SingUpRequest;
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
    private final AuthenticationActionService actionService;

    @InitBinder("singUpRequest")
    public void initSingUpRequestBinder(WebDataBinder binder) {
        binder.addValidators(signUpValidator);
    }

    @PostMapping("/singIn")
    public void singIn(@Valid @RequestBody SingInRequest request) {
        service.signIn(request);
    }

    @PostMapping("/singUp")
    public void singUp(@Valid @RequestBody SingUpRequest request) {
        service.signUp(request);
    }

    //TODO 11/22/24: maybe move to another controller
    @PostMapping("/restorePassword")
    public void restorePassword(@Valid @RequestBody PasswordRestoreRequest request) {
        service.restorePassword(request);
    }

    @PostMapping("/action")
    public void doAction(@Valid @RequestBody AuthenticationActionRequest request) {
        actionService.doAction(request);
    }
}
