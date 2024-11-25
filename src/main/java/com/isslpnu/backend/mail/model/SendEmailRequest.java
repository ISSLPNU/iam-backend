package com.isslpnu.backend.mail.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SendEmailRequest {

    private String messageCode;
    private Object[] messageArgs;
    private String subjectCode;
    private Object[] subjectArgs;
    private Set<String> sendTo;

}
