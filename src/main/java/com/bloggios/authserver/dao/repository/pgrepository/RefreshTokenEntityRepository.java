package com.bloggios.authserver.dao.repository.pgrepository;

import com.bloggios.authserver.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.dao.repository.pgrepository
 * Created_on - May 04 - 2024
 * Created_at - 09:54
 */

public interface RefreshTokenEntityRepository extends JpaRepository<RefreshTokenEntity, String> {

    Optional<RefreshTokenEntity> findByUserId(String userId);
    void deleteByUserId(String userId);
}
