package com.isslpnu.backend.mail;

import com.isslpnu.backend.constant.ApplicationProfiles;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Profile(ApplicationProfiles.LOCAL)
public class SimpleMailSender extends MailSender {

    private final JavaMailSender mailSender;

    @Override
    protected void sendMail(SimpleMailMessage message) {
        mailSender.send(message);
    }
}
