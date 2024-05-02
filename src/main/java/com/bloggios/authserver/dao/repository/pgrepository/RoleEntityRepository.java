package com.bloggios.authserver.dao.repository.pgrepository;

import com.bloggios.authserver.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.dao.repository.pgrepository
 * Created_on - May 02 - 2024
 * Created_at - 15:27
 */

public interface RoleEntityRepository extends JpaRepository<RoleEntity, String> {
}
