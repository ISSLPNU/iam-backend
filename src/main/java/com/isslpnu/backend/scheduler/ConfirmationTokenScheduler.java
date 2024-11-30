package com.isslpnu.backend.scheduler;

import com.isslpnu.backend.domain.ConfirmationToken;
import com.isslpnu.backend.service.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenScheduler {

    private final ConfirmationTokenService confirmationTokenService;

    @Scheduled(cron = "0 10 * * * *")
    public void cleanOldItems() {
        //TODO 11/30/24: Add Lock...
        int lastSize;
        do {
            List<ConfirmationToken> tokens = confirmationTokenService.findTop100ByCreatedAtAfter(LocalDateTime.now());
            lastSize = tokens.size();
            confirmationTokenService.delete(tokens);
        } while (lastSize == 100);
    }
}
