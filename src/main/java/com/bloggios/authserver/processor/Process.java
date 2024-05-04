package com.bloggios.authserver.processor;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.processor
 * Created_on - May 04 - 2024
 * Created_at - 10:27
 */

@FunctionalInterface
public interface Process<A> {

    void process(A a);
}
