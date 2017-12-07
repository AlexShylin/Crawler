package com.gd.ashylin.crawler.service;

import com.gd.ashylin.crawler.db.dao.DbMetadataDao;
import com.gd.ashylin.crawler.db.entity.metadata.DbMetadata;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Alexander Shylin
 */
@RunWith(MockitoJUnitRunner.class)
public class HealthServiceTest {

    @InjectMocks
    private HealthServiceImpl healthService = new HealthServiceImpl();

    @Mock
    private DbMetadataDao dbMetadataDao = mock(DbMetadataDao.class);


    @Test
    public void testHealthCheck() {
        DbMetadata dbMetadata = new DbMetadata(null ,null);
        when(dbMetadataDao.getDbMetadata()).thenReturn(dbMetadata);

        Assert.assertEquals(dbMetadata, healthService.healthCheck());
    }
}
