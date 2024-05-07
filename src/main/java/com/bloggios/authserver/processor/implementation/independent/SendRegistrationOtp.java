package com.bloggios.authserver.processor.implementation.independent;

import com.bloggios.authserver.constants.EnvironmentConstants;
import com.bloggios.authserver.dao.implementation.esabstractdao.RegistrationOtpDocumentDao;
import com.bloggios.authserver.document.RegistrationOtpDocument;
import com.bloggios.authserver.entity.UserEntity;
import com.bloggios.authserver.enums.DaoStatus;
import com.bloggios.authserver.feign.BloggiosMailProviderApplicationFeign;
import com.bloggios.authserver.payload.request.RegisterOtpMailRequest;
import com.bloggios.authserver.payload.response.ApplicationResponse;
import com.bloggios.authserver.utils.OtpGenerator;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.processor.implementation.independent
 * Created_on - May 07 - 2024
 * Created_at - 11:06
 */

@Component
public class SendRegistrationOtp {

    private final OtpGenerator otpGenerator;
    private final Environment environment;
    private final BloggiosMailProviderApplicationFeign bloggiosMailProviderApplicationFeign;
    private final RegistrationOtpDocumentDao registrationOtpDocumentDao;

    public SendRegistrationOtp(
            OtpGenerator otpGenerator,
            Environment environment,
            BloggiosMailProviderApplicationFeign bloggiosMailProviderApplicationFeign,
            RegistrationOtpDocumentDao registrationOtpDocumentDao
    ) {
        this.otpGenerator = otpGenerator;
        this.environment = environment;
        this.bloggiosMailProviderApplicationFeign = bloggiosMailProviderApplicationFeign;
        this.registrationOtpDocumentDao = registrationOtpDocumentDao;
    }


    public void sendOtpMail(UserEntity userEntity, int timesSent) {
        RegistrationOtpDocument registrationOtpDocument = otpGenerator.registrationOtpSupplier(userEntity);
        registrationOtpDocument.setTimesSent(timesSent);
        RegisterOtpMailRequest registerOtpMailRequest = RegisterOtpMailRequest
                .builder()
                .otp(registrationOtpDocument.getOtp())
                .email(userEntity.getEmail())
                .apiKey(environment.getProperty(EnvironmentConstants.MAIL_API_KEY))
                .build();
        ResponseEntity<ApplicationResponse> response = bloggiosMailProviderApplicationFeign.sendRegisterOtpMail(registerOtpMailRequest);
        if (response.getStatusCode().is2xxSuccessful()) {
            registrationOtpDocumentDao.initOperation(DaoStatus.CREATE, registrationOtpDocument);
        }
    }
}
