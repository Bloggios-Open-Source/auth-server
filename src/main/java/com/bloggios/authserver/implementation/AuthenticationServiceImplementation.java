package com.bloggios.authserver.implementation;

import com.bloggios.authserver.payload.request.RegisterRequest;
import com.bloggios.authserver.payload.response.ApplicationResponse;
import com.bloggios.authserver.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.implementation
 * Created_on - May 01 - 2024
 * Created_at - 12:27
 */

@Service
public class AuthenticationServiceImplementation implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImplementation.class);

    @Override
    public ApplicationResponse registerUser(RegisterRequest registerRequest) {
        long startTime = System.currentTimeMillis();

        return null;
    }
}
