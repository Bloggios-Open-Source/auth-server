package com.bloggios.authserver.constants;

import lombok.experimental.UtilityClass;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.constants
 * Created_on - May 02 - 2024
 * Created_at - 12:25
 */

@UtilityClass
public class EnvironmentConstants {

    public static final String APPLICATION_VERSION = "application.version";
    public static final String USER_INDEX = "#{@environment.getProperty('elasticsearch.user')}";
    public static final String ES_SETTING = "/es-setting.json";
}
