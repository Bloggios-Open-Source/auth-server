package com.bloggios.authserver.ymlparser;

import com.bloggios.authserver.constants.BeanNameConstants;
import com.bloggios.authserver.constants.InternalErrorCodes;
import com.bloggios.authserver.exception.payload.InternalException;
import com.thoughtworks.xstream.InitializationException;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.ymlparser
 * Created_on - May 01 - 2024
 * Created_at - 16:16
 */

@Configuration
public class ImportErrorMessages {

    @SneakyThrows(value = {IOException.class, InitializationException.class})
    @Bean(BeanNameConstants.ERROR_PROPERTIES_BEAN)
    public Properties errorMessages() {
        File file = ResourceUtils.getFile("classpath:error-messages.properties");
        InputStream in = new FileInputStream(file);
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException exception) {
            throw new InternalException(InternalErrorCodes.EXCEPTION_CODES);
        } finally {
            in.close();
        }
        return properties;
    }
}
