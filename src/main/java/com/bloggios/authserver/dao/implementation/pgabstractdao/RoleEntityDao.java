package com.bloggios.authserver.dao.implementation.pgabstractdao;

import com.bloggios.authserver.constants.InternalErrorCodes;
import com.bloggios.authserver.dao.PgAbstractDao;
import com.bloggios.authserver.dao.repository.pgrepository.RoleEntityRepository;
import com.bloggios.authserver.entity.RoleEntity;
import com.bloggios.authserver.exception.payload.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.dao.implementation.pgabstractdao
 * Created_on - May 02 - 2024
 * Created_at - 15:28
 */

@Component
public class RoleEntityDao extends PgAbstractDao<RoleEntity, RoleEntityRepository> {

    protected RoleEntityDao(RoleEntityRepository repository) {
        super(repository);
    }

    public long countRecords() {
        return repository.count();
    }

    public List<RoleEntity> batchSave(List<RoleEntity> roles) {
        return repository.saveAll(roles);
    }

    public List<RoleEntity> findAll() {
        return repository.findAll();
    }

    public RoleEntity findById(String roleId) {
        return repository.findById(roleId)
                .orElseThrow(()-> new BadRequestException(InternalErrorCodes.ROLE_NOT_FOUND));
    }
}
