package com.bloggios.authserver.processor.implementation.voidprocess;

import com.bloggios.authserver.dao.implementation.pgabstractdao.RoleEntityDao;
import com.bloggios.authserver.entity.RoleEntity;
import com.bloggios.authserver.payload.RolePayload;
import com.bloggios.authserver.processor.VoidProcessor;
import com.bloggios.authserver.properties.FetchRoleProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.processor.implementation.voidprocess
 * Created_on - May 02 - 2024
 * Created_at - 12:33
 */

@Component
public class RoleRunnerProcessor implements VoidProcessor {

    private final FetchRoleProperties fetchRoleProperties;
    private final RoleEntityDao roleEntityDao;

    public RoleRunnerProcessor(
            FetchRoleProperties fetchRoleProperties,
            RoleEntityDao roleEntityDao
    ) {
        this.fetchRoleProperties = fetchRoleProperties;
        this.roleEntityDao = roleEntityDao;
    }

    @Override
    public void process() {
        Map<String, RolePayload> roles = fetchRoleProperties.data;
        if (roles.isEmpty()) return;
        long count = roleEntityDao.countRecords();
        if (count == 0) {
            List<RoleEntity> rolesToSave = roles
                    .values()
                    .stream()
                    .map(rolePayload -> RoleEntity
                            .builder()
                            .roleId(rolePayload.getId())
                            .role(rolePayload.getName())
                            .build()
                    )
                    .toList();
            roleEntityDao.batchSave(rolesToSave);
        } else {
            List<RoleEntity> allRoles = roleEntityDao.findAll();
            List<RoleEntity> rolesToSave = allRoles
                    .parallelStream()
                    .filter(roleEntity -> roles.values().stream().noneMatch(role -> role.getId().equals(roleEntity.getRoleId())))
                    .toList();
            roleEntityDao.batchSave(rolesToSave);
        }
    }
}
