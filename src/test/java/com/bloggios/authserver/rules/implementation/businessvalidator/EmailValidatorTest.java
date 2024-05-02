package com.bloggios.authserver.rules.implementation.businessvalidator;

import com.bloggios.authserver.exception.payload.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bloggios.authserver.utils.TestConstants.VALID_EMAIL;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.rules.implementation.businessvalidator
 * Created_on - May 01 - 2024
 * Created_at - 21:09
 */

@ExtendWith(MockitoExtension.class)
class EmailValidatorTest {

    @InjectMocks
    private EmailValidator emailValidator;

    @Test
    void validate() {
        assertDoesNotThrow(()-> emailValidator.validate(VALID_EMAIL));
    }

    @Test
    void validateException() {
        assertThrows(BadRequestException.class, ()-> emailValidator.validate(""));
        assertThrows(BadRequestException.class, ()-> emailValidator.validate(null));
        assertThrows(BadRequestException.class, ()-> emailValidator.validate("invalid"));
    }
}