package com.isslpnu.backend.service;

import com.isslpnu.backend.constant.AuthenticationAction;
import com.isslpnu.backend.domain.ConfirmationToken;
import com.isslpnu.backend.exception.NotFoundException;
import com.isslpnu.backend.mapper.ConfirmationTokenMapper;
import com.isslpnu.backend.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenService extends AbstractService<ConfirmationToken> {

    private final ConfirmationTokenMapper mapper;
    private final ConfirmationTokenRepository repository;

    public ConfirmationToken create(String token, UUID userId, AuthenticationAction action) {
        return super.create(mapper.asConfirmationToken(token, userId, action));
    }

    public ConfirmationToken getByToken(String token, LocalDateTime dateTime) {
        return repository.findByTokenAndCreatedAtAfter(token, dateTime)
                .orElseThrow(() -> new NotFoundException(ConfirmationToken.class, token));
    }

    public ConfirmationToken getByUserIdAndAction(UUID userId, AuthenticationAction action, LocalDateTime dateTime) {
        return repository.findByUserIdAndActionAndCreatedAtAfter(userId, action, dateTime);
    }

    public List<ConfirmationToken> getTop100ByCreatedAtBefore(AuthenticationAction action, LocalDateTime date) {
        return repository.findTop100ByActionAndCreatedAtBefore(action, date);
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
