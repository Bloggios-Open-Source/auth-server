package com.bloggios.authserver.rules.implementation.exhibitor;

import com.bloggios.authserver.payload.request.LoginRequest;
import com.bloggios.authserver.rules.Exhibitor;
import com.bloggios.authserver.rules.implementation.businessvalidator.EmailValidator;
import com.bloggios.authserver.rules.implementation.businessvalidator.PasswordValidator;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.rules.implementation.exhibitor
 * Created_on - May 03 - 2024
 * Created_at - 23:23
 */

@Component
public class LoginRequestExhibitor implements Exhibitor<LoginRequest> {

    private final EmailValidator emailValidator;
    private final PasswordValidator passwordValidator;

    public LoginRequestExhibitor(
            EmailValidator emailValidator,
            PasswordValidator passwordValidator
    ) {
        this.emailValidator = emailValidator;
        this.passwordValidator = passwordValidator;
    }

    @Override
    public void exhibit(LoginRequest loginRequest) {
        passwordValidator.validate(loginRequest.getPassword());
        if (loginRequest.getEntrypoint().contains("@")) {
            emailValidator.validate(loginRequest.getEntrypoint());
        }
    }
}
