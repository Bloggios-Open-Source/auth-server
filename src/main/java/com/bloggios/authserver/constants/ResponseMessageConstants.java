package com.bloggios.authserver.constants;

import lombok.experimental.UtilityClass;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.constants
 * Created_on - May 01 - 2024
 * Created_at - 12:37
 */

@UtilityClass
public class ResponseMessageConstants {

    public static final String REGISTERED_USER = "User registered successfully to Bloggios. Please verify your email to continue";
    public static final String NO_REFRESH_TOKEN_GENERATED = "No refresh token generated as logged in from local";
    public static final String OTP_VERIFIED_SUCCESSFULLY = "Verified Successfully";
    public static final String OTP_RESENT = "OTP sent to your mail address";
}
