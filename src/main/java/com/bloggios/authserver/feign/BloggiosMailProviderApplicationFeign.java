package com.bloggios.authserver.feign;

import com.bloggios.authserver.constants.EnvironmentConstants;
import com.bloggios.authserver.constants.ServiceConstants;
import com.bloggios.authserver.payload.request.RegisterOtpMailRequest;
import com.bloggios.authserver.payload.response.ApplicationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.feign
 * Created_on - May 07 - 2024
 * Created_at - 11:15
 */

@FeignClient(
        name = ServiceConstants.BLOGGIOS_EMAIL_SERVICE_APPLICATION,
        url = EnvironmentConstants.BLOGGIOS_EMAIL_SERVICE_BASE_PATH
)
public interface BloggiosMailProviderApplicationFeign {

    @PostMapping("/email-service/v1/mail-sending/register-otp")
    ResponseEntity<ApplicationResponse> sendRegisterOtpMail(@RequestBody RegisterOtpMailRequest request);
}
