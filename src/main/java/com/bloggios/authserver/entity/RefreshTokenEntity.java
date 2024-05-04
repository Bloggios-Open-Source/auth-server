package com.bloggios.authserver.entity;

import com.bloggios.authserver.constants.ServiceConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.entity
 * Created_on - May 04 - 2024
 * Created_at - 09:50
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(generator = ServiceConstants.RANDOM_UUID)
    @GenericGenerator(name = ServiceConstants.RANDOM_UUID, strategy = "org.hibernate.id.UUIDGenerator")
    private String tokenId;

    @Column(length = 100000)
    private String refreshToken;

    @Column(length = 100000)
    private String accessToken;

    @Column(unique = true)
    private String userId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateGenerated;
}
