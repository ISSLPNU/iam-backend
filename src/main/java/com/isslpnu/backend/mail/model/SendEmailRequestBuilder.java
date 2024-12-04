package com.isslpnu.backend.mail.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SendEmailRequestBuilder {

    private String messageCode;
    private Object[] messageArgs;
    private String subjectCode;
    private Object[] subjectArgs;
    private Set<String> sendTo;

    public SendEmailRequestBuilder messageCode(String messageCode) {
        this.messageCode = messageCode;
        return this;
    }

    public SendEmailRequestBuilder messageArgs(Object... args) {
        this.messageArgs = args;
        return this;
    }

    public SendEmailRequestBuilder subjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
        return this;
    }

    public SendEmailRequestBuilder subjectArgs(Object... args) {
        this.subjectArgs = args;
        return this;
    }

    public SendEmailRequestBuilder sendTo(String... emails) {
        this.sendTo = Set.of(emails);
        return this;
    }

    public SendEmailRequest build() {
        return new SendEmailRequest(messageCode, messageArgs, subjectCode, subjectArgs, sendTo);
    }
}
