package com.bloggios.authserver.controller;

import com.bloggios.authserver.constants.EndpointConstants;
import com.bloggios.authserver.payload.request.LoginRequest;
import com.bloggios.authserver.payload.request.RegisterRequest;
import com.bloggios.authserver.payload.response.ApplicationResponse;
import com.bloggios.authserver.payload.response.AuthResponse;
import com.bloggios.authserver.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.controller
 * Created_on - May 01 - 2024
 * Created_at - 12:18
 */

@RestController
@RequestMapping(EndpointConstants.AuthenticationController.AUTH_BASE)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(
            AuthenticationService authenticationService
    ) {
        this.authenticationService = authenticationService;
    }

    /**
     *
     * Endpoint to register new user
     * @param registerRequest
     * @param httpServletRequest
     * @return
     */
    @PostMapping(EndpointConstants.AuthenticationController.REGISTER_USER)
    public ResponseEntity<ApplicationResponse> registerUser(
            @RequestBody RegisterRequest registerRequest,
            HttpServletRequest httpServletRequest
    ) {
        return ResponseEntity.ok(authenticationService.registerUser(registerRequest, httpServletRequest));
    }

    /**
     *
     * Endpoint to login user
     * @param loginRequest
     * @param request
     * @param response
     * @return
     */
    @PostMapping(EndpointConstants.AuthenticationController.LOGIN_USER)
    public ResponseEntity<AuthResponse> loginUser(
            @RequestBody LoginRequest loginRequest,
            HttpServletRequest request,
            HttpServletResponse response
            ) {
        AuthResponse authResponse = authenticationService.loginUser(loginRequest, request, response);
        if (authResponse.getCookie() != null) response.addCookie(authResponse.getCookie());
        return ResponseEntity.ok(authResponse);
    }

    /**
     *
     * Endpoint to verify OTP of new user
     * @param otp
     * @param userId
     * @return
     */
    @GetMapping(EndpointConstants.AuthenticationController.VERIFY_OTP)
    public ResponseEntity<ApplicationResponse> verifyOtp(@RequestHeader("otp") String otp, @RequestParam String userId) {
        return ResponseEntity.ok(authenticationService.verifyOtp(otp, userId));
    }

    @GetMapping(EndpointConstants.AuthenticationController.RESEND_OTP)
    public ResponseEntity<ApplicationResponse> resendOtp(@RequestParam String email) {
        return ResponseEntity.ok(authenticationService.resendOtp(email));
    }
}
