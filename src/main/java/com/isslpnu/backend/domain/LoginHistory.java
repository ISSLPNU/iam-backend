package com.isslpnu.backend.domain;

import com.isslpnu.backend.constant.LoginStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(schema = "login_history")
public class LoginHistory extends BaseEntity {

    @Column(name = "ip")
    private String ip;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LoginStatus status;
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
