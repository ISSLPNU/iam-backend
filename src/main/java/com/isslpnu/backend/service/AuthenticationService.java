package com.isslpnu.backend.service;

import com.isslpnu.backend.api.dto.PasswordRestoreRequest;
import com.isslpnu.backend.api.dto.SignInResponse;
import com.isslpnu.backend.api.dto.SingInRequest;
import com.isslpnu.backend.api.dto.SingUpRequest;
import com.isslpnu.backend.constant.AuthenticationAction;
import com.isslpnu.backend.constant.LoginStatus;
import com.isslpnu.backend.domain.ConfirmationToken;
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

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final AuthMapper mapper;
    private final MailHelper mailHelper;
    private final TokenService tokenService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TwoFactorService twoFactorService;
    private final LoginHistoryService loginHistoryService;
    private final ConfirmationTokenService confirmationTokenService;

    @Value("${url.action}")
    private String actionUrl;
    @Value("${default_ttl.confirmation_action}")
    private int actionTtl;

    @Transactional
    public SignInResponse signIn(SingInRequest request) {
        User user = userService.getByEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            loginHistoryService.create(LoginStatus.FAILURE, "Credentials are invalid.");
            throw new ValidationException("validation.authentication.invalidCredentials", "Credentials are invalid.");
        }

        if (!user.isEmailConfirmed()) {
            return mapper.asSignInResponse(false, false);
        }

        if (user.isTfaEnabled()) {
            String token = processTwoFactor(user);
            return mapper.asSignInResponse(token, true);
        }

        loginHistoryService.create(LoginStatus.SUCCESS);
        return mapper.asSignInResponse(tokenService.generateToken(user.getId(), user.getRole()), false);
    }


    private String processTwoFactor(User user) {
        ConfirmationToken confirmationToken = confirmationTokenService.getByUserIdAndAction(user.getId(), AuthenticationAction.TWO_FACTOR,
                LocalDateTime.now().minusMinutes(actionTtl));
        if (Objects.nonNull(confirmationToken)) {
            return confirmationToken.getToken();
        }

        String token = tokenService.generateToken(user.getEmail(), AuthenticationAction.TWO_FACTOR);
        ConfirmationToken createdToken = confirmationTokenService.create(token, user.getId(), AuthenticationAction.TWO_FACTOR);

        String secret = IntStream.rangeClosed(1, 8)
                .mapToObj(i -> SECURE_RANDOM.nextInt(10))
                .map(String::valueOf)
                .collect(Collectors.joining(""));

        twoFactorService.create(createdToken.getId(), Long.parseLong(secret));

        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .messageCode("notification.twoFactor.email")
                .messageArgs(LocalDateTime.now(), secret)
                .subjectCode("notification.twoFactor.subject")
                .sendTo(user.getEmail())
                .build();
        mailHelper.sendMailAfterCommit(sendEmailRequest);

        return createdToken.getToken();
    }

    @Transactional
    public void signUp(SingUpRequest request) {
        User user = mapper.asUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.create(user);

        String token = tokenService.generateToken(user.getEmail(), AuthenticationAction.SIGN_UP_CONFIRMATION);
        confirmationTokenService.create(token, user.getId(), AuthenticationAction.SIGN_UP_CONFIRMATION);

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
        confirmationTokenService.create(token, user.getId(), AuthenticationAction.PASSWORD_RESTORE);

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
