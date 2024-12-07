package com.isslpnu.backend.security.model;


import com.isslpnu.backend.constant.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@Setter
public class UserDetails {

    private String id;
    private Role role;
    private List<SimpleGrantedAuthority> authorities;

}
