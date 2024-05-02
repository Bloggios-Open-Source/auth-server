package com.bloggios.authserver.properties;

import com.bloggios.authserver.constants.BeanNameConstants;
import com.bloggios.authserver.payload.response.ExceptionResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import java.util.Properties;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.properties
 * Created_on - May 01 - 2024
 * Created_at - 16:16
 */

@Configuration
public class FetchErrorProperties {

    private final Properties properties;

    public FetchErrorProperties(
            @Qualifier(BeanNameConstants.ERROR_PROPERTIES_BEAN) Properties properties
    ) {
        this.properties = properties;
    }

    public ExceptionResponse exceptionResponse(HttpStatus status, String code) {
        String property = properties.getProperty(code);
        String message = "";
        String field = "";
        String type = "INTERNAL ERROR";
        if (!ObjectUtils.isEmpty(property)) {
            String[] strings = property.split("~");
            message = strings[0];
            field = strings.length > 1 ? strings[1] : "";
        }
        if (code.startsWith("DE")) {
            type = "DATA ERROR";
        }
        return new ExceptionResponse(message, code, field, type, status.name());
    }

    public ExceptionResponse generateExceptionResponse(HttpStatus status, String message, String code) {
        String[] split = message.split("~");
        String field = split.length > 1 ? split[1] : "";
        String type = "INTERNAL ERROR";
        if (code.startsWith("DE")) {
            type = "DATA ERROR";
        }
        return ExceptionResponse
                .builder()
                .message(split[0])
                .field(field)
                .status(status.name())
                .type(type)
                .code(code)
                .build();
    }
}
