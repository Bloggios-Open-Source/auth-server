package com.bloggios.authserver.exception.payload;

import lombok.EqualsAndHashCode;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.exception
 * Created_on - May 01 - 2024
 * Created_at - 16:19
 */

@EqualsAndHashCode(callSuper = true)
public class InternalException extends ExceptionProvider {

    public InternalException(String code) {
        super(code);
    }
}
