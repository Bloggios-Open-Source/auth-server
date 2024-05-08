package com.bloggios.authserver.transformer.implementation.transform;

import com.bloggios.authserver.constants.ServiceConstants;
import com.bloggios.authserver.dao.implementation.pgabstractdao.RoleEntityDao;
import com.bloggios.authserver.entity.RoleEntity;
import com.bloggios.authserver.entity.UserEntity;
import com.bloggios.authserver.enums.Provider;
import com.bloggios.authserver.payload.request.RegisterRequest;
import com.bloggios.authserver.utils.IpUtils;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.bloggios.authserver.constants.EnvironmentConstants.APPLICATION_VERSION;
import static com.bloggios.authserver.constants.ServiceConstants.*;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.transformer.implementation.transform
 * Created_on - May 02 - 2024
 * Created_at - 12:13
 */

@Component
public class RegisterRequestToUserEntityTransformer {

    private final Environment environment;
    private final RoleEntityDao roleEntityDao;
    private final PasswordEncoder passwordEncoder;

    public RegisterRequestToUserEntityTransformer(
            Environment environment,
            RoleEntityDao roleEntityDao,
            PasswordEncoder passwordEncoder
    ) {
        this.environment = environment;
        this.roleEntityDao = roleEntityDao;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity transform(RegisterRequest registerRequest, HttpServletRequest httpServletRequest) {
        List<RoleEntity> roleEntities = new ArrayList<>();
        if (registerRequest.getEmail().contains(BLOGGIOS_CLOUD) || registerRequest.getEmail().contains(BLOGGIOS_IN)) {
            List<RoleEntity> all = roleEntityDao.findAll();
            List<RoleEntity> roles = all
                    .stream()
                    .filter(roleEntity -> !roleEntity.getRoleId().equals(ServiceConstants.ADMIN_ROLE))
                    .toList();
            roleEntities.addAll(roles);
        } else if (registerRequest.getEmail().contains(BLOGGIOS_COM)) {
            List<RoleEntity> all = roleEntityDao.findAll();
            roleEntities.addAll(all);
        } else {
            RoleEntity userRole = roleEntityDao.findById(USER_ROLE);
            RoleEntity dummyRole = roleEntityDao.findById(DUMMY_ROLE);
            roleEntities.addAll(List.of(userRole, dummyRole));
        }
        return UserEntity
                .builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .username(registerRequest.getEmail()) // To be changed
                .dateRegistered(new Date())
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isVerified(false)
                .isProfileAdded(false)
                .provider(Provider.email)
                .remoteAddress(IpUtils.getRemoteAddress(httpServletRequest))
                .version(environment.getProperty(APPLICATION_VERSION))
                .timesDisabled(0)
                .roles(roleEntities)
                .build();
    }
}
