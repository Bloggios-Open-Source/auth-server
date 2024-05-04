package com.bloggios.authserver.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.servlet.http.Cookie;
import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.payload.response
 * Created_on - May 03 - 2024
 * Created_at - 16:17
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {

    private String accessToken;
    private String userId;
    private String remoteAddress;
    private List<String> authorities;
    private String email;
    private String message;

    @JsonIgnore
    private Cookie cookie;
}
