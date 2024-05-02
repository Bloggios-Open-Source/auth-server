package com.bloggios.authserver.document;

import com.bloggios.authserver.constants.EnvironmentConstants;
import com.bloggios.authserver.constants.ServiceConstants;
import com.bloggios.authserver.document.embeddable.RoleDocument;
import com.bloggios.authserver.enums.Provider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.document
 * Created_on - May 02 - 2024
 * Created_at - 16:19
 */

@Document(
        indexName = EnvironmentConstants.USER_INDEX
)
@Setting(
        settingPath = EnvironmentConstants.ES_SETTING
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDocument {

    @Id
    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String userId;

    @MultiField(
            mainField = @Field(
                    type = FieldType.Text,
                    name = "email",
                    analyzer = ServiceConstants.DEFAULT_AUTOCOMPLETE,
                    fielddata = true
            ),
            otherFields = {
                    @InnerField(
                            suffix = ServiceConstants.VERBATIM,
                            type = FieldType.Keyword,
                            normalizer = ServiceConstants.DEFAULT_NORMALIZER
                    )
            }
    )
    private String email;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String password;

    @MultiField(
            mainField = @Field(
                    type = FieldType.Text,
                    name = "username",
                    analyzer = ServiceConstants.DEFAULT_AUTOCOMPLETE,
                    fielddata = true
            ),
            otherFields = {
                    @InnerField(
                            suffix = ServiceConstants.VERBATIM,
                            type = FieldType.Keyword,
                            normalizer = ServiceConstants.DEFAULT_NORMALIZER
                    )
            }
    )
    private String username;

    @Field(
            type = FieldType.Date
    )
    private Date dateRegistered;

    @Field(
            type = FieldType.Date
    )
    private Date dateUpdated;
    private Boolean isEnabled;
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isVerified;
    private Boolean isProfileAdded;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private Provider provider;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String remoteAddress;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String version;
    private int timesDisabled = 0;

    @Field(
            type = FieldType.Nested
    )
    private List<RoleDocument> roles = new ArrayList<>();
}
