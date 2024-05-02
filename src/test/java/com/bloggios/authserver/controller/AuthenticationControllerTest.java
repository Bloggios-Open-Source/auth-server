package com.bloggios.authserver.controller;

import com.bloggios.authserver.payload.request.RegisterRequest;
import com.bloggios.authserver.payload.response.ApplicationResponse;
import com.bloggios.authserver.service.AuthenticationService;
import com.bloggios.authserver.utils.ApplicationResponseUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.controller
 * Created_on - May 01 - 2024
 * Created_at - 12:34
 */

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authenticationService;

    @Test
    void registerUser() {
        Mockito.when(authenticationService.registerUser(ArgumentMatchers.any(RegisterRequest.class)))
                .thenReturn(ApplicationResponseUtil.getRegisterUserResponse());
        ResponseEntity<ApplicationResponse> applicationResponseResponseEntity = authenticationController.registerUser(new RegisterRequest());
        assertEquals(200, applicationResponseResponseEntity.getStatusCodeValue());
    }
}