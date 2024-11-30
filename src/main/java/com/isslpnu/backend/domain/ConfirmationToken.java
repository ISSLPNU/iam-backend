package com.isslpnu.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken extends BaseEntity {

    @Column(name = "token")
    private String token;

}
