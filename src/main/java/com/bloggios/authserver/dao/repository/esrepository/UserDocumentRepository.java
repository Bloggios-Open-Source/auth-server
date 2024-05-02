package com.bloggios.authserver.dao.repository.esrepository;

import com.bloggios.authserver.document.UserDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.dao.repository.esrepository
 * Created_on - May 02 - 2024
 * Created_at - 16:34
 */

public interface UserDocumentRepository extends ElasticsearchRepository<UserDocument, String> {
}
