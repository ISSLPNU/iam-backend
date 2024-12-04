package com.isslpnu.backend.api.dto.oauth;

import com.isslpnu.backend.constant.OAuthProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthSignInRequest {

    @NotBlank
    private String token;
    @NotNull
    private OAuthProvider oAuthProvider;

}
