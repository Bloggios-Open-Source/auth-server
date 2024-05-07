package com.bloggios.authserver.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.payload.request
 * Created_on - May 05 - 2024
 * Created_at - 16:42
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterOtpMailRequest {

    private String otp;
    private String email;
    private String apiKey;
}
