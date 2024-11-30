package com.isslpnu.backend.exception;

import com.isslpnu.backend.api.dto.error.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(RequestThrottleException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ErrorDto throttleException(RequestThrottleException e) {
        return build(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto validationException(ValidationException e) {
        String message = messageSource.getMessage(e.getMessageCode(), e.getMessageArgs(), e.getDefaultMessage(), Locale.getDefault());
        return build(message);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto validationException(BindException e) {
        BindingResult result = e.getBindingResult();
        List<String> errors = result.getGlobalErrors().stream()
                .map(error -> messageSource.getMessage(error, Locale.getDefault()))
                .toList();
        return ErrorDto.builder()
                .message("Validation Error")
                .errors(errors)
                .build();
    }

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto invalidParameterException(InvalidParameterException e) {
        return build(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto exception(Exception e) {
        return build(e.getMessage());
    }

    private ErrorDto build(String message) {
        return ErrorDto.builder()
                .message(message)
                .build();
    }
}
