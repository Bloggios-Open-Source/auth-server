package com.bloggios.authserver.implementation;

import com.bloggios.authserver.authentication.UserPrincipal;
import com.bloggios.authserver.constants.DataErrorCodes;
import com.bloggios.authserver.constants.EnvironmentConstants;
import com.bloggios.authserver.constants.ResponseMessageConstants;
import com.bloggios.authserver.constants.ServiceConstants;
import com.bloggios.authserver.dao.implementation.pgabstractdao.RefreshTokenEntityDao;
import com.bloggios.authserver.dao.implementation.pgabstractdao.UserEntityDao;
import com.bloggios.authserver.document.UserDocument;
import com.bloggios.authserver.entity.UserEntity;
import com.bloggios.authserver.exception.payload.AuthenticationException;
import com.bloggios.authserver.payload.request.LoginRequest;
import com.bloggios.authserver.payload.request.RegisterRequest;
import com.bloggios.authserver.payload.response.ApplicationResponse;
import com.bloggios.authserver.payload.response.AuthResponse;
import com.bloggios.authserver.persistence.RefreshTokenPersistence;
import com.bloggios.authserver.persistence.RegisterUserPersistence;
import com.bloggios.authserver.processor.implementation.process.RegistrationOtpProcessor;
import com.bloggios.authserver.rules.implementation.exhibitor.LoginRequestExhibitor;
import com.bloggios.authserver.rules.implementation.exhibitor.RegisterRequestExhibitor;
import com.bloggios.authserver.service.AuthenticationService;
import com.bloggios.authserver.transformer.implementation.transform.RegisterRequestToUserEntityTransformer;
import com.bloggios.authserver.utils.IpUtils;
import com.bloggios.authserver.utils.JwtTokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    private final LoginRequestExhibitor loginRequestExhibitor;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenEntityDao refreshTokenEntityDao;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenPersistence refreshTokenPersistence;
    private final RegistrationOtpProcessor registrationOtpProcessor;

    public AuthenticationServiceImplementation(
            RegisterRequestExhibitor registerRequestExhibitor,
            RegisterRequestToUserEntityTransformer registerRequestToUserEntityTransformer,
            RegisterUserPersistence registerUserPersistence,
            LoginRequestExhibitor loginRequestExhibitor,
            AuthenticationManager authenticationManager,
            RefreshTokenEntityDao refreshTokenEntityDao,
            JwtTokenGenerator jwtTokenGenerator,
            RefreshTokenPersistence refreshTokenPersistence,
            RegistrationOtpProcessor registrationOtpProcessor
    ) {
        this.registerRequestExhibitor = registerRequestExhibitor;
        this.registerRequestToUserEntityTransformer = registerRequestToUserEntityTransformer;
        this.registerUserPersistence = registerUserPersistence;
        this.loginRequestExhibitor = loginRequestExhibitor;
        this.authenticationManager = authenticationManager;
        this.refreshTokenEntityDao = refreshTokenEntityDao;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.refreshTokenPersistence = refreshTokenPersistence;
        this.registrationOtpProcessor = registrationOtpProcessor;
    }

    @Override
    public ApplicationResponse registerUser(RegisterRequest registerRequest, HttpServletRequest httpServletRequest) {
        long startTime = System.currentTimeMillis();
        registerRequestExhibitor.exhibit(registerRequest);
        UserEntity transform = registerRequestToUserEntityTransformer.transform(registerRequest, httpServletRequest);
        UserDocument persist = registerUserPersistence.persist(transform);
        registrationOtpProcessor.process(transform);
        logger.info("Register User : Time Taken {}", System.currentTimeMillis() - startTime);
        return ApplicationResponse
                .builder()
                .userId(persist.getUserId())
                .message(ResponseMessageConstants.REGISTERED_USER)
                .build();
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        long startTime = System.currentTimeMillis();
        loginRequestExhibitor.exhibit(loginRequest);
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEntrypoint(),
                loginRequest.getPassword()
        ));
        UserPrincipal principal = (UserPrincipal) authenticate.getPrincipal();
        if (!principal.getIsVerified()) {
            throw new AuthenticationException(DataErrorCodes.USER_NOT_VERIFIED);
        }
        CompletableFuture.runAsync(()-> refreshTokenEntityDao.deleteByUserId(principal.getUserId()));
        String origin = request.getHeader(ServiceConstants.ORIGIN);
        boolean isLongToken = ObjectUtils.isEmpty(origin);
        if (origin.isEmpty()) origin = ServiceConstants.LOCAL_ORIGIN;
        String remoteAddress = IpUtils.getRemoteAddress(request);
        String accessToken = jwtTokenGenerator.generateAccessToken(authenticate, origin, isLongToken, remoteAddress);
        List<String> authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if (isLongToken) {
            if (!authorities.contains("ROLE_ADMIN") || !authorities.contains("ROLE_DEVELOPER")) {
                throw new AuthenticationException(DataErrorCodes.AUTHORITIES_LONG_TOKEN);
            }
            logger.info("Login User (No Cookie) : Time taken {}ms", System.currentTimeMillis() - startTime);
            return AuthResponse
                    .builder()
                    .accessToken(accessToken)
                    .email(principal.getEmail())
                    .userId(principal.getUserId())
                    .accessToken(accessToken)
                    .authorities(authorities)
                    .remoteAddress(remoteAddress)
                    .message(ResponseMessageConstants.NO_REFRESH_TOKEN_GENERATED)
                    .build();
        } else {
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authenticate, origin, remoteAddress);
            CompletableFuture.runAsync(()-> refreshTokenPersistence.persist(refreshToken, principal, accessToken));
            Cookie cookie = new Cookie(EnvironmentConstants.COOKIE_NAME, refreshToken);
            cookie.setHttpOnly(!origin.contains("localhost"));
            cookie.setPath("/");
            cookie.setMaxAge(86400);
            cookie.setDomain(origin);
            logger.info("Login User : Time taken {}ms", System.currentTimeMillis() - startTime);
            return AuthResponse
                    .builder()
                    .accessToken(accessToken)
                    .email(principal.getEmail())
                    .userId(principal.getUserId())
                    .accessToken(accessToken)
                    .authorities(authorities)
                    .remoteAddress(remoteAddress)
                    .message(ResponseMessageConstants.NO_REFRESH_TOKEN_GENERATED)
                    .cookie(cookie)
                    .build();
        }
    }
}
