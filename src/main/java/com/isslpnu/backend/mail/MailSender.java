package com.isslpnu.backend.mail;

import com.isslpnu.backend.mail.model.SendEmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

import java.util.Locale;

@Slf4j
public abstract class MailSender {

    @Autowired
    private MessageSource messageSource;

    @Value("${mailing.email.replyTo}")
    private String replyTo;

    @Async
    public void sendMail(SendEmailRequest request) {
        try {
            sendMail(convertToSimpleMessage(request));
        } catch (Exception e) {
            log.error("Error on sending email to {}.", request.getSendTo());
        }
    }

    private SimpleMailMessage convertToSimpleMessage(SendEmailRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(messageSource.getMessage(request.getMessageCode(), request.getMessageArgs(), Locale.getDefault()));
        message.setSubject(messageSource.getMessage(request.getSubjectCode(), request.getSubjectArgs(), Locale.getDefault()));
        message.setTo(request.getSendTo().toArray(String[]::new));
        message.setReplyTo(replyTo);
        return message;
    }

    protected abstract void sendMail(SimpleMailMessage message);
}
