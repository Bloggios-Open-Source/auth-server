package com.bloggios.authserver.dao.implementation.pgabstractdao;

import com.bloggios.authserver.dao.repository.pgrepository.RoleEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - auth-server
 * Package - com.bloggios.authserver.dao.implementation.pgabstractdao
 * Created_on - May 02 - 2024
 * Created_at - 15:36
 */

@ExtendWith(MockitoExtension.class)
class RoleEntityDaoTest {

    @InjectMocks
    private RoleEntityDao roleEntityDao;

    @Mock
    private RoleEntityRepository roleEntityRepository;

    @Test
    void countRecords() {
        Mockito.when(roleEntityRepository.count()).thenReturn(5L);
        long count = roleEntityDao.countRecords();
        Mockito.verify(roleEntityRepository, Mockito.times(1)).count();
        assertEquals(5L, count);
    }
}