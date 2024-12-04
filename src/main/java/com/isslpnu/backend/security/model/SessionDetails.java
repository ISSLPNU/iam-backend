package com.isslpnu.backend.security.model;

import com.isslpnu.backend.constant.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@Setter
public class SessionDetails {

    private String id;
    private Role role;
    private String ip;
    private List<SimpleGrantedAuthority> authorities;

}
