package com.isslpnu.backend.service;

import com.isslpnu.backend.domain.LoginHistory;
import com.isslpnu.backend.repository.LoginHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoginHistoryService extends AbstractService<LoginHistory> {

    private final LoginHistoryRepository repository;

    @Override
    protected JpaRepository<LoginHistory, String> getRepository() {
        return repository;
    }

    @Override
    protected Class<LoginHistory> getType() {
        return LoginHistory.class;
    }
}
