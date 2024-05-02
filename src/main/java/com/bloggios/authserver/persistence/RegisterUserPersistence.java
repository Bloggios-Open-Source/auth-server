package com.bloggios.authserver.persistence;

import com.bloggios.authserver.dao.implementation.esabstractdao.UserDocumentDao;
import com.bloggios.authserver.dao.implementation.pgabstractdao.UserEntityDao;
import com.bloggios.authserver.document.UserDocument;
import com.bloggios.authserver.entity.UserEntity;
import com.bloggios.authserver.enums.DaoStatus;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.persistence
 * Created_on - May 02 - 2024
 * Created_at - 16:08
 */

@Component
public class RegisterUserPersistence {

    private static final Logger logger = LoggerFactory.getLogger(RegisterUserPersistence.class);

    private final UserEntityDao userEntityDao;
    private final ModelMapper modelMapper;
    private final UserDocumentDao userDocumentDao;

    public RegisterUserPersistence(
            UserEntityDao userEntityDao,
            ModelMapper modelMapper,
            UserDocumentDao userDocumentDao
    ) {
        this.userEntityDao = userEntityDao;
        this.modelMapper = modelMapper;
        this.userDocumentDao = userDocumentDao;
    }

    public UserDocument persist(UserEntity userEntity) {
        UserEntity response = userEntityDao.initOperation(DaoStatus.CREATE, userEntity);
        logger.info("Data persisted to Postgres Database for Register User");
        UserDocument userDocument = modelMapper.map(response, UserDocument.class);
        return userDocumentDao.initOperation(DaoStatus.CREATE, userDocument);
    }
}
