package com.bloggios.authserver.rules;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.rules
 * Created_on - May 01 - 2024
 * Created_at - 16:06
 */

@FunctionalInterface
public interface BusinessValidator<A> {

    void validate(A a);
}
