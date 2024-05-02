package com.bloggios.authserver.rules.implementation.exhibitor;

import com.bloggios.authserver.payload.request.RegisterRequest;
import com.bloggios.authserver.rules.implementation.businessvalidator.EmailValidator;
import com.bloggios.authserver.rules.implementation.businessvalidator.PasswordValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.bloggios.authserver.utils.TestConstants.VALID_EMAIL;
import static com.bloggios.authserver.utils.TestConstants.VALID_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.rules.implementation.exhibitor
 * Created_on - May 02 - 2024
 * Created_at - 11:56
 */

@ExtendWith(MockitoExtension.class)
class RegisterRequestExhibitorTest {

    @InjectMocks
    private RegisterRequestExhibitor registerRequestExhibitor;

    @Mock
    private EmailValidator emailValidator;

    @Mock
    private PasswordValidator passwordValidator;

    @Test
    void exhibit() {
        RegisterRequest registerRequest = RegisterRequest
                .builder()
                .email(VALID_EMAIL)
                .password(VALID_PASSWORD)
                .build();
        assertDoesNotThrow(()-> registerRequestExhibitor.exhibit(registerRequest));
        Mockito.verify(emailValidator, Mockito.times(1)).validate(VALID_EMAIL);
        Mockito.verify(passwordValidator, Mockito.times(1)).validate(VALID_PASSWORD);
    }
}