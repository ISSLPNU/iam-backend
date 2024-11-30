package com.isslpnu.backend.service;

import com.isslpnu.backend.domain.ConfirmationToken;
import com.isslpnu.backend.exception.NotFoundException;
import com.isslpnu.backend.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenService extends AbstractService<ConfirmationToken> {

    private final ConfirmationTokenRepository repository;

    @Value("${default_ttl}")
    private int ttl;

    public void create(String token) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        super.create(confirmationToken);
    }

    public ConfirmationToken findByToken(String token) {
        return repository.findByTokenAndCreatedAtAfter(token, LocalDateTime.now().minusMinutes(ttl))
                .orElseThrow(() -> new NotFoundException(ConfirmationToken.class, token));
    }

    public List<ConfirmationToken> findTop100ByCreatedAtAfter(LocalDateTime date) {
        return repository.findTop100ByCreatedAtAfter(date);
    }

    @Override
    protected JpaRepository<ConfirmationToken, String> getRepository() {
        return repository;
    }

    @Override
    protected Class<ConfirmationToken> getType() {
        return ConfirmationToken.class;
    }
}
