package com.gd.ashylin.crawler.db;

import com.gd.ashylin.crawler.db.dao.*;
import org.apache.commons.dbcp.BasicDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author Alexander Shylin
 */
public class H2DAOFactory extends DAOFactory {

    private Context context;
    private DataSource dataSource;

    public H2DAOFactory() {
        // get credentials from servlet context
        try {
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
            context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/h2");
        } catch (NamingException e) {
            e.printStackTrace();
            // TODO logging
        }
    }

    public H2DAOFactory(String connectionString, String user, String password, String driver) {
        // get credentials from params
        dataSource = new BasicDataSource();
        ((BasicDataSource) dataSource).setDriverClassName(driver);
        ((BasicDataSource) dataSource).setUsername(user);
        ((BasicDataSource) dataSource).setPassword(password);
        ((BasicDataSource) dataSource).setUrl(connectionString);
    }

    @Override
    public DbMetadataDAO getDbMetadataDAO() {
        return new H2DbMetadataDAO(dataSource);
    }

    @Override
    public CrawlerDAO getCrawlerDAO() {
        return new H2CrawlerDAO(dataSource);
    }

    @Override
    public ScrapResultDAO getScrapResultDAO() {
        return new H2ScrapResultDAO(dataSource);
    }

}
