package com.isslpnu.backend.api;

import com.isslpnu.backend.api.dto.UserDetailsDto;
import com.isslpnu.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    @GetMapping("/whoami")
    public UserDetailsDto whoami() {
        return service.whoami();
    }

    @PatchMapping("/tfa")
    public void updateTfa(@RequestParam boolean enabled) {
        service.updateTfa(enabled);
    }
}
