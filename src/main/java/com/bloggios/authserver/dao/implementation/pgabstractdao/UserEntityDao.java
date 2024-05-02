package com.bloggios.authserver.dao.implementation.pgabstractdao;

import com.bloggios.authserver.dao.PgAbstractDao;
import com.bloggios.authserver.dao.repository.pgrepository.UserEntityRepository;
import com.bloggios.authserver.entity.UserEntity;
import org.springframework.stereotype.Component;

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
}
