package com.isslpnu.backend.mapper;

import com.isslpnu.backend.constant.LoginStatus;
import com.isslpnu.backend.domain.LoginHistory;
import com.isslpnu.backend.security.util.SessionUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {SessionUtil.class})
public interface LoginHistoryMapper {

    @Mapping(target = "ip", expression = "java(SessionUtil.getIp())")
    LoginHistory asLoginHistory(LoginStatus status, String error);

}
