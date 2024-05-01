package com.bloggios.authserver.rules.implementation.exhibitor;

import com.bloggios.authserver.payload.request.RegisterRequest;
import com.bloggios.authserver.rules.Exhibitor;
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

    @Override
    public void exhibit(RegisterRequest registerRequest) {
        
    }
}
