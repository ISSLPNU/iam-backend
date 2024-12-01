package com.isslpnu.backend.scheduler;

import com.isslpnu.backend.constant.AuthenticationAction;
import com.isslpnu.backend.domain.ConfirmationToken;
import com.isslpnu.backend.domain.TwoFactor;
import com.isslpnu.backend.service.ConfirmationTokenService;
import com.isslpnu.backend.service.TwoFactorService;
import com.isslpnu.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConfirmationTokenScheduler {

    private final UserService userService;
    private final TwoFactorService twoFactorService;
    private final ConfirmationTokenService confirmationTokenService;

    @Value("${default_ttl.confirmation_action}")
    private int actionTtl;

    @Scheduled(cron = "30 * * * * *")
    public void cleanOldItems() {
        //TODO 11/30/24: Add Lock...
        int lastSize;
        try {
            do {
                List<ConfirmationToken> tokens = confirmationTokenService.getTop100ByCreatedAtBefore(AuthenticationAction.SIGN_UP_CONFIRMATION, LocalDateTime.now().minusMinutes(actionTtl));
                confirmationTokenService.delete(tokens);
                userService.deleteAll(tokens.stream().map(ConfirmationToken::getUserId).toList());
                lastSize = tokens.size();
            } while (lastSize == 100);

            do {
                List<ConfirmationToken> tokens = confirmationTokenService.getTop100ByCreatedAtBefore(AuthenticationAction.PASSWORD_RESTORE, LocalDateTime.now().minusMinutes(actionTtl));
                lastSize = tokens.size();
                confirmationTokenService.delete(tokens);
            } while (lastSize == 100);

            do {
                List<ConfirmationToken> tokens = confirmationTokenService.getTop100ByCreatedAtBefore(AuthenticationAction.TWO_FACTOR, LocalDateTime.now().minusMinutes(actionTtl));
                List<TwoFactor> twoFactors = twoFactorService.getByConfirmationTokenId(tokens.stream().map(ConfirmationToken::getUserId).toList());
                lastSize = tokens.size();
                confirmationTokenService.delete(tokens);
                twoFactorService.delete(twoFactors);
            } while (lastSize == 100);

        } catch (Exception e) {
            log.error("Failed to clean old items in Confirmation Token", e);
        }
    }
}
