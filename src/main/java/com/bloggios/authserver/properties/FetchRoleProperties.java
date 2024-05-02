package com.bloggios.authserver.properties;

import com.bloggios.authserver.payload.RolePayload;
import com.bloggios.authserver.ymlparser.YamlFileMapParserFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.properties
 * Created_on - May 02 - 2024
 * Created_at - 12:45
 */

@Configuration
@ConfigurationProperties(prefix = "roles")
@PropertySource(value = "classpath:configuration/roles.yml", factory = YamlFileMapParserFactory.class)
@Getter
@Setter
public class FetchRoleProperties {

    public Map<String, RolePayload> data = new HashMap<>();
}
