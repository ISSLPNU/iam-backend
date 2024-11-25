package com.isslpnu.backend.service;

import com.isslpnu.backend.domain.BaseEntity;
import com.isslpnu.backend.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractService<T extends BaseEntity> {

    public T getOne(String id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new NotFoundException(getType(), id));
    }

    public T create(T entity) {
        return getRepository().save(entity);
    }

    public T update(T entity) {
        return getRepository().save(entity);
    }

    protected abstract JpaRepository<T, String> getRepository();

    protected abstract Class<T> getType();

}
