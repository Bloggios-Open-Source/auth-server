package com.bloggios.authserver.document.embeddable;

import com.bloggios.authserver.constants.ServiceConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.document.embeddable
 * Created_on - May 02 - 2024
 * Created_at - 16:27
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDocument {

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String roleId;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String roleName;
}
