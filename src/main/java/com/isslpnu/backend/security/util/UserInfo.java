package com.isslpnu.backend.security.util;

import com.isslpnu.backend.security.model.SessionDetails;
import com.isslpnu.backend.security.model.UserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class UserInfo {

    public static String getUserId(){
        return getSession().getId();
    }

    private static UserDetails getSession() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
