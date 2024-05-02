package com.bloggios.authserver.utils;

import com.bloggios.authserver.payload.response.ExceptionResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.utils
 * Created_on - May 01 - 2024
 * Created_at - 20:30
 */

@UtilityClass
public class ExceptionResponseUtil {

    public static ExceptionResponse getExceptionResponse(String type, String code) {
        return ExceptionResponse
                .builder()
                .message("Error Message")
                .type(type)
                .status(HttpStatus.BAD_REQUEST.name())
                .field("errorField")
                .code(code)
                .build();
    }
}
