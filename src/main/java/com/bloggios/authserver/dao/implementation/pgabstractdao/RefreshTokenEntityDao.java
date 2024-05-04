package com.bloggios.authserver.dao.implementation.pgabstractdao;

import com.bloggios.authserver.dao.PgAbstractDao;
import com.bloggios.authserver.dao.repository.pgrepository.RefreshTokenEntityRepository;
import com.bloggios.authserver.entity.RefreshTokenEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.dao.implementation.pgabstractdao
 * Created_on - May 04 - 2024
 * Created_at - 09:57
 */

@Component
public class RefreshTokenEntityDao extends PgAbstractDao<RefreshTokenEntity, RefreshTokenEntityRepository> {

    protected RefreshTokenEntityDao(RefreshTokenEntityRepository repository) {
        super(repository);
    }

    public Optional<RefreshTokenEntity> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public void deleteByUserId(String userId) {
        repository.deleteByUserId(userId);
    }
}
