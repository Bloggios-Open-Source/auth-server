package com.bloggios.authserver.constants;

import lombok.experimental.UtilityClass;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.constants
 * Created_on - May 01 - 2024
 * Created_at - 16:34
 */

/**
 *
 * This class is for the data error codes
 */
@UtilityClass
public class DataErrorCodes {

    public static final String EMAIL_MANDATORY = "DE__AUTH-2001";
    public static final String EMAIL_NOT_VALID = "DE__AUTH-2002";
    public static final String PASSWORD_EMPTY = "DE__AUTH-2003";
    public static final String PASSWORD_CRITERIA_NOT_MATCHED = "DE__AUTH-2004";
}
