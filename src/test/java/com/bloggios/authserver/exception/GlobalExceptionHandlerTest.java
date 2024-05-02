package com.bloggios.authserver.exception;

import com.bloggios.authserver.exception.payload.BadRequestException;
import com.bloggios.authserver.payload.response.ExceptionResponse;
import com.bloggios.authserver.properties.FetchErrorProperties;
import com.bloggios.authserver.utils.ExceptionResponseUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.bloggios.authserver.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.exception
 * Created_on - May 01 - 2024
 * Created_at - 20:26
 */

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private FetchErrorProperties fetchErrorProperties;

    @Test
    void handleBadRequestException() {
        BadRequestException badRequestException = new BadRequestException(DATA_ERROR_CODE);
        Mockito.when(fetchErrorProperties.exceptionResponse(any(), any())).thenReturn(ExceptionResponseUtil.getExceptionResponse("DATA ERROR", DATA_ERROR_CODE));
        ResponseEntity<ExceptionResponse> responseEntity = globalExceptionHandler.handleBadRequestException(badRequestException);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getMessage(), "Error Message");
    }

    @Test
    void handleBadRequestExceptionForMessage() {
        BadRequestException badRequestException = new BadRequestException(DATA_ERROR_CODE, ERROR_MESSAGE_WITH_FIELD);
        Mockito.when(fetchErrorProperties.generateExceptionResponse(HttpStatus.BAD_REQUEST, ERROR_MESSAGE_WITH_FIELD, DATA_ERROR_CODE))
                .thenReturn(ExceptionResponseUtil.getExceptionResponse(DATA_ERROR, DATA_ERROR_CODE));
        ResponseEntity<ExceptionResponse> responseEntity = globalExceptionHandler.handleBadRequestException(badRequestException);
        assertNotNull(responseEntity);
    }
}