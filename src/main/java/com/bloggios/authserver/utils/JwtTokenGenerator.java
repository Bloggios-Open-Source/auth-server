/*
 * Copyright © 2023-2024 Rohit Parihar and Bloggios
 * All rights reserved.
 * This software is the property of Rohit Parihar and is protected by copyright law.
 * The software, including its source code, documentation, and associated files, may not be used, copied, modified, distributed, or sublicensed without the express written consent of Rohit Parihar.
 * For licensing and usage inquiries, please contact Rohit Parihar at rohitparih@gmail.com, or you can also contact support@bloggios.com.
 * This software is provided as-is, and no warranties or guarantees are made regarding its fitness for any particular purpose or compatibility with any specific technology.
 * For license information and terms of use, please refer to the accompanying LICENSE file or visit http://www.apache.org/licenses/LICENSE-2.0.
 * Unauthorized use of this software may result in legal action and liability for damages.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bloggios.authserver.utils;

import com.bloggios.authserver.authentication.UserPrincipal;
import com.bloggios.authserver.constants.ServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static com.bloggios.authserver.constants.EnvironmentConstants.ACTIVE_PROFILE;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.utils
 * Created_on - 10 December-2023
 * Created_at - 19 : 45
 */

@Component
public class JwtTokenGenerator {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenGenerator.class);

    private final JwtEncoder jwtEncoder;
    private final Environment environment;

    public JwtTokenGenerator(
            JwtEncoder jwtEncoder,
            Environment environment
    ) {
        this.jwtEncoder = jwtEncoder;
        this.environment = environment;
    }

    public String generateAccessToken(Authentication authentication, String origin, Boolean isLong, String remoteAddress) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        Instant now = Instant.now();
        JwtClaimsSet jwtClaimSet = JwtClaimsSet.builder()
                .issuer(origin)
                .issuedAt(now)
                .expiresAt(now.plus(Boolean.TRUE.equals(isLong) ? 20 : 2, ChronoUnit.MINUTES))
                .subject(principal.getUserId())
                .claim(ServiceConstants.AUTHORITY, roles)
                .claim(ServiceConstants.USER_EMAIL, principal.getEmail())
                .claim(ServiceConstants.ENVIRONMENT, Objects.requireNonNull(environment.getProperty(ACTIVE_PROFILE)))
                .claim(ServiceConstants.TOKEN_TYPE, isLong ? ServiceConstants.EXTENDED_TOKEN : ServiceConstants.NORMAL_TOKEN)
                .claim(ServiceConstants.REMOTE_ADDRESS, remoteAddress)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimSet)).getTokenValue();
    }

    public String generateRefreshToken(Authentication authentication, String origin, String remoteAddress) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Instant now = Instant.now();
        JwtClaimsSet jwtClaimSet = JwtClaimsSet.builder()
                .issuer(origin)
                .issuedAt(now)
                .claim(ServiceConstants.ENVIRONMENT, Objects.requireNonNull(environment.getProperty(ACTIVE_PROFILE)))
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(principal.getUserId())
                .claim(ServiceConstants.USER_EMAIL, principal.getEmail())
                .claim(ServiceConstants.REMOTE_ADDRESS, remoteAddress)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimSet)).getTokenValue();
    }
}
