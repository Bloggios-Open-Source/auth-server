package com.bloggios.authserver.rules.implementation.exhibitor;

import com.bloggios.authserver.payload.request.RegisterRequest;
import com.bloggios.authserver.rules.Exhibitor;
import com.bloggios.authserver.rules.implementation.businessvalidator.EmailValidator;
import com.bloggios.authserver.rules.implementation.businessvalidator.PasswordValidator;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.rules.implementation.exhibitor
 * Created_on - May 01 - 2024
 * Created_at - 12:45
 */

@Component
public class RegisterRequestExhibitor implements Exhibitor<RegisterRequest> {

    private final EmailValidator emailValidator;
    private final PasswordValidator passwordValidator;

    public RegisterRequestExhibitor
            (EmailValidator emailValidator,
             PasswordValidator passwordValidator
            ) {
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
    }

    @Override
    public void exhibit(RegisterRequest registerRequest) {
        emailValidator.validate(registerRequest.getEmail());
        passwordValidator.validate(registerRequest.getPassword());
    }
}
