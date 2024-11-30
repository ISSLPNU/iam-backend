package com.isslpnu.backend.mail;

import com.isslpnu.backend.mail.model.SendEmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@RequiredArgsConstructor
@Component
public class MailHelper {

    private final MailSender mailSender;

    public void sendMailAfterCommit(SendEmailRequest request) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                mailSender.sendMail(request);
            }
        });
    }
}
