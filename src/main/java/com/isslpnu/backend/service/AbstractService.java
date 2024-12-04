package com.isslpnu.backend.service;

import com.isslpnu.backend.domain.BaseEntity;
import com.isslpnu.backend.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Transactional
public abstract class AbstractService<T extends BaseEntity> {

    public T getOne(String id) {
        return getOne(UUID.fromString(id));
    }

    public T getOne(UUID id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new NotFoundException(getType(), id));
    }

    public List<T> getAll(Collection<UUID> ids) {
        return getRepository().findAllById(ids);
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

    public void deleteAll(Collection<UUID> ids) {
        delete(getAll(ids));
    }

    public void delete(T entity) {
        getRepository().delete(entity);
    }

    public void delete(Collection<T> entities) {
        entities.forEach(this::delete);
    }

    protected abstract JpaRepository<T, UUID> getRepository();

    protected abstract Class<T> getType();

}
