package com.isslpnu.backend.mail;

import com.isslpnu.backend.constant.ApplicationProfiles;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Profile(ApplicationProfiles.PRODUCTION)
public class AwsMailSender extends MailSender {

    @Override
    protected void sendMail(SimpleMailMessage message) {
        throw new NotImplementedException("Email sending via SES is not implemented for now.");
    }
}
