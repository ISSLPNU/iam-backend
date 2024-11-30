package com.isslpnu.backend.repository;

import com.isslpnu.backend.constant.LoginStatus;
import com.isslpnu.backend.domain.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, UUID> {

    int countByIpAndStatusAndCreatedAtAfter(String ip, LoginStatus status, LocalDateTime dateTime);

}
