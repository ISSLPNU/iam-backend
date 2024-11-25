package com.isslpnu.backend.security.model;

import com.isslpnu.backend.constant.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetails {

    private String id;
    private Role role;

}
