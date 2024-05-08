package com.bloggios.authserver.persistence;

import com.bloggios.authserver.dao.implementation.esabstractdao.UserDocumentDao;
import com.bloggios.authserver.document.UserDocument;
import com.bloggios.authserver.entity.UserEntity;
import com.bloggios.authserver.enums.DaoStatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.persistence
 * Created_on - May 08 - 2024
 * Created_at - 12:01
 */

@Component
public class UserDocumentPersistence {

    private final UserDocumentDao userDocumentDao;
    private final ModelMapper modelMapper;

    public UserDocumentPersistence(
            UserDocumentDao userDocumentDao,
            ModelMapper modelMapper
    ) {
        this.userDocumentDao = userDocumentDao;
        this.modelMapper = modelMapper;
    }

    public UserDocument persist(UserEntity userEntity) {
        UserDocument userDocument = modelMapper.map(userEntity, UserDocument.class);
        return userDocumentDao.initOperation(DaoStatus.UPDATE, userDocument);
    }
}
