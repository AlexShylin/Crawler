package com.gd.ashylin.crawler.health;


import com.gd.ashylin.crawler.db.DAOFactory;
import com.gd.ashylin.crawler.db.dao.DbMetadataDAO;
import com.gd.ashylin.crawler.db.entity.DbMetadata;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class HealthServiceImpl implements HealthService {

    public String healthCheck() {
        // creating DAO, doing health checks, getting DBMS metadata
        DAOFactory daoFactory = DAOFactory.getDAOFactory(1);
        DbMetadataDAO dbMetadataDAO = daoFactory.getDbMetadataDAO();
        DbMetadata dbMetadata = dbMetadataDAO.getDbMetadata();

        // now converting got information into JSON
        String metaJSON = null;
        try {
            metaJSON = new ObjectMapper().writeValueAsString(dbMetadata);
        } catch (IOException e) {
            // TODO logging
        }

        return metaJSON;
    }
}
