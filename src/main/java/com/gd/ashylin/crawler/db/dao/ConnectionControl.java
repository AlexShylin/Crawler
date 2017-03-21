package com.gd.ashylin.crawler.db.dao;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * For DRY principle
 *
 * @author Alexander Shylin
 */
public abstract class ConnectionControl {

    // connection resources
    protected Connection connection;
    protected DataSource dataSource;

    /*
     * Connection control
     */

    protected void establishConnection() throws SQLException {
        if (dataSource instanceof BasicDataSource) {
            BasicDataSource bds = (BasicDataSource) dataSource;
            connection = DriverManager.getConnection(bds.getUrl(), bds.getUsername(), bds.getPassword());
        } else {
            connection = dataSource.getConnection();
        }
    }

    protected void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO logging
        }
    }
}
