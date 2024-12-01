package com.isslpnu.backend.mapper;

import com.isslpnu.backend.domain.TwoFactor;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TwoFactorMapper {

    TwoFactor asTwoFactor(UUID confirmationTokenId, long secret);

}
