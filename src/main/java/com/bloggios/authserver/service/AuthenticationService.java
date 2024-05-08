package com.bloggios.authserver.service;

import com.bloggios.authserver.payload.request.LoginRequest;
import com.bloggios.authserver.payload.request.RegisterRequest;
import com.bloggios.authserver.payload.response.ApplicationResponse;
import com.bloggios.authserver.payload.response.AuthResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    AuthResponse loginUser(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response);
    ApplicationResponse verifyOtp(String otp, String userId);
    ApplicationResponse resendOtp(String userId);
    ApplicationResponse otpRedirectUserId(LoginRequest loginRequest);
    ApplicationResponse logoutUser(HttpServletRequest request, HttpServletResponse response);
    AuthResponse refreshAccessToken(HttpServletRequest request, HttpServletResponse response);
}
