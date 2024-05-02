package com.bloggios.authserver.implementation;

import com.bloggios.authserver.constants.ResponseMessageConstants;
import com.bloggios.authserver.document.UserDocument;
import com.bloggios.authserver.entity.UserEntity;
import com.bloggios.authserver.payload.request.RegisterRequest;
import com.bloggios.authserver.payload.response.ApplicationResponse;
import com.bloggios.authserver.persistence.RegisterUserPersistence;
import com.bloggios.authserver.rules.implementation.exhibitor.RegisterRequestExhibitor;
import com.bloggios.authserver.service.AuthenticationService;
import com.bloggios.authserver.transformer.implementation.transform.RegisterRequestToUserEntityTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

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

    private final RegisterRequestExhibitor registerRequestExhibitor;
    private final RegisterRequestToUserEntityTransformer registerRequestToUserEntityTransformer;
    private final RegisterUserPersistence registerUserPersistence;

    public AuthenticationServiceImplementation(
            RegisterRequestExhibitor registerRequestExhibitor,
            RegisterRequestToUserEntityTransformer registerRequestToUserEntityTransformer,
            RegisterUserPersistence registerUserPersistence
    ) {
        this.registerRequestExhibitor = registerRequestExhibitor;
        this.registerRequestToUserEntityTransformer = registerRequestToUserEntityTransformer;
        this.registerUserPersistence = registerUserPersistence;
    }

    @Override
    public ApplicationResponse registerUser(RegisterRequest registerRequest, HttpServletRequest httpServletRequest) {
        long startTime = System.currentTimeMillis();
        registerRequestExhibitor.exhibit(registerRequest);
        UserEntity transform = registerRequestToUserEntityTransformer.transform(registerRequest, httpServletRequest);
        UserDocument persist = registerUserPersistence.persist(transform);
        // Send OTP
        logger.info("Register User : Time Taken {}", System.currentTimeMillis() - startTime);
        return ApplicationResponse
                .builder()
                .userId(persist.getUserId())
                .message(ResponseMessageConstants.REGISTERED_USER)
                .build();
    }
}
