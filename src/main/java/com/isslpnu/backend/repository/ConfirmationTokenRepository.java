package com.isslpnu.backend.repository;

import com.isslpnu.backend.domain.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {

    List<ConfirmationToken> findAllByCreatedAtBefore(LocalDateTime date);

    Optional<ConfirmationToken> findByToken(String token);

}
