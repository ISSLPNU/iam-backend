package com.isslpnu.backend.scheduler;

import com.isslpnu.backend.domain.ConfirmationToken;
import com.isslpnu.backend.domain.User;
import com.isslpnu.backend.repository.UserRepository;
import com.isslpnu.backend.service.ConfirmationTokenService;
import com.isslpnu.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenScheduler {

    private final ConfirmationTokenService confirmationTokenService;
    private final UserService userService;

    @Value("${default_ttl.confirmation_action}")
    private int actionTtl;

    @Scheduled(cron = "30 * * * * *")
    public void cleanOldItems() {
        //TODO 11/30/24: Add Lock...
        int lastSize;
        do {
            List<ConfirmationToken> tokens = confirmationTokenService.findTop100ByCreatedAtBefore(LocalDateTime.now().minusMinutes(actionTtl));
            List<User> users = userService.getAll(tokens.stream().map(ConfirmationToken::getUserId).toList());
            users.stream().filter(user -> !user.isEmailConfirmed()).forEach(userService::delete);
            lastSize = tokens.size();
            confirmationTokenService.delete(tokens);
        } while (lastSize == 100);
    }
}
