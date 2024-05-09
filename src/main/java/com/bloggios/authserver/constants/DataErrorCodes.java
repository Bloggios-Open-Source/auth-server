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
    public static final String USER_NOT_FOUND = "DE__AUTH-2005";
    public static final String INCORRECT_PASSWORD = "DE__AUTH-2006";
    public static final String USER_INACTIVE = "DE__AUTH-2007";
    public static final String ACCOUNT_EXPIRED = "DE__AUTH-2008";
    public static final String USER_CREDENTIALS_EXPIRED = "DE__AUTH-2009";
    public static final String ACCOUNT_LOCKED = "DE__AUTH-2010";
    public static final String AUTHORITIES_LONG_TOKEN = "DE__AUTH-2011";
    public static final String USER_NOT_VERIFIED = "DE__AUTH-2012";
    public static final String OTP_EMPTY = "DE__AUTH-2013";
    public static final String USER_ID_NOT_PRESENT_VERIFY_OTP = "DE__AUTH-2014";
    public static final String USER_NOT_PRESENT_FOR_OTP = "DE__AUTH-2015";
    public static final String USER_ALREADY_VERIFIED = "DE__AUTH-2016";
    public static final String EXPIRED_OTP = "DE__AUTH-2017";
    public static final String OTP_NOT_VALID = "DE__AUTH-2018";
    public static final String OTP_RESEND_LIMIT_EXCEED = "DE__AUTH-2019";
    public static final String PROVIDER_NOT_EMAIL_RESEND_OTP = "DE__AUTH-2020";
    public static final String EMAIL_ALREADY_REGISTERED = "DE__AUTH-2021";
}
