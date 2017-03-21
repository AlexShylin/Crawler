package com.gd.ashylin.crawler.db;

import com.gd.ashylin.crawler.db.dao.DbMetadataDAO;
import com.gd.ashylin.crawler.db.entity.metadata.DbMetadata;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class H2DbMetadataTest {
    DAOFactory daoFactory;
    DbMetadataDAO dbMetadataDAO;
    DbMetadata dbMetadata;

    @Before
    public void init() {
        Properties properties = new Properties();
        String connectionString, user, password, driver;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("h2ConnectionCredentials.properties").getFile());
            properties.load(new FileInputStream(file));

            // loading properties
            connectionString = properties.getProperty("connection");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            driver = properties.getProperty("driver");

            daoFactory = DAOFactory.getH2DAOFactory(connectionString, user, password, driver);
            dbMetadataDAO = daoFactory.getDbMetadataDAO();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void dbMetadataTest() {
        dbMetadata = dbMetadataDAO.getDbMetadata();
        Assert.assertEquals("OK", dbMetadata.getSummary().getStatus());
    }
}
