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

import com.bloggios.authserver.constants.InternalErrorCodes;
import com.bloggios.authserver.constants.ServiceConstants;
import com.bloggios.authserver.exception.payload.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.utils
 * Created_on - 13 December-2023
 * Created_at - 22 : 58
 */

@Component
public class JwtDecoderUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtDecoderUtil.class);

    private final JwtDecoder jwtDecoder;

    public JwtDecoderUtil(
            JwtDecoder jwtDecoder
    ) {
        this.jwtDecoder = jwtDecoder;
    }

    public Collection<? extends GrantedAuthority> extractAuthorities(String jwtToken) {
        try {
            Jwt jwt = jwtDecoder.decode(jwtToken);
            List<String> authorities = jwt.getClaimAsStringList(ServiceConstants.AUTHORITY);
            return authorities
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
        } catch (Exception exception) {
            logger.error("Exception Occurred while extracting Authorities with default message as : {}", exception.getMessage());
            throw new AuthenticationException(InternalErrorCodes.UNABLE_TO_EXTRACT_AUTHORITIES);
        }
    }

    public String extractUserId(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            return jwt.getSubject();
        } catch (Exception e) {
            logger.error("Exception Occurred while extracting User Id from token with default message as : {}", e.getMessage());
            throw new AuthenticationException(InternalErrorCodes.UNABLE_TO_EXTRACT_USER_ID_FROM_TOKEN);
        }
    }

    public String extractUserIp(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            return jwt.getClaimAsString(ServiceConstants.REMOTE_ADDRESS);
        } catch (Exception e) {
            logger.error("Exception Occurred while extracting Remote Address from token with default message as : {}", e.getMessage());
            throw new AuthenticationException(InternalErrorCodes.UNABLE_TO_EXTRACT_REMOTE_ADDRESS_FROM_TOKEN);
        }
    }
}
