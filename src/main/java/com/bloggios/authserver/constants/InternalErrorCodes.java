package com.bloggios.authserver.constants;

import lombok.experimental.UtilityClass;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.constants
 * Created_on - May 01 - 2024
 * Created_at - 16:20
 */

/**
 *
 * This class is for the internal error codes
 */

@UtilityClass
public class InternalErrorCodes {

    public static final String EXCEPTION_CODES = "IE__AUTH-1001";
    public static final String ROLE_NOT_FOUND = "IE__AUTH-1002";
    public static final String UNABLE_TO_EXTRACT_AUTHORITIES = "IE__AUTH-1003";
    public static final String UNABLE_TO_EXTRACT_USER_ID_FROM_TOKEN = "IE__AUTH-1004";
    public static final String UNABLE_TO_EXTRACT_REMOTE_ADDRESS_FROM_TOKEN = "IE__AUTH-1005";
    public static final String UNAUTHORIZED_REDIRECT_URI = "IE__AUTH-1006";
}
