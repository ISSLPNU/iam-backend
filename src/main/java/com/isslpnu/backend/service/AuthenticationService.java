package com.isslpnu.backend.service;

import com.isslpnu.backend.api.dto.PasswordRestoreRequest;
import com.isslpnu.backend.api.dto.SingInRequest;
import com.isslpnu.backend.api.dto.SingUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserService userService;
    private final LoginHistoryService loginHistoryService;
    private final ConfirmationTokenService confirmationTokenService;

    public void signIn(SingInRequest request) {
        //TODO 11/22/24: Fill in the next task
        try {

        } catch (Exception e) {

        } finally {

        }
    }

    public void signUp(SingUpRequest request) {
        //TODO 11/22/24: Fill in the next task
    }

    public void restorePassword(PasswordRestoreRequest request) {
        //TODO 11/22/24: restore password
    }
}
