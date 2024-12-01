package com.isslpnu.backend.exception;

import com.isslpnu.backend.api.dto.error.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

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
        Map<String, String> fieldErrors = result.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, error -> messageSource.getMessage(error, Locale.getDefault())));
        return ErrorDto.builder()
                .message("Validation Error")
                .errors(errors)
                .fieldErrors(fieldErrors)
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
