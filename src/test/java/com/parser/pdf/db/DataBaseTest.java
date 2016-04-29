package com.parser.pdf.db;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseTest {

    private static DataBase dataBase;
    private static Connection connection;
    @BeforeClass
    public static void initDataBase() {
        dataBase = new DataBase();
    }

    @Test
    public void testGetConnection() {
        connection = dataBase.getConnection();
        assertNotNull(connection);
    }

    @AfterClass
    public static void closeConnection() {
        if (dataBase != null && connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
