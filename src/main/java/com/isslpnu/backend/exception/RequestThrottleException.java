package com.isslpnu.backend.exception;

public class RequestThrottleException extends RuntimeException{

    public RequestThrottleException(String message) {
        super(message);
    }
}
