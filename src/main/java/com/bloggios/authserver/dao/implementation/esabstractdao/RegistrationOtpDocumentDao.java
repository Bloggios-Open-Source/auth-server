package com.bloggios.authserver.dao.implementation.esabstractdao;

import com.bloggios.authserver.constants.DataErrorCodes;
import com.bloggios.authserver.dao.EsAbstractDao;
import com.bloggios.authserver.dao.repository.esrepository.RegistrationOtpDocumentRepository;
import com.bloggios.authserver.document.RegistrationOtpDocument;
import com.bloggios.authserver.exception.payload.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    public RegistrationOtpDocument findByUserId(String userId) {
        return repository.findByUserId(userId)
                .orElseThrow(()-> new BadRequestException(DataErrorCodes.USER_NOT_PRESENT_FOR_OTP));
    }

    public Optional<RegistrationOtpDocument> findByUserIdOptional(String userId) {
        return repository.findByUserId(userId);
    }

    public void deleteById(String otpId) {
        repository.deleteById(otpId);
    }
}
