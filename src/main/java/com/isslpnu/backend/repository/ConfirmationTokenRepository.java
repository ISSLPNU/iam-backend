package com.isslpnu.backend.repository;

import com.isslpnu.backend.domain.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, UUID> {

    List<ConfirmationToken> findAllByCreatedAtBefore(LocalDateTime date);

    Optional<ConfirmationToken> findByTokenAndCreatedAtAfter(String token, LocalDateTime dateTime);

    List<ConfirmationToken> findTop100ByCreatedAtBefore(LocalDateTime dateTime);


}
