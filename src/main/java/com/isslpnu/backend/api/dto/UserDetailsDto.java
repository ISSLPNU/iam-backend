package com.isslpnu.backend.api.dto;

import com.isslpnu.backend.constant.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDetailsDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean tfaEnabled;
    private Role role;

}
