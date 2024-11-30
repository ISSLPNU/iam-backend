package com.isslpnu.backend.api.validator;

import com.isslpnu.backend.api.dto.SingInRequest;
import com.isslpnu.backend.constant.LoginStatus;
import com.isslpnu.backend.exception.RequestThrottleException;
import com.isslpnu.backend.security.util.SessionInfo;
import com.isslpnu.backend.service.LoginHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class SignInValidator implements Validator {

    private final LoginHistoryService loginHistoryService;

    @Value("${auth.max_attempts}")
    private int maxAttempts;
    @Value("${auth.period}")
    private int period;

    @Override
    public boolean supports(Class<?> clazz) {
        return SingInRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String ip = SessionInfo.getIp();
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(period);
        int counter = loginHistoryService.countByIpAndStatusAndCreatedAtAfter(ip, LoginStatus.FAILURE, dateTime);
        if (counter > maxAttempts) {
            throw new RequestThrottleException("Too many sign in requests :)");
        }
    }
}