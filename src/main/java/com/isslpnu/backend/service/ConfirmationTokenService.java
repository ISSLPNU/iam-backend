package com.isslpnu.backend.service;

import com.isslpnu.backend.constant.AuthenticationAction;
import com.isslpnu.backend.domain.ConfirmationToken;
import com.isslpnu.backend.exception.NotFoundException;
import com.isslpnu.backend.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenService extends AbstractService<ConfirmationToken> {

    private final ConfirmationTokenRepository repository;

    public void create(String token, UUID userId) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setUserId(userId);
        super.create(confirmationToken);
    }

    public ConfirmationToken findByToken(String token, LocalDateTime dateTime) {
        return repository.findByTokenAndCreatedAtAfter(token, dateTime)
                .orElseThrow(() -> new NotFoundException(ConfirmationToken.class, token));
    }

    public List<ConfirmationToken> findTop100ByCreatedAtBefore(LocalDateTime date) {
        return repository.findTop100ByCreatedAtBefore(date);
    }

    @Override
    protected JpaRepository<ConfirmationToken, UUID> getRepository() {
        return repository;
    }

    @Override
    protected Class<ConfirmationToken> getType() {
        return ConfirmationToken.class;
    }
}
