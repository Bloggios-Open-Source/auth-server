package com.bloggios.authserver.constants;

import lombok.experimental.UtilityClass;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.constants
 * Created_on - April 30 - 2024
 * Created_at - 15:36
 */

@UtilityClass
public class EndpointConstants {

    private static final String BASE = "/auth-server/v1";

    public static class AuthenticationController {
        public static final String AUTH_BASE = BASE + "/auth";
        public static final String REGISTER_USER = "/register";
        public static final String LOGIN_USER = "/login";
        public static final String VERIFY_OTP = "/verify-otp";
    }
}
