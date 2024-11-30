package com.isslpnu.backend.domain;

import com.isslpnu.backend.constant.AuthenticationAction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken extends AuditableEntity {

    @Column(name = "token")
    private String token;
    @Column(name = "user_id", updatable = false)
    private UUID userId;

}
