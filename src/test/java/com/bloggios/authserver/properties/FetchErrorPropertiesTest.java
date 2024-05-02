package com.bloggios.authserver.properties;

import com.bloggios.authserver.payload.response.ExceptionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void exceptionResponseForNoField() {
        String property = "Error Message";
        Mockito.when(properties.getProperty("DE__AUTH-2001")).thenReturn(property);
        ExceptionResponse exceptionResponse = fetchErrorProperties.exceptionResponse(HttpStatus.BAD_REQUEST, "DE__AUTH-2001");
        assertEquals("Error Message", exceptionResponse.getMessage());
        assertEquals("", exceptionResponse.getField());
        assertEquals("DATA ERROR", exceptionResponse.getType());
    }

    @Test
    void exceptionResponseForNoInternalError() {
        String property = "Error Message";
        Mockito.when(properties.getProperty("IE__AUTH-2001")).thenReturn(property);
        ExceptionResponse exceptionResponse = fetchErrorProperties.exceptionResponse(HttpStatus.BAD_REQUEST, "IE__AUTH-2001");
        assertEquals("Error Message", exceptionResponse.getMessage());
        assertEquals("", exceptionResponse.getField());
        assertEquals("INTERNAL ERROR", exceptionResponse.getType());
    }

    @Test
    void exceptionResponseForNoProperty() {
        String property = "";
        Mockito.when(properties.getProperty("IE__AUTH-2001")).thenReturn(property);
        ExceptionResponse exceptionResponse = fetchErrorProperties.exceptionResponse(HttpStatus.BAD_REQUEST, "IE__AUTH-2001");
        assertEquals("", exceptionResponse.getMessage());
        assertEquals("", exceptionResponse.getField());
        assertEquals("INTERNAL ERROR", exceptionResponse.getType());
    }

    @Test
    void generateExceptionResponseTest() {
        String message = "Error Message~errorField";
        ExceptionResponse exceptionResponse = fetchErrorProperties.generateExceptionResponse(HttpStatus.BAD_REQUEST, message, "DE__AUTH-2001");
        assertEquals("Error Message", exceptionResponse.getMessage());
        assertEquals("errorField", exceptionResponse.getField());
        assertEquals("DATA ERROR", exceptionResponse.getType());
    }

    @Test
    void generateExceptionResponseTestForNoField() {
        String message = "Error Message";
        ExceptionResponse exceptionResponse = fetchErrorProperties.generateExceptionResponse(HttpStatus.BAD_REQUEST, message, "IE__AUTH-2001");
        assertEquals("Error Message", exceptionResponse.getMessage());
        assertEquals("", exceptionResponse.getField());
        assertEquals("INTERNAL ERROR", exceptionResponse.getType());
    }
}