package com.gd.ashylin.crawler.db;

import com.gd.ashylin.crawler.db.dao.DbMetadataDAO;
import com.gd.ashylin.crawler.db.entity.DbMetadata;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MySqlDbMetadataTest {

    DAOFactory daoFactory;
    DbMetadataDAO dbMetadataDAO;
    DbMetadata dbMetadata;

    @Before
    public void initDAO() {
        daoFactory = DAOFactory.getDAOFactory(1);
        dbMetadataDAO = daoFactory.getDbMetadataDAO();
    }

    @Test
    public void dbMetadataTest() {
        dbMetadata = dbMetadataDAO.getDbMetadata();
        Assert.assertEquals("OK", dbMetadata.getSummary().getStatus());
    }
}
