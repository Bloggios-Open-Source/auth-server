package com.bloggios.authserver.configuration;

import com.bloggios.authserver.processor.implementation.voidprocess.RoleRunnerProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.configuration
 * Created_on - May 02 - 2024
 * Created_at - 12:30
 */

@Component
public class AuthApplicationRunner implements ApplicationRunner {

    private final RoleRunnerProcessor roleRunnerProcessor;

    public AuthApplicationRunner(
            RoleRunnerProcessor roleRunnerProcessor
    ) {
        this.roleRunnerProcessor = roleRunnerProcessor;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        roleRunnerProcessor.process();
    }
}
