package com.isslpnu.backend.mapper;

import com.isslpnu.backend.constant.AuthenticationAction;
import com.isslpnu.backend.domain.ConfirmationToken;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ConfirmationTokenMapper {

    ConfirmationToken asConfirmationToken(String token, UUID userId, AuthenticationAction action);

}
