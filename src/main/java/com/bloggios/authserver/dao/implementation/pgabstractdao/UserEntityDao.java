package com.bloggios.authserver.dao.implementation.pgabstractdao;

import com.bloggios.authserver.constants.DataErrorCodes;
import com.bloggios.authserver.dao.PgAbstractDao;
import com.bloggios.authserver.dao.repository.pgrepository.UserEntityRepository;
import com.bloggios.authserver.entity.UserEntity;
import com.bloggios.authserver.exception.payload.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.dao.implementation.pgabstractdao
 * Created_on - May 02 - 2024
 * Created_at - 16:06
 */

@Component
public class UserEntityDao extends PgAbstractDao<UserEntity, UserEntityRepository> {

    protected UserEntityDao(UserEntityRepository repository) {
        super(repository);
    }

    public Optional<UserEntity> findByEmailOptional(String email) {
        return repository.findByEmail(email);
    }

    public UserEntity findById(String userId) {
        return repository.findById(userId)
                .orElseThrow(()-> new BadRequestException(DataErrorCodes.USER_NOT_FOUND));
    }
}
