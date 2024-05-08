package com.bloggios.authserver.processor.implementation.process;

import com.bloggios.authserver.entity.UserEntity;
import com.bloggios.authserver.processor.Process;
import com.bloggios.authserver.processor.implementation.independent.SendRegistrationOtp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.processor.implementation.process
 * Created_on - May 07 - 2024
 * Created_at - 11:01
 */

@Component
public class RegistrationOtpProcessor implements Process<UserEntity> {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationOtpProcessor.class);

    private final SendRegistrationOtp sendRegistrationOtp;

    public RegistrationOtpProcessor(
            SendRegistrationOtp sendRegistrationOtp
    ) {
        this.sendRegistrationOtp = sendRegistrationOtp;
    }

    @Override
    public void process(UserEntity userEntity) {
        if (userEntity.getIsVerified()) {
            logger.error("User already enabled, so not sending OTP");
        } else {
            sendRegistrationOtp.sendOtpMail(userEntity, 1);
        }
    }
}
