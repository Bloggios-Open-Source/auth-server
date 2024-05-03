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

package com.bloggios.authserver.authentication;

import com.bloggios.authserver.constants.DataErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.authentication
 * Created_on - 10 December-2023
 * Created_at - 18 : 03
 */

public class BloggiosAuthenticationProvider extends DaoAuthenticationProvider {

    private static final Logger logging = LoggerFactory.getLogger(BloggiosAuthenticationProvider.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (Objects.isNull(userDetails) || ObjectUtils.isEmpty(userDetails)) {
            throw new com.bloggios.authserver.exception.payload.AuthenticationException(DataErrorCodes.USER_NOT_FOUND);
        }
        validateForInactiveUser(userDetails);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new com.bloggios.authserver.exception.payload.AuthenticationException(DataErrorCodes.INCORRECT_PASSWORD);
        }
        return new AuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AuthenticationToken.class);
    }

    private static void validateForInactiveUser(UserDetails userDetails) {
        if (!userDetails.isEnabled()) {
            throw new com.bloggios.authserver.exception.payload.AuthenticationException(DataErrorCodes.USER_INACTIVE);
        }
        if (!userDetails.isAccountNonExpired()) {
            throw new com.bloggios.authserver.exception.payload.AuthenticationException(DataErrorCodes.ACCOUNT_EXPIRED);
        }
        if (!userDetails.isCredentialsNonExpired()) {
            throw new com.bloggios.authserver.exception.payload.AuthenticationException(DataErrorCodes.USER_CREDENTIALS_EXPIRED);
        }
        if (!userDetails.isAccountNonLocked()) {
            throw new com.bloggios.authserver.exception.payload.AuthenticationException(DataErrorCodes.ACCOUNT_LOCKED);
        }
    }
}
