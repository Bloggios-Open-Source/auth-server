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

package com.bloggios.authserver.oauth2;

import com.bloggios.authserver.authentication.UserPrincipal;
import com.bloggios.authserver.constants.EnvironmentConstants;
import com.bloggios.authserver.constants.ServiceConstants;
import com.bloggios.authserver.dao.implementation.esabstractdao.UserDocumentDao;
import com.bloggios.authserver.dao.implementation.pgabstractdao.RoleEntityDao;
import com.bloggios.authserver.dao.implementation.pgabstractdao.UserEntityDao;
import com.bloggios.authserver.document.UserDocument;
import com.bloggios.authserver.entity.RoleEntity;
import com.bloggios.authserver.entity.UserEntity;
import com.bloggios.authserver.enums.Provider;
import com.bloggios.authserver.exception.payload.OAuth2AuthenticationProcessingException;
import com.bloggios.authserver.persistence.RegisterUserPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.oauth2
 * Created_on - 07 February-2024
 * Created_at - 18 : 10
 */

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserEntityDao userAuthDao;
    private final RoleEntityDao roleEntityDao;
    private final Environment environment;
    private final RegisterUserPersistence registerUserPersistence;
    private final UserDocumentDao userDocumentDao;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(ObjectUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        Optional<UserEntity> userOptional = userAuthDao.findByEmailOptional(oAuth2UserInfo.getEmail());
        UserEntity auth;
        UserDocument authDocument;
        if(userOptional.isPresent()) {
            auth = userOptional.get();
            if(!auth.getProvider().equals(Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        auth.getProvider() + " account. Please use your " + auth.getProvider() +
                        " account to login.");
            }
            authDocument = userDocumentDao.findById(auth.getUserId());
        } else {
            authDocument = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }
        return UserPrincipal.create(authDocument, oAuth2User.getAttributes());
    }

    private UserDocument registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        RoleEntity userRole = roleEntityDao.findById(ServiceConstants.USER_ROLE);
        RoleEntity dummyRole = roleEntityDao.findById(ServiceConstants.DUMMY_ROLE);
        List<RoleEntity> roleEntities = List.of(userRole, dummyRole);
        UserEntity userEntity = UserEntity
                .builder()
                .email(oAuth2UserInfo.getEmail())
                .username(oAuth2UserInfo.getEmail()) // To be changed
                .dateRegistered(new Date())
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isVerified(false)
                .isProfileAdded(false)
                .provider(Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .version(environment.getProperty(EnvironmentConstants.APPLICATION_VERSION))
                .timesDisabled(0)
                .roles(roleEntities)
                .build();
        return registerUserPersistence.persist(userEntity);
    }
}

