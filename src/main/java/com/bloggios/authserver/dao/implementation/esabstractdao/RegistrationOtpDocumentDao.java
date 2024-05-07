package com.bloggios.authserver.dao.implementation.esabstractdao;

import com.bloggios.authserver.dao.EsAbstractDao;
import com.bloggios.authserver.dao.repository.esrepository.RegistrationOtpDocumentRepository;
import com.bloggios.authserver.document.RegistrationOtpDocument;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.dao.implementation.esabstractdao
 * Created_on - May 07 - 2024
 * Created_at - 11:34
 */

@Component
public class RegistrationOtpDocumentDao extends EsAbstractDao<RegistrationOtpDocument, RegistrationOtpDocumentRepository> {

    protected RegistrationOtpDocumentDao(RegistrationOtpDocumentRepository repository) {
        super(repository);
    }
}
