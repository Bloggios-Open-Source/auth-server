package com.bloggios.authserver.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.configuration
 * Created_on - May 02 - 2024
 * Created_at - 16:37
 */

@Configuration
public class ApplicationBeans {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
