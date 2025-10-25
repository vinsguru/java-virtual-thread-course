package com.vinsguru.sec09.security.threadlocal;

import com.vinsguru.sec09.security.SecurityContext;
import com.vinsguru.sec09.security.UserRole;

public class SecurityContextHolder {

    private static final SecurityContext ANONYMOUS_CONTEXT = new SecurityContext(0, UserRole.ANONYMOUS);
    private static final ThreadLocal<SecurityContext> contextHolder = ThreadLocal.withInitial(() -> ANONYMOUS_CONTEXT);

    // package private
    static void setContext(SecurityContext securityContext){
        contextHolder.set(securityContext);
    }

    static void clear(){
        contextHolder.remove();
    }

    // public
    public static SecurityContext getContext(){
        return contextHolder.get();
    }

}
