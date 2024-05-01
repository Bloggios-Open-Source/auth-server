package com.bloggios.authserver.exception;

import lombok.EqualsAndHashCode;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.exception
 * Created_on - May 01 - 2024
 * Created_at - 16:12
 */

@EqualsAndHashCode(callSuper = true)
public class BadRequestException extends ExceptionProvider {

    public BadRequestException(String code) {
        super(code);
    }

    public BadRequestException(String code, String message, String field) {
        super(code);
        this.message = message;
        this.field = field;
    }

    private String message;
    private String field;
}
