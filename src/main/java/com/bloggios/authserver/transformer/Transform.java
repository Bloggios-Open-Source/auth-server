package com.bloggios.authserver.transformer;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.transformer
 * Created_on - May 02 - 2024
 * Created_at - 12:12
 */

@FunctionalInterface
public interface Transform<A, B> {

    A transform(B b);
}
