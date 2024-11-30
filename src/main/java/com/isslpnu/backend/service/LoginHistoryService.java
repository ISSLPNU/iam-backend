package com.isslpnu.backend.service;

import com.isslpnu.backend.constant.LoginStatus;
import com.isslpnu.backend.domain.LoginHistory;
import com.isslpnu.backend.mapper.LoginHistoryMapper;
import com.isslpnu.backend.repository.LoginHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginHistoryService extends AbstractService<LoginHistory> {

    private final LoginHistoryMapper mapper;
    private final LoginHistoryRepository repository;

    public void create(LoginStatus status, String error) {
        super.create(mapper.asLoginHistory(status, error));
    }

    @Override
    protected JpaRepository<LoginHistory, String> getRepository() {
        return repository;
    }

    @Override
    protected Class<LoginHistory> getType() {
        return LoginHistory.class;
    }
}
