package com.bloggios.authserver.service;

import com.bloggios.authserver.payload.request.RegisterRequest;
import com.bloggios.authserver.payload.response.ApplicationResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.service
 * Created_on - May 01 - 2024
 * Created_at - 12:26
 */

public interface AuthenticationService {

    ApplicationResponse registerUser(RegisterRequest registerRequest, HttpServletRequest httpServletRequest);
}
