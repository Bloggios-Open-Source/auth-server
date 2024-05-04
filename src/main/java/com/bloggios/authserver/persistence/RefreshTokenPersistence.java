package com.bloggios.authserver.persistence;

import com.bloggios.authserver.authentication.UserPrincipal;
import com.bloggios.authserver.dao.implementation.pgabstractdao.RefreshTokenEntityDao;
import com.bloggios.authserver.entity.RefreshTokenEntity;
import com.bloggios.authserver.enums.DaoStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.persistence
 * Created_on - May 04 - 2024
 * Created_at - 10:28
 */

@Component
public class RefreshTokenPersistence {

    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenPersistence.class);

    private final RefreshTokenEntityDao refreshTokenEntityDao;

    public RefreshTokenPersistence(
            RefreshTokenEntityDao refreshTokenEntityDao
    ) {
        this.refreshTokenEntityDao = refreshTokenEntityDao;
    }

    public void persist(String refreshToken, UserPrincipal userPrincipal, String accessToken) {
        RefreshTokenEntity build = RefreshTokenEntity
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(userPrincipal.getUserId())
                .dateGenerated(new Date())
                .build();
        logger.info("Saving Refresh Token Entity to Database");
        refreshTokenEntityDao.initOperation(DaoStatus.CREATE, build);
    }
}
