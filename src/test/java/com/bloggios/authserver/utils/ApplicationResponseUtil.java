package com.bloggios.authserver.utils;

import com.bloggios.authserver.constants.ResponseMessageConstants;
import com.bloggios.authserver.payload.response.ApplicationResponse;
import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.utils
 * Created_on - May 01 - 2024
 * Created_at - 12:36
 */

@UtilityClass
public class ApplicationResponseUtil {

    public static ApplicationResponse getRegisterUserResponse() {
        return ApplicationResponse
                .builder()
                .message(ResponseMessageConstants.REGISTERED_USER)
                .userId(UUID.randomUUID().toString())
                .build();
    }
}
