package com.isslpnu.backend.mail.event;

import com.isslpnu.backend.mail.model.SendEmailRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MailEvent {

    private SendEmailRequest request;

}
