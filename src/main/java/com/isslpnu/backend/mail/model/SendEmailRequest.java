package com.isslpnu.backend.mail.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SendEmailRequest {

    private String messageCode;
    private Object[] messageArgs;
    private String subjectCode;
    private Object[] subjectArgs;
    private Set<String> sendTo;

    public static SendEmailRequestBuilder builder(){
        return new SendEmailRequestBuilder();
    }
}
