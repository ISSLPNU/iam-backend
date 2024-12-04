package com.isslpnu.backend.domain;

import com.isslpnu.backend.constant.LoginStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "login_history")
public class LoginHistory extends AuditableEntity {

    @Column(name = "ip")
    private String ip;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LoginStatus status;
    @Column(name = "error")
    private String error;

}
