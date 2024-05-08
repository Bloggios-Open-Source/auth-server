package com.bloggios.authserver.implementation;

import com.bloggios.authserver.authentication.UserPrincipal;
import com.bloggios.authserver.constants.DataErrorCodes;
import com.bloggios.authserver.constants.EnvironmentConstants;
import com.bloggios.authserver.constants.ResponseMessageConstants;
import com.bloggios.authserver.constants.ServiceConstants;
import com.bloggios.authserver.dao.implementation.esabstractdao.RegistrationOtpDocumentDao;
import com.bloggios.authserver.dao.implementation.pgabstractdao.RefreshTokenEntityDao;
import com.bloggios.authserver.dao.implementation.pgabstractdao.UserEntityDao;
import com.bloggios.authserver.document.RegistrationOtpDocument;
import com.bloggios.authserver.document.UserDocument;
import com.bloggios.authserver.entity.UserEntity;
import com.bloggios.authserver.enums.DaoStatus;
import com.bloggios.authserver.exception.payload.AuthenticationException;
import com.bloggios.authserver.exception.payload.BadRequestException;
import com.bloggios.authserver.payload.request.LoginRequest;
import com.bloggios.authserver.payload.request.RegisterRequest;
import com.bloggios.authserver.payload.response.ApplicationResponse;
import com.bloggios.authserver.payload.response.AuthResponse;
import com.bloggios.authserver.persistence.RefreshTokenPersistence;
import com.bloggios.authserver.persistence.RegisterUserPersistence;
import com.bloggios.authserver.persistence.UserDocumentPersistence;
import com.bloggios.authserver.processor.implementation.independent.ResendOtpProcessor;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    private final RegistrationOtpDocumentDao registrationOtpDocumentDao;
    private final UserEntityDao userEntityDao;
    private final UserDocumentPersistence userDocumentPersistence;
    private final ResendOtpProcessor resendOtpProcessor;

    public AuthenticationServiceImplementation(
            RegisterRequestExhibitor registerRequestExhibitor,
            RegisterRequestToUserEntityTransformer registerRequestToUserEntityTransformer,
            RegisterUserPersistence registerUserPersistence,
            LoginRequestExhibitor loginRequestExhibitor,
            AuthenticationManager authenticationManager,
            RefreshTokenEntityDao refreshTokenEntityDao,
            JwtTokenGenerator jwtTokenGenerator,
            RefreshTokenPersistence refreshTokenPersistence,
            RegistrationOtpProcessor registrationOtpProcessor,
            RegistrationOtpDocumentDao registrationOtpDocumentDao,
            UserEntityDao userEntityDao,
            UserDocumentPersistence userDocumentPersistence,
            ResendOtpProcessor resendOtpProcessor
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
        this.registrationOtpDocumentDao = registrationOtpDocumentDao;
        this.userEntityDao = userEntityDao;
        this.userDocumentPersistence = userDocumentPersistence;
        this.resendOtpProcessor = resendOtpProcessor;
    }

    @Override
    public ApplicationResponse registerUser(RegisterRequest registerRequest, HttpServletRequest httpServletRequest) {
        long startTime = System.currentTimeMillis();
        registerRequestExhibitor.exhibit(registerRequest);
        Optional<UserEntity> byEmailOptional = userEntityDao.findByEmailOptional(registerRequest.getEmail());
        if (byEmailOptional.isPresent()) {
            throw new BadRequestException(DataErrorCodes.EMAIL_ALREADY_REGISTERED);
        }
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
        CompletableFuture.runAsync(() -> refreshTokenEntityDao.deleteByUserId(principal.getUserId()));
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
            CompletableFuture.runAsync(() -> refreshTokenPersistence.persist(refreshToken, principal, accessToken));
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

    @Override
    public ApplicationResponse verifyOtp(String otp, String userId) {
        long startTime = System.currentTimeMillis();
        if (otp == null || ObjectUtils.isEmpty(otp))
            throw new BadRequestException(DataErrorCodes.OTP_EMPTY);
        if (userId == null || ObjectUtils.isEmpty(userId))
            throw new BadRequestException(DataErrorCodes.USER_ID_NOT_PRESENT_VERIFY_OTP);
        RegistrationOtpDocument registrationOtpDocument = registrationOtpDocumentDao.findByUserId(userId);
        UserEntity userEntity = userEntityDao.findById(userId);
        if (!registrationOtpDocument.getOtp().equals(otp)) {
            throw new BadRequestException(DataErrorCodes.OTP_NOT_VALID);
        }
        if (userEntity.getIsVerified()) {
            throw new BadRequestException(DataErrorCodes.USER_ALREADY_VERIFIED);
        }
        if (registrationOtpDocument.getExpiry().before(new Date())) {
            throw new BadRequestException(DataErrorCodes.EXPIRED_OTP);
        }
        userEntity.setIsVerified(true);
        userEntity.setDateUpdated(new Date());
        registrationOtpDocumentDao.deleteById(registrationOtpDocument.getOtpId());
        UserEntity response = userEntityDao.initOperation(DaoStatus.UPDATE, userEntity);
        UserDocument userDocument = userDocumentPersistence.persist(response);
        logger.info("Execution time for verifyOtp is {}ms", System.currentTimeMillis() - startTime);
        return ApplicationResponse
                .builder()
                .message(ResponseMessageConstants.OTP_VERIFIED_SUCCESSFULLY)
                .userId(userDocument.getUserId())
                .build();
    }

    @Override
    public ApplicationResponse resendOtp(String userId) {
        long startTime = System.currentTimeMillis();
        Optional<RegistrationOtpDocument> registrationOtpDocumentOptional = registrationOtpDocumentDao.findByUserIdOptional(userId);
        int timesent = 1;
        if (registrationOtpDocumentOptional.isPresent()) {
            RegistrationOtpDocument registrationOtpDocument = registrationOtpDocumentOptional.get();
            timesent = registrationOtpDocument.getTimesSent();
            registrationOtpDocumentDao.deleteById(registrationOtpDocument.getOtpId());
            if (registrationOtpDocument.getTimesSent() >= 4) {
                throw new BadRequestException(DataErrorCodes.OTP_RESEND_LIMIT_EXCEED);
            }
        }
        UserEntity userEntity = userEntityDao.findById(userId);
        resendOtpProcessor.process(userEntity, timesent + 1);
        logger.info("Execution time for resendOtp is {}ms", System.currentTimeMillis() - startTime);
        return ApplicationResponse
                .builder()
                .message(ResponseMessageConstants.OTP_RESENT)
                .userId(userId)
                .build();
    }

    @Override
    public ApplicationResponse otpRedirectUserId(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public ApplicationResponse logoutUser(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public AuthResponse refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
