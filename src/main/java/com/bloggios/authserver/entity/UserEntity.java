package com.bloggios.authserver.entity;

import com.bloggios.authserver.constants.ServiceConstants;
import com.bloggios.authserver.enums.Provider;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.entity
 * Created_on - April 30 - 2024
 * Created_at - 12:39
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEntity {

    @Id
    @GeneratedValue(generator = ServiceConstants.RANDOM_UUID)
    @GenericGenerator(name = ServiceConstants.RANDOM_UUID, strategy = ServiceConstants.UUID_GENERATOR)
    private String userId;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @Column(unique = true)
    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;

    private Boolean isEnabled;
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isVerified;
    private Boolean isProfileAdded;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String remoteAddress;
    private String version;
    private int timesDisabled = 0;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "UserEntity", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "RoleEntity", referencedColumnName = "roleId")
    )
    private List<RoleEntity> roles = new ArrayList<>();
}
