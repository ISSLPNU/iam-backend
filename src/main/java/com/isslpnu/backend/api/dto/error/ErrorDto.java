package com.isslpnu.backend.api.dto.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ErrorDto {

    private String message;
    private List<String> errors;

}
