package com.bloggios.authserver.rules.implementation.businessvalidator;

import com.bloggios.authserver.constants.DataErrorCodes;
import com.bloggios.authserver.exception.BadRequestException;
import com.bloggios.authserver.rules.BusinessValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.rules.implementation.businessvalidator
 * Created_on - May 01 - 2024
 * Created_at - 16:07
 */

@Component
public class EmailValidator implements BusinessValidator<String> {

    @Override
    public void validate(String email) {
        if (email == null || ObjectUtils.isEmpty(email)) {
            throw new BadRequestException(DataErrorCodes.EMAIL_MANDATORY);
        }
    }
}
