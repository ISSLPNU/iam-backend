package com.isslpnu.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "two_factor")
public class TwoFactor extends BaseEntity {

    @Column(name = "confirmation_token_id", updatable = false)
    private UUID confirmationTokenId;
    @Column(name = "secret")
    private long secret;

}
