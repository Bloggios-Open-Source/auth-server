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

package com.bloggios.authserver.configuration;

import com.bloggios.authserver.authentication.BloggiosAuthenticationEntryPoint;
import com.bloggios.authserver.authentication.BloggiosAuthenticationProvider;
import com.bloggios.authserver.authentication.CustomUserDetailService;
import com.bloggios.authserver.authentication.JwtTokenValidationFilter;
import com.bloggios.authserver.oauth2.CustomOAuth2UserService;
import com.bloggios.authserver.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.bloggios.authserver.oauth2.OAuth2AuthenticationFailureHandler;
import com.bloggios.authserver.oauth2.OAuth2AuthenticationSuccessHandler;
import com.bloggios.authserver.properties.AuthenticationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - auth-provider-read-service
 * Package - com.bloggios.auth.provider.read.configuration
 * Created_on - 09 December-2023
 * Created_at - 23 : 47
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final AuthenticationProperties authenticationProperties;
    private final CustomUserDetailService customUserDetailService;
    private final BloggiosAuthenticationEntryPoint bloggiosAuthenticationEntryPoint;
    private final JwtTokenValidationFilter jwtTokenValidationFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    public SecurityConfiguration(
            AuthenticationProperties authenticationProperties,
            CustomUserDetailService customUserDetailService,
            BloggiosAuthenticationEntryPoint bloggiosAuthenticationEntryPoint,
            JwtTokenValidationFilter jwtTokenValidationFilter,
            CustomOAuth2UserService customOAuth2UserService,
            OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
            OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler
    ) {
        this.authenticationProperties = authenticationProperties;
        this.customUserDetailService = customUserDetailService;
        this.bloggiosAuthenticationEntryPoint = bloggiosAuthenticationEntryPoint;
        this.jwtTokenValidationFilter = jwtTokenValidationFilter;
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<String> paths = authenticationProperties.getPathExclude().getPaths();
        String[] pathArray = new String[paths.size()];
        paths.toArray(pathArray);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> {
                    auth
                            .antMatchers(pathArray).permitAll()
                            .anyRequest().authenticated();
                })
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(e -> {
                    e.authenticationEntryPoint(
                            bloggiosAuthenticationEntryPoint
                    );
                    e.accessDeniedHandler(
                            (request, response, accessDeniedException) -> response.sendError(HttpServletResponse.SC_FORBIDDEN)
                    );
                })
                .formLogin().disable()
                .httpBasic().disable()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler);
        http.addFilterBefore(jwtTokenValidationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BloggiosAuthenticationProvider bloggiosAuthenticationProvider() {
        BloggiosAuthenticationProvider authenticationProvider = new BloggiosAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> bloggiosAuthenticationProvider().authenticate(authentication);
    }

    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }
}
