package com.isslpnu.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isslpnu.backend.constant.OAuthProvider;
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
@Table(name = "app_user")
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @JsonIgnore
    @Column(name = "password")
    private String password;
    @Column(name = "tfa_enabled")
    private boolean tfaEnabled;
    @Column(name = "email_confirmed")
    private boolean emailConfirmed;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_provider")
    private OAuthProvider oAuthProvider;

}
