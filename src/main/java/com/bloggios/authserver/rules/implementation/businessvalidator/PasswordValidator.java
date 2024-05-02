package com.bloggios.authserver.rules.implementation.businessvalidator;

import com.bloggios.authserver.constants.DataErrorCodes;
import com.bloggios.authserver.constants.ServiceConstants;
import com.bloggios.authserver.exception.payload.BadRequestException;
import com.bloggios.authserver.rules.BusinessValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.regex.Pattern;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.rules.implementation.businessvalidator
 * Created_on - May 01 - 2024
 * Created_at - 21:14
 */

@Component
public class PasswordValidator implements BusinessValidator<String> {

    @Override
    public void validate(String password) {
        if (ObjectUtils.isEmpty(password)){
            throw new BadRequestException(DataErrorCodes.PASSWORD_EMPTY);
        }
        if (!Pattern.matches(ServiceConstants.PASSWORD_REGEX, password)){
            throw new BadRequestException(DataErrorCodes.PASSWORD_CRITERIA_NOT_MATCHED);
        }
    }
}
