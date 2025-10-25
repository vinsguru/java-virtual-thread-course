package com.vinsguru.sec09.security.scopedvalue;

import com.vinsguru.sec09.security.SecurityContext;
import com.vinsguru.sec09.security.UserRole;

import java.util.Map;

public class AuthenticationService {

    private static final String VALID_PASSWORD = "password";
    private static final Map<Integer, UserRole> USER_ROLES = Map.of(
            1, UserRole.ADMIN,
            2, UserRole.EDITOR,
            3, UserRole.VIEWER
    );

    public static void loginAndExecute(Integer userId, String password, Runnable runnable) {
        if (!VALID_PASSWORD.equals(password)) {
            throw new SecurityException("Invalid password for user id:" + userId);
        }
        var securityContext = new SecurityContext(userId, USER_ROLES.getOrDefault(userId, UserRole.ANONYMOUS));
        ScopedValue.where(SecurityContextHolder.getScopedValue(), securityContext)
                   .run(runnable);
    }

    public static void runAsAdmin(Runnable runnable) {
        var securityContext = SecurityContextHolder.getScopedValue()
                                                   .orElseThrow(() -> new SecurityException("User must login"));
        var elevatedContext = new SecurityContext(securityContext.userId(), UserRole.ADMIN);
        ScopedValue.where(SecurityContextHolder.getScopedValue(), elevatedContext)
                   .run(runnable);
    }

}
