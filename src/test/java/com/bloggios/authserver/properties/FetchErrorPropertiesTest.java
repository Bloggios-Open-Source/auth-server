package com.bloggios.authserver.properties;

import com.bloggios.authserver.constants.BeanNameConstants;
import com.bloggios.authserver.payload.response.ExceptionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.properties
 * Created_on - May 01 - 2024
 * Created_at - 16:48
 */

@ExtendWith(MockitoExtension.class)
class FetchErrorPropertiesTest {

    @InjectMocks
    private FetchErrorProperties fetchErrorProperties;

    @Mock
    private Properties properties;

    @Test
    void exceptionResponse() {
        String property = "Error Message~field";
        Mockito.when(properties.getProperty("DE__AUTH-2001")).thenReturn(property);
        ExceptionResponse exceptionResponse = fetchErrorProperties.exceptionResponse(HttpStatus.BAD_REQUEST, "DE__AUTH-2001");
        assertEquals("Error Message", exceptionResponse.getMessage());
        assertEquals("field", exceptionResponse.getField());
        assertEquals("DATA ERROR", exceptionResponse.getType());
    }
}