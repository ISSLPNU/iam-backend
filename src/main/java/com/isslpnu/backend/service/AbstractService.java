package com.isslpnu.backend.service;

import com.isslpnu.backend.domain.BaseEntity;
import com.isslpnu.backend.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public abstract class AbstractService<T extends BaseEntity> {

    public T getOne(String id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new NotFoundException(getType(), id));
    }

    @Transactional
    public T create(T entity) {
        return getRepository().save(entity);
    }

    @Transactional
    public T update(T entity) {
        return getRepository().save(entity);
    }

    public void delete(String id) {
        delete(getOne(id));
    }

    public void delete(T entity) {
        getRepository().delete(entity);
    }

    public void delete(Collection<T> entities) {
        entities.forEach(this::delete);
    }

    protected abstract JpaRepository<T, String> getRepository();

    protected abstract Class<T> getType();

}
