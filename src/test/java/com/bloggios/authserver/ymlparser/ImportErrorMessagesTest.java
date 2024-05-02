package com.bloggios.authserver.ymlparser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.ymlparser
 * Created_on - May 01 - 2024
 * Created_at - 16:26
 */

@ExtendWith(MockitoExtension.class)
class ImportErrorMessagesTest {

    @InjectMocks
    private ImportErrorMessages importErrorMessages;

    @Test
    void testErrorMessages() throws Exception {
        Properties properties = importErrorMessages.errorMessages();
        assertNotNull(properties);
    }
}