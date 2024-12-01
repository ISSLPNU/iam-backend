package com.isslpnu.backend.service;

import com.isslpnu.backend.domain.TwoFactor;
import com.isslpnu.backend.mapper.TwoFactorMapper;
import com.isslpnu.backend.repository.TwoFactorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TwoFactorService extends AbstractService<TwoFactor> {

    private final TwoFactorMapper mapper;
    private final TwoFactorRepository repository;

    public void create(UUID confirmationTokenId, long secret) {
        super.create(mapper.asTwoFactor(confirmationTokenId, secret));
    }

    public TwoFactor getByConfirmationTokenId(UUID confirmationTokenId) {
        return repository.findByConfirmationTokenId(confirmationTokenId);
    }

    public List<TwoFactor> getByConfirmationTokenId(List<UUID> confirmationTokenIds) {
        return repository.findByConfirmationTokenIdIn(confirmationTokenIds);
    }

    @Override
    protected JpaRepository<TwoFactor, UUID> getRepository() {
        return repository;
    }

    @Override
    protected Class<TwoFactor> getType() {
        return TwoFactor.class;
    }
}
