package com.gerardo.thrive.auth.security;

public final class SecurityEndpoints {
    private SecurityEndpoints() {
    }

    public static final String[] PUBLIC = {
            "/api/security/user/test/register",
            "/api/security/user/test/public/**",
            "/api/security/user/test/login",
            "/api/security/user/test/refresh",

    };

    public static final String[] PRIVATE = {
            "/api/security/resource/test/data"
    };

    public static final String[] AUTHENTICATED = {
            "/api/security/entity/test/ping",
            "/api/security/user/test/logout"
    };
}
