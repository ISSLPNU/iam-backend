package com.isslpnu.backend.domain;

import com.isslpnu.backend.constant.Role;
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
@Table(schema = "app_user")
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "tfa_enabled")
    private String tfaEnabled;
    @Column(name = "email_confirmed")
    private boolean emailConfirmed;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

}
