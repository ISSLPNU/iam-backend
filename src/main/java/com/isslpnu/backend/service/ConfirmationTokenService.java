package com.isslpnu.backend.service;

import com.isslpnu.backend.domain.ConfirmationToken;
import com.isslpnu.backend.exception.NotFoundException;
import com.isslpnu.backend.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenService extends AbstractService<ConfirmationToken> {

    private final ConfirmationTokenRepository repository;

    public ConfirmationToken findByToken(String token) {
        return repository.findByToken(token)
                .orElseThrow(() -> new NotFoundException(ConfirmationToken.class, token));
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
