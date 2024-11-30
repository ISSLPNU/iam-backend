package com.isslpnu.backend.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final String messageCode;
    private final Object[] messageArgs;
    private final String defaultMessage;

    public ValidationException(String messageCode, Object[] messageArgs, String defaultMessage) {
        super(defaultMessage);
        this.messageCode = messageCode;
        this.messageArgs = messageArgs;
        this.defaultMessage = defaultMessage;
    }

    public ValidationException(String messageCode, Object[] messageArgs) {
        this(messageCode, messageArgs, null);
    }

    public ValidationException(String messageCode, String defaultMessage) {
        this(messageCode, null, defaultMessage);
    }
}
