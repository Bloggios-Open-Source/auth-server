package com.bloggios.authserver.constants;

import lombok.experimental.UtilityClass;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.constants
 * Created_on - April 30 - 2024
 * Created_at - 12:40
 */

@UtilityClass
public class ServiceConstants {

    public static final String RANDOM_UUID = "randomUUID";
    public static final String UUID_GENERATOR = "org.hibernate.id.UUIDGenerator";
    public static final String BREADCRUMB_ID = "breadcrumbId";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{9,}$";
}
