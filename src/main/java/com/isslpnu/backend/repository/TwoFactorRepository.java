package com.isslpnu.backend.repository;

import com.isslpnu.backend.domain.TwoFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TwoFactorRepository extends JpaRepository<TwoFactor, UUID> {

    TwoFactor findByConfirmationTokenId(UUID confirmationTokenId);

    List<TwoFactor> findByConfirmationTokenIdIn(List<UUID> confirmationTokenIds);

}
