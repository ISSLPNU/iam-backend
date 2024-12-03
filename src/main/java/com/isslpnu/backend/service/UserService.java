package com.isslpnu.backend.service;

import com.isslpnu.backend.api.dto.UserDetailsDto;
import com.isslpnu.backend.domain.User;
import com.isslpnu.backend.exception.NotFoundException;
import com.isslpnu.backend.mapper.UserMapper;
import com.isslpnu.backend.repository.UserRepository;
import com.isslpnu.backend.security.util.SessionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService extends AbstractService<User> {

    private final UserMapper mapper;
    private final UserRepository repository;

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public User getByEmail(String email) {
        return repository.getByEmail(email).orElseThrow(() -> new NotFoundException(User.class, email));
    }

    public UserDetailsDto whoami() {
        return mapper.asUserDetailsDto(getOne(SessionInfo.getUserId()));
    }

    public void updateTfa(boolean tfaEnabled) {
        User user = getOne(SessionInfo.getUserId());
        user.setTfaEnabled(tfaEnabled);
        super.update(user);
    }

    @Override
    protected JpaRepository<User, UUID> getRepository() {
        return repository;
    }

    @Override
    protected Class<User> getType() {
        return User.class;
    }
}
