package com.isslpnu.backend.security.util;

import com.isslpnu.backend.security.model.SessionDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SessionInfo {

    public static String getIp() {
        return getSession().getIp();
    }

    public static String getUserId(){
        return getSession().getId();
    }

    private static SessionDetails getSession() {
        return (SessionDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
