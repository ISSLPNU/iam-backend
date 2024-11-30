package com.isslpnu.backend.service;

import com.isslpnu.backend.constant.LoginStatus;
import com.isslpnu.backend.domain.LoginHistory;
import com.isslpnu.backend.domain.User;
import com.isslpnu.backend.mapper.LoginHistoryMapper;
import com.isslpnu.backend.repository.LoginHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LoginHistoryService extends AbstractService<LoginHistory> {

    private final LoginHistoryMapper mapper;
    private final LoginHistoryRepository repository;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void create(LoginStatus status, String error) {
        super.create(mapper.asLoginHistory(status, error));
    }

    public int countByIpAndStatusAndCreatedAtAfter(String ip, LoginStatus status, LocalDateTime dateTime){
        return repository.countByIpAndStatusAndCreatedAtAfter(ip, status, dateTime);
    }

    @Override
    protected JpaRepository<LoginHistory, UUID> getRepository() {
        return repository;
    }

    @Override
    protected Class<LoginHistory> getType() {
        return LoginHistory.class;
    }
}
