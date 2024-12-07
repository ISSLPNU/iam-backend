package com.isslpnu.backend.security.util;

import com.isslpnu.backend.security.model.SessionDetails;

public class SessionUtil {

    private static final ThreadLocal<SessionDetails> SESSION_INFO = new ThreadLocal<>();

    public static String getIp(){
        return SESSION_INFO.get().getIp();
    }

    public static void setSessionDetails(SessionDetails sessionDetails){
        SESSION_INFO.set(sessionDetails);
    }

}
