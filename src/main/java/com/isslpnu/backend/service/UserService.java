package com.isslpnu.backend.service;

import com.isslpnu.backend.domain.User;
import com.isslpnu.backend.exception.NotFoundException;
import com.isslpnu.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService extends AbstractService<User> {

    private final UserRepository repository;

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public User getByEmail(String email) {
        return repository.getByEmail(email).orElseThrow(() -> new NotFoundException(User.class, email));
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
