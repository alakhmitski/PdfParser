package com.parser.pdf.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DataBase {
    private static final String RESOURCE_NAME = "sqlscripts/db-connection.properties";
    private static final String DB_DRIVER_PROM_NAME = "driver";
    private static final String DB_URL_PROP_NAME = "url";
    private static final String DB_USER_PROP_NAME = "user";
    private static final String DB_PASSWORD_PROP_NAME = "password";

    public Connection getConnection() {
        try {
            Properties properties = getProperties();
            Class.forName(properties.getProperty(DB_DRIVER_PROM_NAME));
            String url = properties.getProperty(DB_URL_PROP_NAME);
            String user = properties.getProperty(DB_USER_PROP_NAME);
            String password = properties.getProperty(DB_PASSWORD_PROP_NAME);
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (ClassNotFoundException | IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Properties getProperties() throws IOException {
        Properties dbProperties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream resourceStream = loader.getResourceAsStream(RESOURCE_NAME);
        dbProperties.load(resourceStream);
        return dbProperties;
    }

}
