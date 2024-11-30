package com.isslpnu.backend.mapper;

import com.isslpnu.backend.constant.LoginStatus;
import com.isslpnu.backend.domain.LoginHistory;
import com.isslpnu.backend.security.util.SessionInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SessionInfo.class})
public interface LoginHistoryMapper {

    @Mapping(target = "ip", expression = "java(SessionInfo.getIp())")
    LoginHistory asLoginHistory(LoginStatus status, String error);

}
