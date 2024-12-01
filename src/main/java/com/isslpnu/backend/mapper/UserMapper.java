package com.isslpnu.backend.mapper;

import com.isslpnu.backend.api.dto.UserDetailsDto;
import com.isslpnu.backend.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDetailsDto asUserDetailsDto(User user);

}
