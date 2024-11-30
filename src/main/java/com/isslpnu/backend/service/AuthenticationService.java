package com.isslpnu.backend.service;

import com.isslpnu.backend.api.dto.PasswordRestoreRequest;
import com.isslpnu.backend.api.dto.SignInResponse;
import com.isslpnu.backend.api.dto.SingInRequest;
import com.isslpnu.backend.api.dto.SingUpRequest;
import com.isslpnu.backend.constant.AuthenticationAction;
import com.isslpnu.backend.constant.LoginStatus;
import com.isslpnu.backend.domain.User;
import com.isslpnu.backend.exception.ValidationException;
import com.isslpnu.backend.mail.MailHelper;
import com.isslpnu.backend.mail.model.SendEmailRequest;
import com.isslpnu.backend.mapper.AuthMapper;
import com.isslpnu.backend.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriTemplate;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final AuthMapper mapper;
    private final MailHelper mailHelper;
    private final TokenService tokenService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final LoginHistoryService loginHistoryService;
    private final ConfirmationTokenService confirmationTokenService;

    @Value("${url.action}")
    private String actionUrl;

    @Transactional(readOnly = true)
    public SignInResponse signIn(SingInRequest request) {
        User user = userService.getByEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            loginHistoryService.create(LoginStatus.FAILURE, "Credentials are invalid.");
            throw new ValidationException("validation.authentication.invalidCredentials", "Credentials are invalid.");
        }

        if (!user.isEmailConfirmed()) {
            return mapper.asSignInResponse(false, true);
        }

        if (user.isTfaEnabled()) {
            //TODO 11/26/24: send mail
            return mapper.asSignInResponse(true, false);
        }

        loginHistoryService.create(LoginStatus.SUCCESS, null);
        return mapper.asSignInResponse(tokenService.generateToken(user.getId(), user.getRole()));
    }

    @Transactional
    public void signUp(SingUpRequest request) {
        User user = mapper.asUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.create(user);

        String token = tokenService.generateToken(user.getEmail(), AuthenticationAction.SIGN_UP_CONFIRMATION);
        confirmationTokenService.create(token, user.getId());

        String link = new UriTemplate(actionUrl).expand(token).toString();
        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .messageCode("notification.signUp.confirm.email")
                .messageArgs(LocalDateTime.now(), link)
                .subjectCode("notification.signUp.confirm.subject")
                .sendTo(request.getEmail())
                .build();
        mailHelper.sendMailAfterCommit(sendEmailRequest);
    }

    @Transactional
    public void restorePassword(PasswordRestoreRequest request) {
        User user = userService.getByEmail(request.getEmail());
        String token = tokenService.generateToken(user.getEmail(), AuthenticationAction.PASSWORD_RESTORE);
        confirmationTokenService.create(token, user.getId());

        String link = new UriTemplate(actionUrl).expand(token).toString();
        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .messageCode("notification.passwordRestore.email")
                .messageArgs(LocalDateTime.now(), link)
                .subjectCode("notification.passwordRestore.subject")
                .sendTo(request.getEmail())
                .build();
        mailHelper.sendMailAfterCommit(sendEmailRequest);
    }
}
