package com.gd.ashylin.crawler.db;

import com.gd.ashylin.crawler.db.dao.DbMetadataDao;
import com.gd.ashylin.crawler.db.dao.H2DbMetadataDao;
import com.gd.ashylin.crawler.db.entity.metadata.DbMetadata;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class H2HealthCheckTest {

    private H2DbMetadataDao metadataDAO = new H2DbMetadataDao();
    private BasicDataSource dataSource = new BasicDataSource();

    @Before
    @SuppressWarnings("Duplicates")
    public void init() throws IOException {
        Properties properties = new Properties();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("h2-test-connect-data.properties").getFile());
        properties.load(new FileInputStream(file));

        // loading properties
        dataSource.setUrl(properties.getProperty("connection"));
        dataSource.setUsername(properties.getProperty("user"));
        dataSource.setPassword(properties.getProperty("password"));
        dataSource.setDriverClassName(properties.getProperty("driver"));

        metadataDAO.setDataSource(dataSource);
    }

    @Test
    public void dbMetadataTest() {
        DbMetadata metadata = metadataDAO.getDbMetadata();

        Assert.assertEquals("Assert db status", DbMetadataDao.STATUS_OK, metadata.getSummary().getStatus());
    }
}
