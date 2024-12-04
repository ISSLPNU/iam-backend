package com.isslpnu.backend.exception;

import java.util.UUID;

public class NotFoundException extends RuntimeException {

    public NotFoundException(Class<?> entityType, UUID id) {
        this(entityType, id.toString());
    }

    public NotFoundException(Class<?> entityType, String cause) {
        super("Entity %s is not found by %s.".formatted(entityType.getSimpleName(), cause));
    }
}
