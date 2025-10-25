package com.vinsguru.sec09.security;

public record SecurityContext(Integer userId,
                              UserRole role) {

    public boolean hasPermission(UserRole requiredRole){
        return this.role.ordinal() <= requiredRole.ordinal();
    }

}
