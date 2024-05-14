package com.bloggios.authserver.processor.implementation.independent;

import com.bloggios.authserver.constants.DataErrorCodes;
import com.bloggios.authserver.entity.UserEntity;
import com.bloggios.authserver.enums.Provider;
import com.bloggios.authserver.exception.payload.BadRequestException;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.processor.implementation.independent
 * Created_on - May 08 - 2024
 * Created_at - 12:19
 */

@Component
public class ResendOtpProcessor {

    private final SendRegistrationOtp sendRegistrationOtp;

    public ResendOtpProcessor(
            SendRegistrationOtp sendRegistrationOtp
    ) {
        this.sendRegistrationOtp = sendRegistrationOtp;
    }

    public void process(UserEntity userEntity, int timeSent) {
        if (userEntity.getIsVerified()) {
            throw new BadRequestException(DataErrorCodes.USER_ALREADY_VERIFIED);
        }
        if (!userEntity.getProvider().equals(Provider.email)) {
            throw new BadRequestException(DataErrorCodes.PROVIDER_NOT_EMAIL_RESEND_OTP);
        }
        sendRegistrationOtp.sendOtpMail(userEntity, timeSent);
    }
}
