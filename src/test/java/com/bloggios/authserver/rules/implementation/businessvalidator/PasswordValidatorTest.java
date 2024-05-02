package com.bloggios.authserver.rules.implementation.businessvalidator;

import com.bloggios.authserver.exception.payload.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bloggios.authserver.utils.TestConstants.VALID_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.rules.implementation.businessvalidator
 * Created_on - May 01 - 2024
 * Created_at - 21:17
 */

@ExtendWith(MockitoExtension.class)
class PasswordValidatorTest {

    @InjectMocks
    private PasswordValidator passwordValidator;

    @Test
    void validate() {
        assertDoesNotThrow(()-> passwordValidator.validate(VALID_PASSWORD));
    }

    @Test
    void validateException() {
        assertThrows(BadRequestException.class, ()-> passwordValidator.validate(""));
        assertThrows(BadRequestException.class, ()-> passwordValidator.validate(null));
        assertThrows(BadRequestException.class, ()-> passwordValidator.validate("invalid"));
    }
}