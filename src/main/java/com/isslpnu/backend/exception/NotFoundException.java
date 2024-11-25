package com.isslpnu.backend.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Class<?> entityType, String id) {
        super("Entity %s is not found by %s.".formatted(entityType.getSimpleName(), id));
    }
}
