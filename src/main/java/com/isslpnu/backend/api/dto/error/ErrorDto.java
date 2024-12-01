package com.isslpnu.backend.api.dto.error;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class ErrorDto {

    private String message;
    private List<String> errors;
    private Map<String, String> fieldErrors;

}
