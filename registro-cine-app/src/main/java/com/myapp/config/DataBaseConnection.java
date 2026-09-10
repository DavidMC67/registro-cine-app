package com.myapp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Se encarga únicamente de abrir una conexión JDBC hacia MySQL
 * usando los datos definidos en Credentials.
 */
public class DataBaseConnection {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                Credentials.URL_DATA_BASE,
                Credentials.USER_DB,
                Credentials.PASS_DB
        );
    }
}
