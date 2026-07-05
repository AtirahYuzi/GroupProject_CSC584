package com.esukarelawan.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            conn = DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/ESukarelawanDB",
                    "app",
                    "app"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
}