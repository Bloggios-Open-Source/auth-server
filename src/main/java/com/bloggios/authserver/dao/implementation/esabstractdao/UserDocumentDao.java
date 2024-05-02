package com.bloggios.authserver.dao.implementation.esabstractdao;

import com.bloggios.authserver.dao.EsAbstractDao;
import com.bloggios.authserver.dao.repository.esrepository.UserDocumentRepository;
import com.bloggios.authserver.document.UserDocument;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.dao.implementation.esabstractdao
 * Created_on - May 02 - 2024
 * Created_at - 16:35
 */

@Component
public class UserDocumentDao extends EsAbstractDao<UserDocument, UserDocumentRepository> {

    protected UserDocumentDao(UserDocumentRepository repository) {
        super(repository);
    }
}
